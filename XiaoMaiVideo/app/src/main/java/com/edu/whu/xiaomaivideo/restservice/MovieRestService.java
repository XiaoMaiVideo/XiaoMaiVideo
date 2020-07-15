/**
 * Author: 叶俊豪
 * Create Time: 2020/7/15
 * Update Time: 2020/7/15
 */

package com.edu.whu.xiaomaivideo.restservice;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.edu.whu.xiaomaivideo.model.Movie;
import com.edu.whu.xiaomaivideo.model.User;
import com.edu.whu.xiaomaivideo.restcallback.MovieRestCallback;
import com.edu.whu.xiaomaivideo.restcallback.UserRestCallback;
import com.edu.whu.xiaomaivideo.util.Constant;
import com.edu.whu.xiaomaivideo.util.HttpUtil;

public class MovieRestService {
    @SuppressLint("StaticFieldLeak")
    public static void addMovie(final Movie movie, final MovieRestCallback restCallback) {
        new AsyncTask<Movie, Integer, String>() {
            MovieRestCallback movieRestCallback = restCallback;
            @Override
            protected String doInBackground(Movie... movies) {
                // 发同步请求
                String url = Constant.BASEURL+"movie";
                String json = JSON.toJSONString(movies[0]);
                return HttpUtil.sendPostRequest(url, json);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                // Log.e("MovieRestService", s);
                JSONObject jsonObject = JSON.parseObject(s);
                int responseNum = jsonObject.getInteger("code");
                Movie movie = jsonObject.getObject("data", Movie.class);
                movieRestCallback.onSuccess(responseNum, movie);
            }
        }.execute(movie);
    }

    public static void getMovieByID(final long movieId, final UserRestCallback restCallback) {

    }

    public static void getAllMovies(final UserRestCallback restCallback) {

    }
}
