/**
 * Author: 叶俊豪
 * Create Time: 2020/7/16
 * Update Time: 2020/7/25
 */
package com.edu.whu.xiaomaivideo.ui.fragment;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Rect;
import android.os.Bundle;

import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.edu.whu.xiaomaivideo.R;
import com.edu.whu.xiaomaivideo.adapter.MovieAdapter;
import com.edu.whu.xiaomaivideo.model.Movie;
import com.edu.whu.xiaomaivideo.restcallback.MovieRestCallback;
import com.edu.whu.xiaomaivideo.restservice.MovieRestService;
import com.edu.whu.xiaomaivideo.util.CheckPermissionUtil;
import com.edu.whu.xiaomaivideo.util.Constant;
import com.edu.whu.xiaomaivideo.widget.MovieRecyclerView;
import com.jiajie.load.LoadingDialog;
import com.lxj.xpopup.XPopup;
import com.lxj.xpopup.core.BasePopupView;

import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.List;

import cn.jzvd.Jzvd;
import cn.jzvd.JzvdStd;

public class FindSubFragment extends Fragment {
    private static final String ARG_TYPE = "type";

    private String mType;
    private List<Movie> movieList;
    public RecyclerView mRecyclerView;
    private MovieRecyclerView movieRecyclerView;
    private View rootView;

    public FindSubFragment() {
        // Required empty public constructor
    }

    public static FindSubFragment newInstance(String type) {
        FindSubFragment fragment = new FindSubFragment();
        Bundle args = new Bundle();
        args.putString(ARG_TYPE, type);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mType = getArguments().getString(ARG_TYPE);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_find_sub, container, false);
        mRecyclerView = rootView.findViewById(R.id.findRecyclerView);
        // BasePopupView popupView = new XPopup.Builder(getActivity()).asLoading().setTitle("加载中...").show();
        if (mType.equals(Constant.RECOMMEND)) {
            // 推荐算法得到的影片
            MovieRestService.getMovies(0, new MovieRestCallback() {
                @Override
                public void onSuccess(int resultCode, List<Movie> movies) {
                    super.onSuccess(resultCode, movies);
                    movieList = movies;
                    setRecyclerView();
                    // popupView.dismiss();
                }
            });
        }
        else if (mType.equals(Constant.LOCAL_HOT)) {
            // 本地热点
            // 开了定位权限才行
            if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                    && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // 提示没有定位
                movieList = new ArrayList<>();
                Toast.makeText(getActivity(), "你未开启定位权限，不能查看本地热点哦！", Toast.LENGTH_LONG).show();
                setRecyclerView();
            }
            else {
                // 定位没准备好
                if (Constant.currentLocation.getValue().equals("")) {
                    Constant.currentLocation.observe(getActivity(), new Observer<String>() {
                        @Override
                        public void onChanged(String s) {
                            if (!s.equals("")) {
                                MovieRestService.getMoviesByLocation(s, 0, new MovieRestCallback() {
                                    @Override
                                    public void onSuccess(int resultCode, List<Movie> movies) {
                                        super.onSuccess(resultCode, movies);
                                        movieList = movies;
                                        setRecyclerView();
                                        // popupView.dismiss();
                                    }
                                });
                            }
                        }
                    });
                }
                // 定位准备好了
                else {
                    MovieRestService.getMoviesByLocation(Constant.currentLocation.getValue(), 0, new MovieRestCallback() {
                        @Override
                        public void onSuccess(int resultCode, List<Movie> movies) {
                            super.onSuccess(resultCode, movies);
                            movieList = movies;
                            setRecyclerView();
                            // popupView.dismiss();
                        }
                    });
                }
            }
        }
        return rootView;
    }

    private void setRecyclerView() {
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.setAdapter(new MovieAdapter(getActivity(), movieList));
        // 有操作就对movieRecyclerView做
        movieRecyclerView = new MovieRecyclerView(mRecyclerView);
    }


}
