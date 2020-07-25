/**
 * Author: 张俊杰、叶俊豪、方胜强
 * Create Time: 2020/7/10
 * Update Time: 2020/7/23
 */

package com.edu.whu.xiaomaivideo.ui.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.RequestOptions;
import com.edu.whu.xiaomaivideo.R;
import com.edu.whu.xiaomaivideo.adapter.EditUserInfoAdapter;
import com.edu.whu.xiaomaivideo.restcallback.RestCallback;
import com.edu.whu.xiaomaivideo.restservice.UserRestService;
import com.edu.whu.xiaomaivideo.ui.dialog.ResetPasswordDialog;
import com.edu.whu.xiaomaivideo.util.Constant;
import com.edu.whu.xiaomaivideo.util.HttpUtil;
import com.edu.whu.xiaomaivideo.util.UriToPathUtil;
import com.edu.whu.xiaomaivideo.viewModel.EditUserInfoViewModel;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.jkt.tcompress.OnCompressListener;
import com.jkt.tcompress.TCompress;
import com.lxj.xpopup.XPopup;
import com.lxj.xpopupext.listener.CityPickerListener;
import com.lxj.xpopupext.listener.TimePickerListener;
import com.lxj.xpopupext.popup.CityPickerPopup;
import com.lxj.xpopupext.popup.TimePickerPopup;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;

import de.mustafagercek.library.LoadingButton;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;
import qiu.niorgai.StatusBarCompat;

public class EditUserInfoActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    EditUserInfoViewModel editUserInfoViewModel;
    EditUserInfoAdapter editUserInfoAdapter1,editUserInfoAdapter2;
    LoadingButton button, button2, button3;
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
        button3 = findViewById(R.id.button3);
        imageView = findViewById(R.id.imageView);
        Glide.with(this).load(Constant.currentUser.getAvatar()).apply(RequestOptions.bitmapTransform(new CircleCrop())).into(imageView);
        setRecyclerView();
        setInfoButtonListener();
        setProfileButtonListener();
        setPasswordButtonListener();

        StatusBarCompat.setStatusBarColor(this, getResources().getColor(R.color.gainsboro));

        editUserInfoViewModel = new ViewModelProvider(Objects.requireNonNull(this)).get(EditUserInfoViewModel.class);

        editUserInfoViewModel.getAllUserInfo().observe(this, infoMaps -> {
            editUserInfoAdapter1.setUserInfoList(infoMaps);
            editUserInfoAdapter2.setUserInfoList(infoMaps);
            editUserInfoAdapter1.notifyDataSetChanged();
            editUserInfoAdapter2.notifyDataSetChanged();
        });
    }

    public void setProfileButtonListener() {
        button2.setButtonOnClickListener(v -> {
            Intent intent = new Intent();
            intent.setType("image/*");
            // action表示intent的类型，可以是查看、删除、发布或其他情况；我们选择ACTION_GET_CONTENT，系统可以根据Type类型来调用系统程序选择Type
            // 类型的内容给你选择
            intent.setAction(Intent.ACTION_GET_CONTENT);
            // 如果第二个参数大于或等于0，那么当用户操作完成后会返回到本程序的onActivityResult方法
            startActivityForResult(intent, 1);
        });
    }

    @Override
    public void onBackPressed()
    {
        setResult(RESULT_OK);
        super.onBackPressed();
    }

    public void setPasswordButtonListener() {
        button3.setButtonOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new XPopup.Builder(EditUserInfoActivity.this)
                        .asCustom(new ResetPasswordDialog(EditUserInfoActivity.this, new ResetPasswordDialog.OnConfirmButtonClickListener() {
                            @Override
                            public void onClick(ResetPasswordDialog dialog) {
                                TextInputEditText originPasswordEditText = dialog.findViewById(R.id.originPassword);
                                String originPassword = originPasswordEditText.getText().toString();
                                TextInputEditText newPasswordEditText = dialog.findViewById(R.id.newPassword);
                                String newPassword = newPasswordEditText.getText().toString();
                                TextInputEditText reNewPasswordEditText = dialog.findViewById(R.id.reNewPassword);
                                String reNewPassword = reNewPasswordEditText.getText().toString();
                                if (!newPassword.equals(reNewPassword)) {
                                    TextInputLayout reNewPasswordLayout = dialog.findViewById(R.id.reNewPasswordLayout);
                                    reNewPasswordLayout.setError("两次输入新密码不一致！");
                                }
                                else if (!originPassword.equals(Constant.currentUser.getPassword())) {
                                    TextInputLayout originPasswordLayout = dialog.findViewById(R.id.originPasswordLayout);
                                    originPasswordLayout.setError("原密码输入错误！");
                                }
                                else {
                                    Constant.currentUser.setPassword(newPassword);
                                    LoadingButton loadingButton = dialog.findViewById(R.id.tv_confirm);
                                    loadingButton.onStartLoading();
                                    UserRestService.modifyUser(Constant.currentUser, new RestCallback() {
                                        @Override
                                        public void onSuccess(int resultCode) {
                                            loadingButton.onStopLoading();
                                            // 改缓存的数据
                                            SharedPreferences sp = getApplication().getSharedPreferences("data", Context.MODE_PRIVATE);;
                                            SharedPreferences.Editor editor = sp.edit();
                                            editor.putString("username", Constant.currentUser.getUsername());
                                            editor.putString("password", Constant.currentUser.getPassword());
                                            editor.apply();
                                            // 退出弹窗
                                            dialog.dismiss();
                                            Toast.makeText(EditUserInfoActivity.this, "修改密码成功！", Toast.LENGTH_LONG).show();
                                        }
                                    });
                                }
                            }
                        })).show();
            }
        });
    }

    public void setInfoButtonListener() {
        button.setButtonOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isEdit) {
                    recyclerView.setAdapter(editUserInfoAdapter1);
                    button.setButtonText("提交信息");
                    isEdit = true;
                } else {
                    editUserInfoAdapter1.commit();
                    button.onStartLoading();
                    UserRestService.modifyUser(Constant.currentUser, new RestCallback() {
                        @Override
                        public void onSuccess(int resultCode) {
                            button.onStopLoading();
                            button.setButtonText("修改信息");
                            isEdit = false;
                            if (resultCode == Constant.RESULT_SUCCESS) {
                                // 修改成功
                                Toast.makeText(EditUserInfoActivity.this, "修改信息成功！", Toast.LENGTH_LONG).show();
                                // 更改adapter
                                recyclerView.setAdapter(editUserInfoAdapter2);
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
    }

    public void setRecyclerView() {
        recyclerView.setLayoutManager(new LinearLayoutManager(EditUserInfoActivity.this));
        editUserInfoAdapter1 = new EditUserInfoAdapter(EditUserInfoActivity.this, new EditUserInfoAdapter.OnItemClickListener() {
            @Override
            public void onClick(EditUserInfoAdapter.EditUserInfoViewHolder_2 viewHolder, int pos) {
                if (pos == 3) {
                    // 性别
                    new XPopup.Builder(EditUserInfoActivity.this)
                            .asBottomList("请选择性别", new String[]{"男", "女"},
                                    (position, text) -> {
                                        viewHolder.editInfoItem.setText("性别：" + text);
                                        editUserInfoAdapter1.updateGender(text);
                                    })
                            .show();
                }
                else if (pos == 4) {
                    // 生日
                    Calendar date = Calendar.getInstance();
                    date.set(1970, 1,1);
                    Calendar date2 = Calendar.getInstance();
                    date2.set(LocalDate.now().getYear(), LocalDate.now().getMonthValue(),LocalDate.now().getDayOfMonth());
                    TimePickerPopup popup = new TimePickerPopup(EditUserInfoActivity.this)
                            .setDateRang(date, date2)
                            .setTimePickerListener(new TimePickerListener() {
                                @Override
                                public void onTimeChanged(Date date) {
                                }
                                @Override
                                public void onTimeConfirm(Date date, View view) {
                                    DateFormat formatter = SimpleDateFormat.getDateInstance();
                                    String dateString = formatter.format(date);
                                    viewHolder.editInfoItem.setText("生日："+dateString);
                                    editUserInfoAdapter1.updateBirthday(dateString);
                                }
                            });

                    new XPopup.Builder(EditUserInfoActivity.this)
                            .asCustom(popup)
                            .show();
                }
                else if (pos == 5) {
                    // 地区
                    CityPickerPopup popup = new CityPickerPopup(EditUserInfoActivity.this);
                    popup.setCityPickerListener(new CityPickerListener() {
                        @Override
                        public void onCityConfirm(String province, String city, String area, View v) {
                            String areaString = province+" "+city;
                            viewHolder.editInfoItem.setText("地区："+areaString);
                            editUserInfoAdapter1.updateArea(areaString);
                        }
                        @Override
                        public void onCityChange(String province, String city, String area) {

                        }
                    });
                    new XPopup.Builder(EditUserInfoActivity.this)
                            .asCustom(popup)
                            .show();
                }
            }
        },true);
        editUserInfoAdapter2 = new EditUserInfoAdapter(EditUserInfoActivity.this, false);
        recyclerView.setAdapter(editUserInfoAdapter2);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
                Uri selectedImage = data.getData();
                String imagePath= UriToPathUtil.getRealFilePath(this,selectedImage);
                Glide.with(this).load(selectedImage).apply(RequestOptions.bitmapTransform(new CircleCrop())).into(imageView);
                button2.onStartLoading();
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
                                    Constant.currentUser.setAvatar(responseData);
                                    UserRestService.modifyUser(Constant.currentUser, new RestCallback() {
                                        @Override
                                        public void onSuccess(int resultCode) {
                                            button2.onStopLoading();
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