package com.edu.whu.xiaomaivideo.util;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

import static com.edu.whu.xiaomaivideo.util.Constant.BASEURL;

public class HttpUtil {
    // 全局OKHttpClient对象
    static OkHttpClient okHttpClient = new OkHttpClient();

    // 发Post请求
    public static void sendPostRequest(String url, RequestBody requestBody, Callback callback) {
        Request request = new Request.Builder()
                .url(url)
                .post(requestBody)
                .build();
        Call call = okHttpClient.newCall(request);
        call.enqueue(callback);
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

    // 发Put请求
    public static void sendPutRequest(String url, RequestBody requestBody, Callback callback) {
        Request request = new Request.Builder()
                .url(url)
                .put(requestBody)
                .build();
        Call call = okHttpClient.newCall(request);
        call.enqueue(callback);
    }

    // TODO: 发视频的请求，这个函数没做完，要改一下
    public static void sendPhotoRequest(int userId, String imagePath, String description, Callback callback) {
        String url = BASEURL + "ReceivePhotoServlet";
        File file = new File(imagePath);
        MultipartBody.Builder builder = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("userId", String.valueOf(userId))
                .addFormDataPart("description", getEncodedString(description))
                .addFormDataPart(
                        "img",
                        file.getName(),
                        RequestBody.create(MediaType.parse("image/jpg"), file)
                );
        RequestBody requestBody = builder.build();
        Request request = new Request.Builder()
                .url(url)
                .post(requestBody)
                .build();
        Call call = okHttpClient.newCall(request);
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
