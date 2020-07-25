/**
 * Author: 叶俊豪
 * Create Time: 2020/7/24
 * Update Time: 2020/7/25
 */
package com.edu.whu.xiaomaivideo.ui.dialog;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.edu.whu.xiaomaivideo.R;
import com.edu.whu.xiaomaivideo.adapter.SelectLabelAdapter;
import com.edu.whu.xiaomaivideo.model.Movie;
import com.edu.whu.xiaomaivideo.restcallback.RestCallback;
import com.edu.whu.xiaomaivideo.restservice.UserRestService;
import com.edu.whu.xiaomaivideo.util.CommonUtil;
import com.edu.whu.xiaomaivideo.util.Constant;
import com.edu.whu.xiaomaivideo.util.HttpUtil;
import com.lxj.xpopup.XPopup;
import com.lxj.xpopup.core.BasePopupView;
import com.lxj.xpopup.core.BottomPopupView;
import com.vincent.videocompressor.VideoCompress;

import org.parceler.Parcels;

import java.io.IOException;
import java.util.List;

import de.mustafagercek.library.LoadingButton;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class TakeVideoDialog extends BottomPopupView {

    Context mContext;
    String inputPath, outputPath;
    RecyclerView recyclerView;
    SelectLabelAdapter selectLabelAdapter;
    LoadingButton submitButton;
    EditText editText;
    CheckBox locationSelector, compressSelector;
    ProgressDialog progressDialog;
    public TakeVideoDialog(@NonNull Context context, String inputPath, String outputPath) {
        super(context);
        mContext = context;
        this.inputPath = inputPath;
        this.outputPath = outputPath;
    }

    @Override
    protected int getImplLayoutId() {
        return R.layout.take_video_dialog;
    }

    @Override
    protected void onCreate() {
        super.onCreate();
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new GridLayoutManager(mContext, 4));
        selectLabelAdapter = new SelectLabelAdapter(mContext);
        recyclerView.setAdapter(selectLabelAdapter);
        submitButton = findViewById(R.id.submitButton);
        editText = findViewById(R.id.descriptionTextMultiLine);
        locationSelector = findViewById(R.id.locationSelector);
        compressSelector = findViewById(R.id.compressSelector);
        if (ActivityCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            locationSelector.setChecked(false);
        }
        else {
            locationSelector.setChecked(true);
        }
        compressSelector.setChecked(true);
        progressDialog = new ProgressDialog(mContext, "压缩中");
        locationSelector.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    if (ActivityCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                        Toast.makeText(mContext, "你未开启定位权限，不能上传位置信息哦！", Toast.LENGTH_LONG).show();
                        locationSelector.setChecked(false);
                    }
                }
            }
        });
        submitButton.setButtonOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if (compressSelector.isChecked()) {
                    compress(inputPath, outputPath);
                }
                else {
                    submitButton.onStartLoading();
                    upload(inputPath, editText.getText().toString(), selectLabelAdapter.selectLabels);
                }
            }
        });
    }

    private void compress(final String input, final String output) {
        VideoCompress.compressVideoLow(input, output, new VideoCompress.CompressListener() {
            @Override
            public void onStart() {
                new XPopup.Builder(mContext)
                        .asCustom(progressDialog)
                        .show();
            }

            @Override
            public void onSuccess() {
                progressDialog.setProgress(100);
                progressDialog.dismissWith(new Runnable() {
                    @Override
                    public void run() {
                        submitButton.onStartLoading();
                        upload(outputPath, editText.getText().toString(), selectLabelAdapter.selectLabels);
                    }
                });
            }

            @Override
            public void onFail() {

            }

            @Override
            public void onProgress(float percent) {
                progressDialog.setProgress((int) percent);
            }
        });
    }

    private void sendVideo(String path, String description, List<String> selectLabels, String location) {
        StringBuilder labelString = new StringBuilder();
        for (String label: selectLabels) {
            labelString.append(label).append(";");
        }
        HttpUtil.sendVideoRequest(path, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String responseData = response.body().string();
                Movie movie = new Movie();
                movie.setUrl(responseData);
                movie.setDescription(description);
                movie.setPublishTime(CommonUtil.convertTimeToDateString(System.currentTimeMillis()));
                movie.setCategories(labelString.toString());
                movie.setLocation(location);
                UserRestService.addUserMovie(movie, new RestCallback() {
                    @Override
                    public void onSuccess(int resultCode) {
                        if (submitButton.isInProgress()) {
                            submitButton.onStopLoading();
                        }
                        movie.setMovieId((long) resultCode);
                        TakeVideoDialog.this.dismissWith(new Runnable() {
                            @Override
                            public void run() {
                                BasePopupView popupView = new XPopup.Builder(mContext)
                                        .asCustom(new SimpleBottomDialog(mContext, R.drawable.success, "发布成功", movie.getMovieId()))
                                        .show();
                            }
                        });
                    }
                });
            }
        });
    }

    private void upload(String path, String description, List<String> selectLabels) {
        if (locationSelector.isChecked()) {
            // 发送位置信息
            sendVideo(path, description, selectLabels, Constant.currentLocation.getValue());
        }
        else {
            // 不发送位置信息
            sendVideo(path, description, selectLabels, "");
        }

    }
}
