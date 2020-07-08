package com.edu.whu.xiaomaivideo.ui.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.edu.whu.xiaomaivideo.R;

public class LoginActivity extends AppCompatActivity {
    private SharedPreferences sp;
    private SharedPreferences.Editor editor;
    private Button loginButton;
    private EditText edit_username;
    private EditText edit_password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        sp = this.getSharedPreferences("data", Context.MODE_PRIVATE);
        editor=sp.edit();
        loginButton=findViewById(R.id.button4);
        edit_password=findViewById(R.id.editTextForPassword);
        edit_username=findViewById(R.id.editTextForUserName);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editor.putString("username",edit_username.getText().toString());
                editor.putString("password",edit_password.getText().toString());
                editor.apply();
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