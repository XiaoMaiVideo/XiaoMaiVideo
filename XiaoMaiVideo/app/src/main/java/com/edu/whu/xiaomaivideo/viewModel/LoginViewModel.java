package com.edu.whu.xiaomaivideo.viewModel;

import android.util.Log;

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

public class LoginViewModel extends ViewModel {

    public final MutableLiveData<Integer> responseCode = new MutableLiveData<>(-1);

    public void sendLoginRequest(String username, String password) {
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
            }
        });
    }

    public void sendRegisterRequest(String username, String password) {
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
            }
        });
    }
}
