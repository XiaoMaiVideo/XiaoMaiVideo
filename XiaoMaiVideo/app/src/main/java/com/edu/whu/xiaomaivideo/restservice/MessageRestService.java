/**
 * Author: 叶俊豪
 * Create Time: 2020/7/23
 * Update Time: 2020/7/23
 */
package com.edu.whu.xiaomaivideo.restservice;

import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.edu.whu.xiaomaivideo.model.Message;
import com.edu.whu.xiaomaivideo.model.Movie;
import com.edu.whu.xiaomaivideo.restcallback.MessageRestCallback;
import com.edu.whu.xiaomaivideo.restcallback.MovieRestCallback;
import com.edu.whu.xiaomaivideo.util.Constant;
import com.edu.whu.xiaomaivideo.util.HttpUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MessageRestService {
    private static String TAG = "MessageRestService";
    public static void getMessages(final Long receiverId, final MessageRestCallback restCallback) {
        new AsyncTask<String, Integer, String>() {
            MessageRestCallback messageRestCallback = restCallback;
            @Override
            protected String doInBackground(String... strings) {
                Uri.Builder builder = Uri.parse(Constant.BASEURL+"/getMessages").buildUpon();
                builder.appendQueryParameter("receiverId", String.valueOf(receiverId));
                builder.appendQueryParameter("senderId", String.valueOf(Constant.currentUser.getUserId()));
                return HttpUtil.sendGetRequest(builder.toString());
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                Log.e(TAG, s);
                JSONObject jsonObject = JSON.parseObject(s);
                int responseNum = jsonObject.getInteger("code");
                JSONArray jsonArray = jsonObject.getJSONArray("data");
                List<Message> messages = new ArrayList<>();
                for (int i=0;i<jsonArray.size();i++) {
                    JSONObject jsonObj = jsonArray.getJSONObject(i);
                    Message message = JSON.toJavaObject(jsonObj, Message.class);
                    messages.add(message);
                }
                messageRestCallback.onSuccess(responseNum, messages);
            }
        }.execute();
    }
}
