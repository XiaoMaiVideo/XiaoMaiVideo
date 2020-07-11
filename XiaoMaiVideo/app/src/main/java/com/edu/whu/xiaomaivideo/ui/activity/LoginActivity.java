package com.edu.whu.xiaomaivideo.ui.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;

import com.edu.whu.xiaomaivideo.R;
import com.edu.whu.xiaomaivideo.databinding.ActivityLoginBinding;
import com.edu.whu.xiaomaivideo.viewModel.LoginViewModel;

import java.util.Objects;

import com.edu.whu.xiaomaivideo.util.Constant;


public class LoginActivity extends AppCompatActivity {
    private String username;
    private String password;
    private String rePassword;
    ActivityLoginBinding activityLoginBinding;
    LoginViewModel loginViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        loginViewModel = new ViewModelProvider(Objects.requireNonNull(this)).get(LoginViewModel.class);
        activityLoginBinding = DataBindingUtil.setContentView(this,R.layout.activity_login);
        activityLoginBinding.setViewmodel(loginViewModel);
        activityLoginBinding.setLifecycleOwner(this);

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

        loginViewModel.responseCode.observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(Integer aInteger) {
                if (aInteger != -1) {
                    loginViewModel.responseCode.postValue(-1);
                    // 在注册页面
                    if (activityLoginBinding.textView2.getText().equals("已有账号？")) {
                        // 注册成功
                        if (aInteger == Constant.RESULT_SUCCESS) {
                            activityLoginBinding.textView2.performClick();
                        }
                        // 用户已存在
                        else if (aInteger == Constant.USER_ALREADY_EXISTS) {
                            // 发个Toast
                            activityLoginBinding.accountTextInputLayout.setError("用户名已存在");
                        }
                        // 注册失败
                        else {

                        }
                    }
                    // 在登录页面
                    else {
                        // 登录成功
                        if (aInteger == Constant.RESULT_SUCCESS) {
                            setResult(RESULT_OK);
                            LoginActivity.this.finish();
                        }
                        // 账户不存在
                        else if (aInteger == Constant.USER_NOT_EXISTS) {
                            // 发个Toast
                            activityLoginBinding.accountTextInputLayout.setError("不存在该用户");
                        }
                        // 密码错误
                        else {
                            // 发个Toast
                            activityLoginBinding.passwordTextInputLayout.setError("密码错误");
                        }
                    }
                }
            }
        });

        activityLoginBinding.loginBtn.setOnClickListener(new View.OnClickListener() {
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
                    rePassword=activityLoginBinding.editRePassword.getText().toString();
                    if (!rePassword.equals(password)) {
                        activityLoginBinding.rePasswordTextInputLayout.setError("两次密码不相同");
                        return;
                    }
                    // 注册
                    else {
                        loginViewModel.sendRegisterRequest(username,password);
                    }
                }
                // 登录
                else {
                    loginViewModel.sendLoginRequest(username,password);
                }

            }
        });

        activityLoginBinding.textView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (activityLoginBinding.textView2.getText().equals("没有账号？")){
                    activityLoginBinding.rePasswordTextInputLayout.setVisibility(0);
                    activityLoginBinding.loginBtn.setText("注册");
                    activityLoginBinding.textView2.setText("已有账号？");
                    activityLoginBinding.editPassword.setText("");
                    activityLoginBinding.editUsername.setText("");
                }
                else {
                    activityLoginBinding.rePasswordTextInputLayout.setVisibility(8);
                    activityLoginBinding.loginBtn.setText("登陆");
                    activityLoginBinding.textView2.setText("没有账号？");
                }
            }
        });

    }
}