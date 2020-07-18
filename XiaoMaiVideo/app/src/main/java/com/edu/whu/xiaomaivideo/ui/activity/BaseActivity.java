/**
 * author ；何慷
 * Create Time：2020/7/17
 */
package com.edu.whu.xiaomaivideo.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public abstract class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        initView();
        initData();
    }

    abstract int getLayoutId();

    abstract void initView();

    abstract void initData();

    protected void showToast(String msg){
        Toast.makeText(this,msg,Toast.LENGTH_LONG).show();
    }

    protected void startActivity(Class c){
        startActivity(new Intent(this,c));
    }

}
