/**
 * Author: 张俊杰、叶俊豪、方胜强
 * Create Time: 2020/7/8
 * Update Time: 2020/7/12
 */

package com.edu.whu.xiaomaivideo.ui.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import android.annotation.SuppressLint;
import android.os.Bundle;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;

import com.edu.whu.xiaomaivideo.R;
import com.edu.whu.xiaomaivideo.databinding.ActivityLoginBinding;
import com.edu.whu.xiaomaivideo.model.User;
import com.edu.whu.xiaomaivideo.restcallback.RestCallback;
import com.edu.whu.xiaomaivideo.restcallback.UserRestCallback;
import com.edu.whu.xiaomaivideo.restservice.UserRestService;
import com.edu.whu.xiaomaivideo.viewModel.LoginViewModel;

import java.util.Objects;

import com.edu.whu.xiaomaivideo.util.Constant;

import qiu.niorgai.StatusBarCompat;


public class LoginActivity extends AppCompatActivity {
    private String username;
    private String password;
    private String rePassword;
    ActivityLoginBinding activityLoginBinding;
    LoginViewModel loginViewModel;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        loginViewModel = new ViewModelProvider(Objects.requireNonNull(this)).get(LoginViewModel.class);
        activityLoginBinding = DataBindingUtil.setContentView(this, R.layout.activity_login);
        activityLoginBinding.setViewmodel(loginViewModel);
        activityLoginBinding.setLifecycleOwner(this);

        StatusBarCompat.setStatusBarColor(this, getResources().getColor(R.color.gainsboro));

        activityLoginBinding.editRePassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                activityLoginBinding.rePasswordTextInputLayout.setErrorEnabled(false);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        activityLoginBinding.editPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                activityLoginBinding.passwordTextInputLayout.setErrorEnabled(false);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        activityLoginBinding.editUsername.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                activityLoginBinding.accountTextInputLayout.setErrorEnabled(false);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        activityLoginBinding.loginBtn.setButtonOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                username = activityLoginBinding.editUsername.getText().toString().trim();
                password = activityLoginBinding.editPassword.getText().toString();
                if (username.isEmpty()) {
                    activityLoginBinding.accountTextInputLayout.setError("用户名不能为空");
                    return;
                }
                if (password.isEmpty()) {
                    activityLoginBinding.passwordTextInputLayout.setError("密码不能为空");
                    return;
                }


                // 注册
                if (activityLoginBinding.textView2.getText().equals("已有账号？")) {
                    rePassword = activityLoginBinding.editRePassword.getText().toString();
                    if (!rePassword.equals(password)) {
                        activityLoginBinding.rePasswordTextInputLayout.setError("两次密码不相同");
                        return;
                    }
                    // 注册
                    else {
                        final User user = new User();
                        user.setUsername(username);
                        user.setPassword(password);
                        UserRestService.addUser(user, new RestCallback() {
                            @Override
                            public void onSuccess(int resultCode) {
                                activityLoginBinding.loginBtn.onStopLoading();
                                if (resultCode == Constant.RESULT_SUCCESS) {
                                    // 成功
                                    activityLoginBinding.textView2.performClick();
                                    loginViewModel.commit(username, password);
                                } else if (resultCode == Constant.USER_ALREADY_EXISTS) {
                                    // 用户已存在
                                    activityLoginBinding.accountTextInputLayout.setError("用户名已存在");
                                } else {
                                    // 注册失败
                                }
                            }
                        });
                    }
                }
                // 登录
                else {
                    User user = new User();
                    user.setUsername(username);
                    user.setPassword(password);
                    UserRestService.verifyUser(user, new UserRestCallback() {
                        @Override
                        public void onSuccess(int resultCode, User user) {
                            activityLoginBinding.loginBtn.onStopLoading();
                            if (resultCode == Constant.RESULT_SUCCESS) {
                                // 成功
                                loginViewModel.commit(username, password);
                                Constant.currentUser = user;
                                setResult(RESULT_OK);
                                LoginActivity.this.finish();
                            } else if (resultCode == Constant.USER_NOT_EXISTS) {
                                // 用户不存在
                                activityLoginBinding.accountTextInputLayout.setError("不存在该用户");
                            } else {
                                // 密码错误
                                activityLoginBinding.passwordTextInputLayout.setError("密码错误");
                            }
                        }
                    });
                }
                activityLoginBinding.loginBtn.onStartLoading();
            }
        });

        activityLoginBinding.textView2.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("WrongConstant")
            @Override
            public void onClick(View view) {
                if (activityLoginBinding.textView2.getText().equals("没有账号？")) {
                    activityLoginBinding.rePasswordTextInputLayout.setVisibility(0);
                    activityLoginBinding.loginBtn.setButtonText("注册");
                    activityLoginBinding.textView2.setText("已有账号？");
                    activityLoginBinding.editPassword.setText("");
                    activityLoginBinding.editUsername.setText("");
                } else {
                    activityLoginBinding.rePasswordTextInputLayout.setVisibility(8);
                    activityLoginBinding.loginBtn.setButtonText("登陆");
                    activityLoginBinding.textView2.setText("没有账号？");
                }
            }
        });
    }
}