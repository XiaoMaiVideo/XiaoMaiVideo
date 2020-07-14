/**
 * Author: 叶俊豪、张俊杰
 * Create Time: 2020/7/8
 * Update Time: 2020/7/12
 */

package com.edu.whu.xiaomaivideo.viewModel;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.edu.whu.xiaomaivideo.model.User;
import com.edu.whu.xiaomaivideo.util.Constant;
import com.edu.whu.xiaomaivideo.util.HttpUtil;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class LoginViewModel extends AndroidViewModel {
    private SharedPreferences sp;
    private SharedPreferences.Editor editor;
    private MutableLiveData<String> username;
    private MutableLiveData<String> password;
    public final MutableLiveData<Integer> responseCode = new MutableLiveData<>(-1);

    public MutableLiveData<String> getUsername() {
        username.setValue(sp.getString("username",""));
        return username;
    }

    public MutableLiveData<String> getPassword() {
        password.setValue(sp.getString("password",""));
        return password;
    }

    @SuppressLint("CommitPrefEdits")
    public LoginViewModel(@NonNull Application application) {
        super(application);
        sp=getApplication().getSharedPreferences("data", Context.MODE_PRIVATE);
        username=new MutableLiveData<>();
        password=new MutableLiveData<>();
        username.setValue(sp.getString("username",""));
        password.setValue(sp.getString("password",""));
        editor = sp.edit();
    }

    public void commit(String username,String password){
        editor.putString("username", username);
        editor.putString("password", password);
        editor.apply();
    }
}
