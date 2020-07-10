package com.edu.whu.xiaomaivideo.viewModel;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

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
        editor=sp.edit();
    }

    public void sendLoginRequest(final String username, final String password) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("username", username);
        jsonObject.put("password", password);
        String json = jsonObject.toJSONString();
        String url = Constant.BASEURL+"user/verify";
        HttpUtil.sendPostRequest(url, json, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                // 请求失败的回调
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                // 请求成功的回调，已经导入了fastjson包用于处理JSON
                String responseData = response.body().string();
                JSONObject jsonObject = JSON.parseObject(responseData);
                int responseNum = jsonObject.getInteger("code");
                if (responseNum == Constant.RESULT_SUCCESS) {
                    Constant.CurrentUser = jsonObject.getObject("data", User.class);
                }
                responseCode.postValue(responseNum);
                editor.putString("username", username);
                editor.putString("password", password);
                editor.apply();
            }
        });
    }

    public void sendRegisterRequest(final String username, final String password) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("username", username);
        jsonObject.put("password", password);
        String json = jsonObject.toJSONString();
        String url = Constant.BASEURL+"user";
        HttpUtil.sendPostRequest(url, json, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                // 请求失败的回调
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                // 请求成功的回调，已经导入了fastjson包用于处理JSON
                String responseData = response.body().string();
                JSONObject jsonObject = JSON.parseObject(responseData);
                responseCode.postValue(jsonObject.getInteger("code"));
                editor.putString("username", username);
                editor.putString("password", password);
                editor.apply();
            }
        });
    }
}
