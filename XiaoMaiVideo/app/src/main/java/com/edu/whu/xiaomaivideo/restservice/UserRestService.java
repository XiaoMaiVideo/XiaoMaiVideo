/**
 * Author: 叶俊豪
 * Create Time: 2020/7/11
 * Update Time: 2020/7/11
 */

package com.edu.whu.xiaomaivideo.restservice;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.edu.whu.xiaomaivideo.model.User;
import com.edu.whu.xiaomaivideo.restcallback.UserRestCallback;
import com.edu.whu.xiaomaivideo.util.Constant;
import com.edu.whu.xiaomaivideo.util.HttpUtil;

public class UserRestService {
    @SuppressLint("StaticFieldLeak")
    public static void addUser(final User user, final UserRestCallback restCallback) {
        new AsyncTask<User, Integer, String>() {
            UserRestCallback userRestCallback = restCallback;
            @Override
            protected String doInBackground(User... users) {
                // 发同步请求
                String url = Constant.BASEURL+"user";
                String json = JSON.toJSONString(users[0]);
                return HttpUtil.sendPostRequest(url, json);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                JSONObject jsonObject = JSON.parseObject(s);
                userRestCallback.onSuccess(jsonObject.getInteger("code"));
            }
        }.execute(user);
    }

    public static void modifyUser(final User user, final UserRestCallback restCallback) {
        new AsyncTask<User, Integer, String>() {
            UserRestCallback userRestCallback = restCallback;
            @Override
            protected String doInBackground(User... users) {
                String url = Constant.BASEURL+"user";
                String json = JSON.toJSONString(users[0]);
                return HttpUtil.sendPutRequest(url, json);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                JSONObject jsonObject = JSON.parseObject(s);
                userRestCallback.onSuccess(jsonObject.getInteger("code"));
            }
        }.execute(user);
    }

    public static void getUserByID(final long userId, final UserRestCallback restCallback) {
        new AsyncTask<Long, Integer, String>() {
            UserRestCallback userRestCallback = restCallback;
            @Override
            protected String doInBackground(Long... number) {
                String url = Constant.BASEURL+"user/"+ number[0];
                return HttpUtil.sendGetRequest(url);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                JSONObject jsonObject = JSON.parseObject(s);
                userRestCallback.onSuccess(jsonObject.getInteger("code"));
            }
        }.execute(userId);
    }

    @SuppressLint("StaticFieldLeak")
    public static void verifyUser(final User user, final UserRestCallback restCallback) {
        new AsyncTask<User, Integer, String>() {
            UserRestCallback userRestCallback = restCallback;
            @Override
            protected String doInBackground(User... users) {
                String url = Constant.BASEURL+"user/verify";
                String json = JSON.toJSONString(users[0]);
                return HttpUtil.sendPostRequest(url, json);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                JSONObject jsonObject = JSON.parseObject(s);
                int responseNum = jsonObject.getInteger("code");
                User user = jsonObject.getObject("data", User.class);
                userRestCallback.onSuccess(responseNum, user);
            }
        }.execute(user);
    }
}
