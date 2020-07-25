/**
 * Author: 叶俊豪、李季东
 * Create Time: 2020/7/15
 * Update Time: 2020/7/25
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
import java.util.Arrays;
import java.util.List;

public class MovieRestService {
    static final String TAG = "MovieRestService";
    public static void getMovieByID(final long movieId, final MovieRestCallback restCallback) {

        new AsyncTask<Long, Integer, String>() {
            MovieRestCallback movieRestCallback = restCallback;
            @Override
            protected String doInBackground(Long... number) {
                // URL参数拼接用下面的方法
                Uri.Builder builder = Uri.parse(Constant.BASEURL+"movie/"+movieId).buildUpon();
                builder.appendQueryParameter("userId", String.valueOf(Constant.currentUser.getUserId()));
                return HttpUtil.sendGetRequest(builder.toString());
            }
            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
//                返回数据的格式
//                {"isok":true,"code":200,"message":"success",
//                 "data":{"movieId":35,"publishTime":"2020年7月15日 21:41:03",
//                 "url":"http://139.224.133.166:8088/api/file/b2290876-d3f6-485e-b4be-841254670c7e.mp4",
//                 "description":"哈哈哈哈哈","categories":"音乐;美食;","likednum":5,"commentnum":2,"sharenum":0,
//                 "publisher":{"userId":26,"username":"yjh8","gender":"男","nickname":"My Classic",
//                 "avatar":"http://139.224.133.166:8088/api/file/d86c1ac6-6dc2-463b-a20b-8788936a1a56.jpg",
//                 "description":"一个很无聊的人","birthday":"2020年7月12日","area":"山西省 太原市","workplace":"春日花花幼儿园","shares":[]},
//                 "likers":[{"userId":28,"username":"1","gender":null,"nickname":null,
//                 "avatar":null,"description":null,"birthday":null,"area":null,"workplace":null,"shares":[]},
//                 {"userId":22,"username":"小麦","gender":"男","nickname":"fsq",
//                 "avatar":"http://139.224.133.166:8088/api/file/32fdb509-57ac-423c-80c8-ff0c9ae8ae93.jpg",
//                 "description":null,"birthday":"2000年8月16日","area":null,"workplace":null,"shares":[]},
//                 {"userId":25,"username":"test1","gender":null,"nickname":null,
//                 "avatar":"http://139.224.133.166:8088/api/file/db137849-f283-4d5b-9d10-4c8e18c60c95.jpg",
//                 description":null,"birthday":null,"area":null,"workplace":null,"shares":[]},
//                 {"userId":40,"username":"fuhao518","gender":null,"nickname":null,"avatar":null,
//                 "description":null,"birthday":null,"area":null,"workplace":null,"shares":[]},
//                 {"userId":28,"username":"1","gender":null,"nickname":null,"avatar":null,"description":null,
//                 "birthday":null,"area":null,"workplace":null,"shares":[]}],
//                 "comments":[{"commentId":43,"
//                 commenter":{"userId":28,"username":"1","gender":null,"nickname":null,"avatar":null
//                 ,"description":null,"birthday":null,"area":null,"workplace":null,"shares":[]},
//                 "time":"2020-07-18 16:40:31","msg":"gegedoushirencai"},
//                 {"commentId":46,
//                 "commenter":{"userId":28,"username":"1","gender":null,"nickname":null,"avatar":null,
//                 "description":null,"birthday":null,"area":null,"workplace":null,"shares":[]},"time":"2020-07-18 19:58:27","msg":"66666"}]}}
                Log.e(TAG, s);
                //JSON数据解码
                JSONObject jsonObject = JSON.parseObject(s);
                int responseNum = jsonObject.getInteger("code");
                JSONObject dataObject = jsonObject.getJSONObject("data");
                Movie movie = JSON.toJavaObject(dataObject, Movie.class);
                // 处理类别
                if (movie.getCategories() != null && !movie.getCategories().equals("")) {
                    // 类别不为空
                    String[] categoryArray = movie.getCategories().split(";");
                    List<String> categoryList = new ArrayList<>(Arrays.asList(categoryArray));
                    movie.setCategoryList(categoryList);
                }
                else {
                    // 类别为空
                    movie.setCategoryList(new ArrayList<>());
                }
                movieRestCallback.onSuccess(responseNum, movie);
            }
        }.execute();
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
                builder.appendQueryParameter("userId", String.valueOf(Constant.currentUser.getUserId()));
                Log.e("MovieRestService--getMovies发送", builder.toString()+"_");
                return HttpUtil.sendGetRequest(builder.toString());
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                Log.e(TAG, s);
                JSONObject jsonObject = JSON.parseObject(s);
                int responseNum = jsonObject.getInteger("code");
                JSONArray jsonArray = jsonObject.getJSONArray("data");
                List<Movie> movies = new ArrayList<>();
                for (int i=0;i<jsonArray.size();i++) {
                    JSONObject jsonObj = jsonArray.getJSONObject(i);
                    Movie movie = JSON.toJavaObject(jsonObj, Movie.class);
                    // 处理类别
                    if (movie.getCategories() != null && !movie.getCategories().equals("")) {
                        // 类别不为空
                        String[] categoryArray = movie.getCategories().split(";");
                        List<String> categoryList = new ArrayList<>(Arrays.asList(categoryArray));
                        movie.setCategoryList(categoryList);
                    }
                    else {
                        // 类别为空
                        movie.setCategoryList(new ArrayList<>());
                    }
                    movies.add(movie);
                }
                movieRestCallback.onSuccess(responseNum, movies);
            }
        }.execute();
    }

    //获取关注粉丝的视频
    public static void getRelatedMovies(final Long userId, final MovieRestCallback restCallback) {
        new AsyncTask<Long, Integer, String>() {
            MovieRestCallback movieRestCallback = restCallback;
            @Override
            protected String doInBackground(Long... number) {
                // page表示第几页（从0开始），total表示一页要多少个（这个要固定），发上去以后就会按total数进行分页，取第page个页返回
                // URL参数拼接用下面的方法
                Uri.Builder builder = Uri.parse(Constant.BASEURL+"getRelatedMoviesById").buildUpon();
                builder.appendQueryParameter("userId", String.valueOf(userId));
                return HttpUtil.sendGetRequest(builder.toString());
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                Log.e(TAG, s);
                //{"isok":true,"code":200,"message":"success","data":[]}
                JSONObject jsonObject = JSON.parseObject(s);
                int responseNum = jsonObject.getInteger("code");
                JSONArray jsonArray = jsonObject.getJSONArray("data");
                List<Movie> movies = new ArrayList<>();
                for (int i=0;i<jsonArray.size();i++) {
                    JSONObject jsonObj = jsonArray.getJSONObject(i);
                    Movie movie = JSON.toJavaObject(jsonObj, Movie.class);
                    // 处理类别
                    if (movie.getCategories() != null && !movie.getCategories().equals("")) {
                        // 类别不为空
                        String[] categoryArray = movie.getCategories().split(";");
                        List<String> categoryList = new ArrayList<>(Arrays.asList(categoryArray));
                        movie.setCategoryList(categoryList);
                    }
                    else {
                        // 类别为空
                        movie.setCategoryList(new ArrayList<>());
                    }
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
                builder.appendQueryParameter("userId", String.valueOf(Constant.currentUser.getUserId()));
                return HttpUtil.sendGetRequest(builder.toString());
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                Log.e(TAG, s);
                JSONObject jsonObject = JSON.parseObject(s);
                int responseNum = jsonObject.getInteger("code");
                JSONArray jsonArray = jsonObject.getJSONArray("data");
                List<Movie> movies = new ArrayList<>();
                for (int i=0;i<jsonArray.size();i++) {
                    JSONObject jsonObj = jsonArray.getJSONObject(i);
                    Movie movie = JSON.toJavaObject(jsonObj, Movie.class);
                    // 处理类别
                    if (movie.getCategories() != null && !movie.getCategories().equals("")) {
                        // 类别不为空
                        String[] categoryArray = movie.getCategories().split(";");
                        List<String> categoryList = new ArrayList<>(Arrays.asList(categoryArray));
                        movie.setCategoryList(categoryList);
                    }
                    else {
                        // 类别为空
                        movie.setCategoryList(new ArrayList<>());
                    }
                    movies.add(movie);
                }
                movieRestCallback.onSuccess(responseNum, movies);
            }
        }.execute();
    }

    public static void getMoviesByLocation(final String location, final int pageNum, final MovieRestCallback restCallback) {
        new AsyncTask<Long, Integer, String>() {
            MovieRestCallback movieRestCallback = restCallback;
            @Override
            protected String doInBackground(Long... number) {
                // page表示第几页（从0开始），total表示一页要多少个（这个要固定），发上去以后就会按total数进行分页，取第page个页返回
                // URL参数拼接用下面的方法
                Uri.Builder builder = Uri.parse(Constant.BASEURL+"getMoviesByLocation").buildUpon();
                builder.appendQueryParameter("page", String.valueOf(pageNum));
                builder.appendQueryParameter("total", Constant.PAGE_LIMIT);
                builder.appendQueryParameter("location", location);
                builder.appendQueryParameter("userId", String.valueOf(Constant.currentUser.getUserId()));
                return HttpUtil.sendGetRequest(builder.toString());
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                Log.e(TAG+"--Location", s);
                JSONObject jsonObject = JSON.parseObject(s);
                int responseNum = jsonObject.getInteger("code");
                JSONArray jsonArray = jsonObject.getJSONArray("data");
                List<Movie> movies = new ArrayList<>();
                for (int i=0;i<jsonArray.size();i++) {
                    JSONObject jsonObj = jsonArray.getJSONObject(i);
                    Movie movie = JSON.toJavaObject(jsonObj, Movie.class);
                    // 处理类别
                    if (movie.getCategories() != null && !movie.getCategories().equals("")) {
                        // 类别不为空
                        String[] categoryArray = movie.getCategories().split(";");
                        List<String> categoryList = new ArrayList<>(Arrays.asList(categoryArray));
                        movie.setCategoryList(categoryList);
                    }
                    else {
                        // 类别为空
                        movie.setCategoryList(new ArrayList<>());
                    }
                    movies.add(movie);
                }
                movieRestCallback.onSuccess(responseNum, movies);
            }
        }.execute();
    }

    public static void searchMovie(final String keyword, final MovieRestCallback restCallback) {
        new AsyncTask<String, Integer, String>() {
            MovieRestCallback movieRestCallback = restCallback;
            @Override
            protected String doInBackground(String... strings) {
                Uri.Builder builder = Uri.parse(Constant.BASEURL+"/getMoviesByDescriptionLike").buildUpon();
                builder.appendQueryParameter("page", "0");
                builder.appendQueryParameter("total", Constant.PAGE_LIMIT);
                builder.appendQueryParameter("description", strings[0]);
                builder.appendQueryParameter("userId", String.valueOf(Constant.currentUser.getUserId()));
                return HttpUtil.sendGetRequest(builder.toString());
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                Log.e(TAG, s);
                JSONObject jsonObject = JSON.parseObject(s);
                int responseNum = jsonObject.getInteger("code");
                JSONArray jsonArray = jsonObject.getJSONArray("data");
                List<Movie> movies = new ArrayList<>();
                for (int i=0;i<jsonArray.size();i++) {
                    JSONObject jsonObj = jsonArray.getJSONObject(i);
                    Movie movie = JSON.toJavaObject(jsonObj, Movie.class);
                    // 处理类别
                    if (movie.getCategories() != null && !movie.getCategories().equals("")) {
                        // 类别不为空
                        String[] categoryArray = movie.getCategories().split(";");
                        List<String> categoryList = new ArrayList<>(Arrays.asList(categoryArray));
                        movie.setCategoryList(categoryList);
                    }
                    else {
                        // 类别为空
                        movie.setCategoryList(new ArrayList<>());
                    }
                    movies.add(movie);
                }
                movieRestCallback.onSuccess(responseNum, movies);
            }
        }.execute(keyword);
    }
}
