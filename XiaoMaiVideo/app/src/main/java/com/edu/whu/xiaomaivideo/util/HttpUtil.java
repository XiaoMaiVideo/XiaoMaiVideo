/**
 * Author: 叶俊豪
 * Create Time: 2020/7/7
 * Update Time: 2020/7/16
 */

package com.edu.whu.xiaomaivideo.util;

import android.util.Xml;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static com.edu.whu.xiaomaivideo.util.Constant.BASEURL;

public class HttpUtil {
    // 全局OKHttpClient对象
    private static OkHttpClient okHttpClient = new OkHttpClient();
    private static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

    // 发Post异步请求，传json串
    public static void sendPostRequest(String url, String json, Callback callback) {
        RequestBody requestBody = RequestBody.create(JSON, json);
        Request request = new Request.Builder()
                .url(url)
                .post(requestBody)
                .build();
        Call call = okHttpClient.newCall(request);
        call.enqueue(callback);
    }

    public static String sendPostRequest(String url, String json) {
        RequestBody requestBody = RequestBody.create(JSON, json);
        Request request = new Request.Builder()
                .url(url)
                .post(requestBody)
                .build();
        Call call = okHttpClient.newCall(request);
        try {
            Response response = call.execute();
            String responseData = response.body().string();
            return responseData;
        }
        catch (IOException e) {
            return Constant.RESPONSE_ERROR;
        }
    }

    public static String sendPutRequest(String url, String json) {
        RequestBody requestBody = RequestBody.create(JSON, json);
        Request request = new Request.Builder()
                .url(url)
                .put(requestBody)
                .build();
        Call call = okHttpClient.newCall(request);
        try {
            Response response = call.execute();
            String responseData = response.body().string();
            return responseData;
        }
        catch (IOException e) {
            return Constant.RESPONSE_ERROR;
        }
    }

    public static String sendGetRequest(String url) {
        Request request = new Request.Builder()
                .url(url)
                .get()
                .build();
        Call call = okHttpClient.newCall(request);
        try {
            Response response = call.execute();
            String responseData = response.body().string();
            return responseData;
        }
        catch (IOException e) {
            return Constant.RESPONSE_ERROR;
        }
    }

    // 发Get请求
    public static void sendGetRequest(String url, Callback callback) {
        Request request = new Request.Builder()
                .url(url)
                .build();
        Call call = okHttpClient.newCall(request);
        call.enqueue(callback);
    }

    // 发带参数Delete请求
    public static void sendDeleteRequest(String url, RequestBody requestBody, Callback callback) {
        Request request = new Request.Builder()
                .url(url)
                .delete(requestBody)
                .build();
        Call call = okHttpClient.newCall(request);
        call.enqueue(callback);
    }

    // 发不带参数Delete请求
    public static void sendDeleteRequest(String url, Callback callback) {
        Request request = new Request.Builder()
                .url(url)
                .delete()
                .build();
        Call call = okHttpClient.newCall(request);
        call.enqueue(callback);
    }

    public static void sendProfileRequest(String imagePath, Callback callback) {
        String url = BASEURL+"api";
        File file = new File(imagePath);
        MultipartBody.Builder builder = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart(
                        "file",
                        file.getName(),
                        RequestBody.create(MediaType.parse("image/jpg"), file)
                );
        RequestBody requestBody = builder.build();
        Request request = new Request.Builder()
                .url(url)
                .post(requestBody)
                .build();
        Call call =  okHttpClient.newCall(request);
        call.enqueue(callback);
    }

    public static void sendVideoRequest(String videoPath, Callback callback) {
        String url = BASEURL+"api";
        File file = new File(videoPath);
        MultipartBody.Builder builder = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart(
                        "file",
                        file.getName(),
                        RequestBody.create(MediaType.parse("application/octet-stream"), file)
                );
        RequestBody requestBody = builder.build();
        Request request = new Request.Builder()
                .url(url)
                .post(requestBody)
                .build();
        Call call =  okHttpClient.newCall(request);
        call.enqueue(callback);
    }

    // 字符串编码
    // 在用MultipartBody传请求的时候，如果请求里面有中文，要先调用这个函数编码，后端接收到信息以后译码，这样才能正常传中文
    private static String getEncodedString(String str) {
        try {
            return URLEncoder.encode(str, "UTF-8");
        }
        catch (UnsupportedEncodingException e) {
            return "";
        }
    }
}
