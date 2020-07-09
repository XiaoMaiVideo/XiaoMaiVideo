package com.edu.whu.xiaomaivideo.viewModel;

import android.content.Intent;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.edu.whu.xiaomaivideo.util.Constant;
import com.edu.whu.xiaomaivideo.util.HttpUtil;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.RequestBody;
import okhttp3.Response;

public class HomeViewModel extends ViewModel {

    public  MutableLiveData<String> mText;

    public HomeViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is home fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }

    // 使用HttpUtil访问网络的例子，仅供参考，没有实际用处
    public void sendPostRequestAsyncExample() {
        String account="", encryptedPassword="";
        RequestBody requestBody = new FormBody.Builder()
                .add("account", account)
                .add("password", encryptedPassword)
                .build();
        String url = Constant.BASEURL+"RegisterServlet";
        HttpUtil.sendPostRequest(url, requestBody, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                // 请求失败的回调
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                // 请求成功的回调，已经导入了fastjson包用于处理JSON
                String responseData = response.body().string();
                JSONObject jsonObject = JSON.parseObject(responseData);
                int responseNum = jsonObject.getInteger("Result");
                // ……
            }
        });
    }
}