package com.edu.whu.xiaomaivideo.ui.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.edu.whu.xiaomaivideo.R;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import com.edu.whu.xiaomaivideo.util.Constant;
import com.edu.whu.xiaomaivideo.viewModel.LoginViewModel;


import java.util.Objects;


public class LoginActivity extends AppCompatActivity {
    private SharedPreferences sp;
    private SharedPreferences.Editor editor;
    private Button loginButton;
    private TextInputEditText edit_username;
    private TextInputEditText edit_password;
    private TextInputEditText edit_rePassword;
    private TextInputLayout accountTextInputLayout;
    private TextInputLayout passwordTextInputLayout;
    private TextInputLayout rePasswordTextInputLayout;
    private TextView textView;
    private String username;
    private String password;
    private String rePassword;

    LoginViewModel loginViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        sp = this.getSharedPreferences("data", Context.MODE_PRIVATE);
        editor=sp.edit();
        loginButton=findViewById(R.id.loginBtn);
        edit_password=findViewById(R.id.edit_password);
        edit_username=findViewById(R.id.edit_username);
        edit_rePassword=findViewById(R.id.edit_rePassword);
        accountTextInputLayout=findViewById(R.id.accountTextInputLayout);
        passwordTextInputLayout=findViewById(R.id.passwordTextInputLayout);
        rePasswordTextInputLayout=findViewById(R.id.rePasswordTextInputLayout);
        textView=findViewById(R.id.textView2);

        edit_rePassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                rePasswordTextInputLayout.setError("");
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        edit_password.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                passwordTextInputLayout.setError("");
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        edit_username.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                accountTextInputLayout.setError("");
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        loginViewModel = new ViewModelProvider(Objects.requireNonNull(this)).get(LoginViewModel.class);
        loginViewModel.responseCode.observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(Integer aInteger) {
                if (aInteger != -1) {
                    loginViewModel.responseCode.postValue(-1);
                    // 在注册页面
                    if (textView.getText().equals("已有账号？")) {
                        // 注册成功
                        if (aInteger == Constant.RESULT_SUCCESS) {
                            textView.performClick();
                        }
                        // 用户已存在
                        else if (aInteger == Constant.USER_ALREADY_EXISTS) {
                            // 发个Toast
                            Log.e("LoginActivity", "用户已存在");
                        }
                        // 注册失败
                        else {

                        }
                    }
                    // 在登录页面
                    else {
                        // 登录成功
                        if (aInteger == Constant.RESULT_SUCCESS) {
                            setResult(Constant.LOGIN_SUCCESS_RESULT);
                            LoginActivity.this.finish();
                        }
                        // 账户不存在
                        else if (aInteger == Constant.USER_NOT_EXISTS) {
                            // 发个Toast
                        }
                        // 密码错误
                        else {
                            // 发个Toast
                        }
                    }
                }
            }
        });

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                accountTextInputLayout.setError("");
                passwordTextInputLayout.setError("");
                rePasswordTextInputLayout.setError("");
                username = edit_username.getText().toString().trim();
                password = edit_password.getText().toString();
                if (username.isEmpty()) {
                    accountTextInputLayout.setError("用户名不能为空");
                    return;
                }
                if (password.isEmpty()) {
                    passwordTextInputLayout.setError("密码不能为空");
                    return;
                }


                // 注册
                if (textView.getText().equals("已有账号？")) {
                    rePassword=edit_rePassword.getText().toString();
                    if (!edit_rePassword.getText().toString().equals(password)) {
                        rePasswordTextInputLayout.setError("两次密码不相同");
                        return;
                    }
                    // 注册
                    else {
                        loginViewModel.sendRegisterRequest(username, password);
                    }
                }
                // 登录
                else {
                    loginViewModel.sendLoginRequest(username, password);
                }

                editor.putString("username",username);
                editor.putString("password",password);
                editor.apply();
            }
        });

        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (textView.getText().equals("没有账号？")){
                    rePasswordTextInputLayout.setVisibility(0);
                    loginButton.setText("注册");
                    textView.setText("已有账号？");
                    edit_password.setText("");
                    edit_username.setText("");
                }
                else {
                    rePasswordTextInputLayout.setVisibility(8);
                    loginButton.setText("登陆");
                    textView.setText("没有账号？");
                    edit_password.setText("");
                    edit_username.setText("");
                }
            }
        });
        try {
            if (sp.getString("password","") != null) {
                edit_password.setText(sp.getString("password",""));
                edit_username.setText(sp.getString("username",""));
            }
        } catch (Exception e) {

        }

    }
}