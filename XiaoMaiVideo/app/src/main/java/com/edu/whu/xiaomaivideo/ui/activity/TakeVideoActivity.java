/**
 * Author: 张俊杰
 * Create Time: 2020/7/14
 * Update Time: 2020/7/17
 */

package com.edu.whu.xiaomaivideo.ui.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.edu.whu.xiaomaivideo.R;
import com.edu.whu.xiaomaivideo.adapter.SelectLabelAdapter;
import com.edu.whu.xiaomaivideo.model.Movie;
import com.edu.whu.xiaomaivideo.model.User;
import com.edu.whu.xiaomaivideo.restcallback.MovieRestCallback;
import com.edu.whu.xiaomaivideo.restcallback.RestCallback;
import com.edu.whu.xiaomaivideo.restcallback.UserRestCallback;
import com.edu.whu.xiaomaivideo.restservice.MovieRestService;
import com.edu.whu.xiaomaivideo.restservice.UserRestService;
import com.edu.whu.xiaomaivideo.util.CommonUtils;
import com.edu.whu.xiaomaivideo.util.Constant;
import com.edu.whu.xiaomaivideo.util.HttpUtil;
import com.edu.whu.xiaomaivideo.util.UriToPathUtil;
import com.lxj.xpopup.XPopup;
import com.vincent.videocompressor.VideoCompress;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.mustafagercek.library.LoadingButton;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Request;
import okhttp3.Response;

public class TakeVideoActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    SelectLabelAdapter selectLabelAdapter;
    Button takeVideoButton;
    LoadingButton compressButton, notCompressButton;
    String inputPath;
    String outputPath;
    ProgressBar progressBar;
    EditText editText;
    CheckBox checkBox;

    //获取当前经纬坐标位置
    LocationManager manager;
    //权限数组
    String[] premissinos;
    //没有权限
    boolean hasNoPermisson;
    //权限存在
    boolean yes;

    public void initPremission(){
        //判断当前版本是否大于6
        if(Build.VERSION.SDK_INT>Build.VERSION_CODES.M){
            for(String per:premissinos){
                if(checkSelfPermission(per)!=PackageManager.PERMISSION_GRANTED){
                    hasNoPermisson=true;
                    break;
                }
            }
            if(hasNoPermisson){
                requestPermissions(premissinos,100);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode==100){
            for(int i=0;i<grantResults.length;i++){
                if(grantResults[i]!=PackageManager.PERMISSION_GRANTED){
                    yes=true;
                    break;
                }
            }
            if(yes){
                Log.e("##","没有权限!");
            }
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_take_video);
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new GridLayoutManager(TakeVideoActivity.this, 4));
        selectLabelAdapter = new SelectLabelAdapter(TakeVideoActivity.this);
        recyclerView.setAdapter(selectLabelAdapter);
        takeVideoButton = findViewById(R.id.takeVideoButton);
        compressButton = findViewById(R.id.compressButton);
        notCompressButton = findViewById(R.id.notCompressButton);
        progressBar = findViewById(R.id.progressBar);
        editText = findViewById(R.id.descriptionTextMultiLine);
        checkBox = findViewById(R.id.locationSelector);

        compressButton.setVisibility(View.GONE);
        notCompressButton.setVisibility(View.GONE);


        LocationListener locationListener=new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                Geocoder geocoder=new Geocoder(getApplicationContext());

                Log.e("精度是:", location.getLatitude() + "");
                Log.e("维度是:", location.getLongitude() + "");
                Log.e("海拔是:", location.getAltitude() + "");
                Log.e("速度", location.getSpeed() + "");
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            @Override
            public void onProviderEnabled(String provider) {

            }

            @Override
            public void onProviderDisabled(String provider) {

            }
        };
            //要申请的权限列表
        premissinos=new String[]{Manifest.permission.ACCESS_COARSE_LOCATION,Manifest.permission.ACCESS_FINE_LOCATION};
        manager = (LocationManager) getSystemService(LOCATION_SERVICE);//获得定位的管理类
        initPremission();
        if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        //第一个参数 位置提供器 第二个参数 位置变化的事件间隔 第三个参数 位置变化的距离间隔 事件监听LocationListener
        manager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);



        takeVideoButton.setOnClickListener(v -> {
            new XPopup.Builder(TakeVideoActivity.this)
                    .asBottomList("拍摄视频还是选择视频？", new String[]{"拍摄视频", "选择视频"},
                            (position, text) -> {
                                if (position == 0) {
                                    Intent intent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
                                    //设置视频录制的最长时间
                                    intent.putExtra(MediaStore.EXTRA_DURATION_LIMIT, 30);
                                    //设置视频录制的画质
                                    intent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 1);
                                    startActivityForResult(intent, 1);
                                }
                                else {
                                    selectVideo();
                                }
                            })
                    .show();
        });
        compressButton.setButtonOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                compress(inputPath, outputPath);
            }
        });
        notCompressButton.setButtonOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                notCompressButton.onStartLoading();
                upload(inputPath, editText.getText().toString(), selectLabelAdapter.selectLabels);
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // 退出后停止监听
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {
            if (resultCode == Activity.RESULT_OK && requestCode == 1) {
                Uri uri = data.getData();
                takeVideoButton.setVisibility(View.GONE);
                inputPath = UriToPathUtil.getRealFilePath(this, uri);
                File path = new File(Environment.getExternalStorageDirectory().toString() + "/xiaomai/video");
                if (!path.exists()) {
                    path.mkdirs();
                }
                outputPath = path.getPath() + "/" + System.currentTimeMillis() + ".mp4";
                Log.e("TakeVideoActivity", outputPath+"_");
                compressButton.setVisibility(View.VISIBLE);
                notCompressButton.setVisibility(View.VISIBLE);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void compress(final String input, final String output) {
        /**
         * 视频压缩
         * 第一个参数:视频源文件路径
         * 第二个参数:压缩后视频保存的路径
         */
        VideoCompress.compressVideoLow(input, output, new VideoCompress.CompressListener() {
            @Override
            public void onStart() {
            }

            @Override
            public void onSuccess() {
                progressBar.setProgress(100);
                compressButton.onStartLoading();
                upload(outputPath, editText.getText().toString(), selectLabelAdapter.selectLabels);
            }

            @Override
            public void onFail() {

            }

            @Override
            public void onProgress(float percent) {
                progressBar.setProgress((int) percent);
            }
        });
    }

    private void upload(String path, String description, List<String> selectLabels) {
        if (checkBox.isChecked()) {
            // 调后端接口返回城市
        }
        else {
            //
        }
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
                movie.setPublishTime(CommonUtils.convertTimeToDateString(System.currentTimeMillis()));
                movie.setCategories(labelString.toString());
                UserRestService.addUserMovie(movie, new RestCallback() {
                    @Override
                    public void onSuccess(int resultCode) {
                        if (resultCode == Constant.RESULT_SUCCESS) {
                            if (compressButton.isInProgress()) {
                                compressButton.onStopLoading();
                            } else if (notCompressButton.isInProgress()) {
                                notCompressButton.onStopLoading();
                            }
                            setResult(RESULT_OK);
                            TakeVideoActivity.this.finish();
                        }
                    }
                });
            }
        });
    }

    //选择视频
    private void selectVideo() {

        if (android.os.Build.BRAND.equals("Huawei")) {
            Intent intentPic = new Intent(Intent.ACTION_PICK,
                    android.provider.MediaStore.Video.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(intentPic, 1);
        }
        if (android.os.Build.BRAND.equals("Xiaomi")) {//是否是小米设备,是的话用到弹窗选取入口的方法去选取视频
            Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "video/*");
            startActivityForResult(Intent.createChooser(intent, "选择要导入的视频"), 1);
        } else {//直接跳到系统相册去选取视频
            Intent intent = new Intent();
            if (Build.VERSION.SDK_INT < 19) {
                intent.setAction(Intent.ACTION_GET_CONTENT);
                intent.setType("video/*");
            } else {
                intent.setAction(Intent.ACTION_OPEN_DOCUMENT);
                intent.addCategory(Intent.CATEGORY_OPENABLE);
                intent.setType("video/*");
            }
            startActivityForResult(Intent.createChooser(intent, "选择要导入的视频"), 1);
        }
    }
}