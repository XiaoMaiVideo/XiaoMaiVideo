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
import com.alibaba.fastjson.serializer.PropertyFilter;
import com.alibaba.fastjson.serializer.SimplePropertyPreFilter;
import com.edu.whu.xiaomaivideo.model.Movie;
import com.edu.whu.xiaomaivideo.model.User;
import com.edu.whu.xiaomaivideo.restcallback.MovieRestCallback;
import com.edu.whu.xiaomaivideo.restcallback.RestCallback;
import com.edu.whu.xiaomaivideo.restcallback.UserRestCallback;
import com.edu.whu.xiaomaivideo.util.Constant;
import com.edu.whu.xiaomaivideo.util.HttpUtil;

public class UserRestService {
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

    public static void getUserByID(final long userId, final RestCallback restCallback) {
        new AsyncTask<Long, Integer, String>() {
            RestCallback callback = restCallback;
            @Override
            protected String doInBackground(Long... number) {
                String url = Constant.BASEURL+"user/"+ number[0];
                return HttpUtil.sendGetRequest(url);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                JSONObject jsonObject = JSON.parseObject(s);
                callback.onSuccess(jsonObject.getInteger("code"));
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
                user.setUsername(Constant.CurrentUser.getUsername());
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
    public static void addUserLike(final long movieId, final RestCallback restCallback) {
        new AsyncTask<Long, Integer, String>() {
            RestCallback callback = restCallback;
            @Override
            protected String doInBackground(Long... numbers) {
                // 发同步请求
                String url = Constant.BASEURL+"userLike";
                User user = new User();
                user.setUsername(Constant.CurrentUser.getUsername());
                user.addLikeMovies(new Movie(numbers[0]));
                String json = JSON.toJSONString(user);
                // Log.e("UserRestService发送", json);
                return HttpUtil.sendPostRequest(url, json);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                // Log.e("UserRestService", s);
                JSONObject jsonObject = JSON.parseObject(s);
                int responseNum = jsonObject.getInteger("code");
                callback.onSuccess(responseNum);
            }
        }.execute(movieId);
    }
}
