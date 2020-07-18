/**
 * Author: 叶俊豪
 * Create Time: 2020/7/15
 * Update Time: 2020/7/15
 */

package com.edu.whu.xiaomaivideo.restservice;

import android.annotation.SuppressLint;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.edu.whu.xiaomaivideo.model.Movie;
import com.edu.whu.xiaomaivideo.model.User;
import com.edu.whu.xiaomaivideo.restcallback.MovieRestCallback;
import com.edu.whu.xiaomaivideo.restcallback.UserRestCallback;
import com.edu.whu.xiaomaivideo.util.Constant;
import com.edu.whu.xiaomaivideo.util.HttpUtil;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

public class MovieRestService {
    static final String TAG = "MovieRestService";
    public static void getMovieByID(final long movieId, final UserRestCallback restCallback) {

    }

    public static void getMovies(final int pageNum, final MovieRestCallback restCallback) {
        new AsyncTask<Long, Integer, String>() {
            MovieRestCallback movieRestCallback = restCallback;
            @Override
            protected String doInBackground(Long... number) {
                // page表示第几页（从0开始），total表示一页要多少个（这个要固定），发上去以后就会按total数进行分页，取第page个页返回
                // URL参数拼接用下面的方法
                Uri.Builder builder = Uri.parse(Constant.BASEURL+"getMovies").buildUpon();
                builder.appendQueryParameter("page", String.valueOf(pageNum));
                builder.appendQueryParameter("total", Constant.PAGE_LIMIT);
                return HttpUtil.sendGetRequest(builder.toString());
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                Log.e(TAG, s);
                JSONObject jsonObject = JSON.parseObject(s);
                int responseNum = jsonObject.getInteger("code");
                JSONObject dataObject = jsonObject.getJSONObject("data");
                JSONArray jsonArray = dataObject.getJSONArray("content");
                List<Movie> movies = new ArrayList<>();
                for (int i=0;i<jsonArray.size();i++) {
                    JSONObject jsonObj = jsonArray.getJSONObject(i);
                    Movie movie = JSON.toJavaObject(jsonObj, Movie.class);
                    movies.add(movie);
                }
                movieRestCallback.onSuccess(responseNum, movies);
            }
        }.execute();
    }

    public static void getMoviesByCategories(final String type, final int pageNum, final MovieRestCallback restCallback) {
        new AsyncTask<Long, Integer, String>() {
            MovieRestCallback movieRestCallback = restCallback;
            @Override
            protected String doInBackground(Long... number) {
                // page表示第几页（从0开始），total表示一页要多少个（这个要固定），发上去以后就会按total数进行分页，取第page个页返回
                // URL参数拼接用下面的方法
                Uri.Builder builder = Uri.parse(Constant.BASEURL+"getMoviesByCategoriesLike").buildUpon();
                builder.appendQueryParameter("page", String.valueOf(pageNum));
                builder.appendQueryParameter("total", Constant.PAGE_LIMIT);
                builder.appendQueryParameter("categories", type);
                return HttpUtil.sendGetRequest(builder.toString());
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                Log.e(TAG, s);
                JSONObject jsonObject = JSON.parseObject(s);
                int responseNum = jsonObject.getInteger("code");
                JSONObject dataObject = jsonObject.getJSONObject("data");
                JSONArray jsonArray = dataObject.getJSONArray("content");
                List<Movie> movies = new ArrayList<>();
                for (int i=0;i<jsonArray.size();i++) {
                    JSONObject jsonObj = jsonArray.getJSONObject(i);
                    Movie movie = JSON.toJavaObject(jsonObj, Movie.class);
                    movies.add(movie);
                }
                movieRestCallback.onSuccess(responseNum, movies);
            }
        }.execute();
    }
}
