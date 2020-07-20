/**
 * Author: 叶俊豪
 * Create Time: 2020/7/11
 * Update Time: 2020/7/16
 */

package com.edu.whu.xiaomaivideo.restservice;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.edu.whu.xiaomaivideo.model.Movie;
import com.edu.whu.xiaomaivideo.model.User;
import com.edu.whu.xiaomaivideo.restcallback.RestCallback;
import com.edu.whu.xiaomaivideo.restcallback.UserRestCallback;
import com.edu.whu.xiaomaivideo.util.Constant;
import com.edu.whu.xiaomaivideo.util.HttpUtil;

import java.util.ArrayList;
import java.util.List;

public class UserRestService {
    static final String TAG = "UserRestService";
    @SuppressLint("StaticFieldLeak")
    public static void addUser(final User user, final RestCallback restCallback) {
        new AsyncTask<User, Integer, String>() {
            RestCallback callback = restCallback;
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
                callback.onSuccess(jsonObject.getInteger("code"));
            }
        }.execute(user);
    }

    public static void modifyUser(final User user, final RestCallback restCallback) {
        new AsyncTask<User, Integer, String>() {
            RestCallback Callback = restCallback;
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
                Callback.onSuccess(jsonObject.getInteger("code"));
            }
        }.execute(user);
    }

    public static void getUserByID(final long userId, final UserRestCallback restCallback) {
        new AsyncTask<Long, Integer, String>() {
            UserRestCallback callback = restCallback;
            @Override
            protected String doInBackground(Long... number) {
                String url = Constant.BASEURL+"user/"+ number[0];
                return HttpUtil.sendGetRequest(url);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                JSONObject jsonObject = JSON.parseObject(s);
                int responseNum = jsonObject.getInteger("code");
                User user = jsonObject.getObject("data", User.class);
                callback.onSuccess(responseNum, user);
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
                Log.e("UserRestService", s);
                JSONObject jsonObject = JSON.parseObject(s);
                int responseNum = jsonObject.getInteger("code");
                User user = jsonObject.getObject("data", User.class);
                userRestCallback.onSuccess(responseNum, user);
            }
        }.execute(user);
    }

    @SuppressLint("StaticFieldLeak")
    public static void addUserMovie(final Movie movie, final RestCallback restCallback) {
        new AsyncTask<Movie, Integer, String>() {
            RestCallback callback = restCallback;
            @Override
            protected String doInBackground(Movie... movies) {
                // 发同步请求
                String url = Constant.BASEURL+"userMovies";
                User user = new User();
                user.setUsername(Constant.currentUser.getUsername());
                user.addMovies(movies[0]);
                String json = JSON.toJSONString(user);
                // Log.e("UserRestService发送", json);
                return HttpUtil.sendPostRequest(url, json);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                Log.e("UserRestService", s);
                JSONObject jsonObject = JSON.parseObject(s);
                int responseNum = jsonObject.getInteger("code");
                callback.onSuccess(responseNum);
            }
        }.execute(movie);
    }

    @SuppressLint("StaticFieldLeak")
    public static void addUserShare(final long movieId, final String msg, final RestCallback restCallback) {
        new AsyncTask<Long, Integer, String>() {
            RestCallback callback = restCallback;
            @Override
            protected String doInBackground(Long... numbers) {
                // 发同步请求
                String url = Constant.BASEURL+"shareMovies";
                User user = new User();
                user.setUsername(Constant.currentUser.getUsername());
                user.addShareMovies(new Movie(numbers[0]), msg);
                String json = JSON.toJSONString(user);
                Log.e("UserRestService发送", json);
                return HttpUtil.sendPostRequest(url, json);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                Log.e("UserRestService", s);
                JSONObject jsonObject = JSON.parseObject(s);
                int responseNum = jsonObject.getInteger("code");
                callback.onSuccess(responseNum);
            }
        }.execute(movieId);
    }

    @SuppressLint("StaticFieldLeak")
    public static void getUserSimpleInfoList(final List<Long> userIds, final UserRestCallback restCallback) {

        new AsyncTask<Long, Integer, String>() {
            UserRestCallback userRestCallback = restCallback;
            @Override
            protected String doInBackground(Long... numbers) {
                // 发同步请求
                String url = Constant.BASEURL+"/user/getSimpleUserInfo";
                String json = JSON.toJSONString(numbers);
                Log.e(TAG, "发送，"+json);
                return HttpUtil.sendPostRequest(url, json);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                Log.e("UserRestService", s);
                JSONObject jsonObject = JSON.parseObject(s);
                int responseNum = jsonObject.getInteger("code");
                JSONArray jsonArray = jsonObject.getJSONArray("data");
                List<User> users = new ArrayList<>();
                for (int i=0;i<jsonArray.size();i++) {
                    JSONObject jsonObj = jsonArray.getJSONObject(i);
                    User user = JSON.toJavaObject(jsonObj, User.class);
                    users.add(user);
                }
                userRestCallback.onSuccess(responseNum, users);
                // int responseNum = jsonObject.getInteger("code");
                // List<User> users = new ArrayList<>();
                // userRestCallback.onSuccess();
            }
        }.execute(userIds.toArray(new Long[userIds.size()]));
    }
}
