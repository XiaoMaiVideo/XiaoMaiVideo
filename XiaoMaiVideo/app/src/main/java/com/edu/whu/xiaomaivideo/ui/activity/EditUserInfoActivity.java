/**
 * Author: 张俊杰、叶俊豪
 * Create Time: 2020/7/10
 * Update Time: 2020/7/12
 */

package com.edu.whu.xiaomaivideo.ui.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.edu.whu.xiaomaivideo.R;
import com.edu.whu.xiaomaivideo.adapter.EditUserInfoAdapter;
import com.edu.whu.xiaomaivideo.restcallback.UserRestCallback;
import com.edu.whu.xiaomaivideo.restservice.UserRestService;
import com.edu.whu.xiaomaivideo.util.Constant;
import com.edu.whu.xiaomaivideo.util.HttpUtil;
import com.edu.whu.xiaomaivideo.util.UriToPathUtil;
import com.edu.whu.xiaomaivideo.viewModel.EditUserInfoViewModel;
import com.jkt.tcompress.OnCompressListener;
import com.jkt.tcompress.TCompress;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Objects;

import de.mustafagercek.library.LoadingButton;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class EditUserInfoActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    EditUserInfoViewModel editUserInfoViewModel;
    EditUserInfoAdapter editUserInfoAdapter1,editUserInfoAdapter2;
    LoadingButton button, button2;
    TextView textView;
    ImageView imageView;
    boolean isEdit = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_user_info);
        recyclerView = findViewById(R.id.edit_user_info_recyclerView);
        button = findViewById(R.id.button);
        button2 = findViewById(R.id.button2);
        imageView = findViewById(R.id.imageView);
        recyclerView.setLayoutManager(new LinearLayoutManager(EditUserInfoActivity.this));
        editUserInfoAdapter1 = new EditUserInfoAdapter(EditUserInfoActivity.this, new EditUserInfoAdapter.OnItemClickListener() {
            @Override
            public void onClick(int pos) {

            }
        },true);
        editUserInfoAdapter2 = new EditUserInfoAdapter(EditUserInfoActivity.this, new EditUserInfoAdapter.OnItemClickListener() {
            @Override
            public void onClick(int pos) {

            }
        },false);
        recyclerView.setAdapter(editUserInfoAdapter2);

        button.setButtonOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isEdit) {
                    recyclerView.setAdapter(editUserInfoAdapter1);
                    button.setButtonText("提交信息");
                    isEdit = true;
                } else {
                    if (!editUserInfoAdapter1.commit()) {
                        button.setButtonText("修改信息");
                        isEdit = false;
                        return;
                    }
                    button.onStartLoading();
                    UserRestService.modifyUser(Constant.CurrentUser, new UserRestCallback() {
                        @Override
                        public void onSuccess(int resultCode) {
                            super.onSuccess(resultCode);
                            button.onStopLoading();
                            button.setButtonText("修改信息");
                            isEdit = false;
                            if (resultCode == Constant.RESULT_SUCCESS) {
                                // 修改成功
                                Toast.makeText(EditUserInfoActivity.this, "修改信息成功！", Toast.LENGTH_LONG).show();
                            }
                            else {
                                // 修改失败
                                Toast.makeText(EditUserInfoActivity.this, "修改信息失败！", Toast.LENGTH_LONG).show();
                            }
                        }
                    });
                }
            }
        });

        button2.setButtonOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                //action表示intent的类型，可以是查看、删除、发布或其他情况；我们选择ACTION_GET_CONTENT，系统可以根据Type类型来调用系统程序选择Type
                //类型的内容给你选择
                intent.setAction(Intent.ACTION_GET_CONTENT);
                //如果第二个参数大于或等于0，那么当用户操作完成后会返回到本程序的onActivityResult方法
                startActivityForResult(intent, 1);
            }
        });

        editUserInfoViewModel = new ViewModelProvider(Objects.requireNonNull(this)).get(EditUserInfoViewModel.class);

        editUserInfoViewModel.getAllUserInfo().observe(this, new Observer<List<EditUserInfoViewModel.InfoMap>>() {
            @Override
            public void onChanged(List<EditUserInfoViewModel.InfoMap> infoMaps) {
                editUserInfoAdapter1.setUserInfoList(infoMaps);
                editUserInfoAdapter2.setUserInfoList(infoMaps);
                editUserInfoAdapter1.notifyDataSetChanged();
                editUserInfoAdapter2.notifyDataSetChanged();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
                Uri selectedImage = data.getData();
                String imagePath= UriToPathUtil.getRealFilePath(this,selectedImage);
                Glide.with(this).load(selectedImage).into(imageView);
                // TODO: 压缩图片，上传服务器
                button.onStartLoading();
                TCompress tCompress = new TCompress.Builder()
                        .setMaxWidth(810)
                        .setMaxHeight(540)
                        .setQuality(80)
                        .setFormat(Bitmap.CompressFormat.JPEG)
                        .setConfig(Bitmap.Config.RGB_565)
                        .build();
                tCompress.compressToFileAsync(new File(imagePath), new OnCompressListener<File>() {
                    @Override
                    public void onCompressFinish(boolean success, final File file) {
                        if (success) {
                            HttpUtil.sendProfileRequest(file.getAbsolutePath(), new Callback() {
                                @Override
                                public void onFailure(Call call, IOException e) {

                                }

                                @Override
                                public void onResponse(Call call, Response response) throws IOException {
                                    String responseData = response.body().string();
                                    Constant.CurrentUser.setAvatar(responseData);
                                    UserRestService.modifyUser(Constant.CurrentUser, new UserRestCallback() {
                                        @Override
                                        public void onSuccess(int resultCode) {
                                            super.onSuccess(resultCode);
                                            button.onStopLoading();
                                            if (resultCode == Constant.RESULT_SUCCESS) {
                                                // 更改头像成功
                                                Toast.makeText(EditUserInfoActivity.this, "更改头像成功！", Toast.LENGTH_LONG).show();
                                            } else {
                                                // 更改头像失败
                                                Toast.makeText(EditUserInfoActivity.this, "更改头像失败！", Toast.LENGTH_LONG).show();
                                            }
                                        }
                                    });
                                }
                            });
                        }
                    }
                });
            } else {
                // 操作错误或没有选择图片
            }
        }
    }
}