package com.edu.whu.xiaomaivideo.ui.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;

import com.edu.whu.xiaomaivideo.R;
import com.edu.whu.xiaomaivideo.databinding.ActivityLoginBinding;
import com.edu.whu.xiaomaivideo.viewModel.LoginViewModel;

import java.util.Objects;


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
        activityLoginBinding.loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                username = activityLoginBinding.editUsername.getText().toString().trim();
                password = activityLoginBinding.editPassword.getText().toString();
                if (username.isEmpty()) activityLoginBinding.accountTextInputLayout.setError("用户名不能为空");
                if (password.isEmpty()) activityLoginBinding.passwordTextInputLayout.setError("密码不能为空");
                if (activityLoginBinding.textView2.getText().equals("已有账号？")){
                    rePassword=activityLoginBinding.editRePassword.getText().toString();
                    if (!activityLoginBinding.editRePassword.getText().toString().equals(password)) {
                        activityLoginBinding.rePasswordTextInputLayout.setError("两次密码不相同");
                        return;
                    }
                }

                if (username.isEmpty() || password.isEmpty()) {
                    return;
                }

                loginViewModel.commit(username,password);

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
                    activityLoginBinding.editPassword.setText("");
                    activityLoginBinding.editUsername.setText("");
                }
            }
        });
//        try {
//            if (sp.getString("password","") != null) {
//                activityLoginBinding.editPassword.setText(sp.getString("password",""));
//                //edit_username.setText(sp.getString("username",""));
//            }
//        } catch (Exception e) {
//
//        }

    }
}