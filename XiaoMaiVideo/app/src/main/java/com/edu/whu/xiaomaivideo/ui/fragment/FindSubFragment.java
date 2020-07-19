package com.edu.whu.xiaomaivideo.ui.fragment;

import android.graphics.Rect;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.edu.whu.xiaomaivideo.R;
import com.edu.whu.xiaomaivideo.adapter.MovieAdapter;
import com.edu.whu.xiaomaivideo.model.Movie;
import com.edu.whu.xiaomaivideo.restcallback.MovieRestCallback;
import com.edu.whu.xiaomaivideo.restservice.MovieRestService;
import com.edu.whu.xiaomaivideo.util.Constant;
import com.edu.whu.xiaomaivideo.widget.MovieRecyclerView;
import com.jiajie.load.LoadingDialog;

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
    public int firstVisibleItem = 0, lastVisibleItem = 0, VisibleCount = 0;
    public JzvdStd videoView;

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
        LoadingDialog dialog = new LoadingDialog.Builder(getActivity()).loadText("加载中...").build();
        dialog.show();
        if (mType.equals(Constant.RECOMMEND)) {
            // 推荐算法得到的影片
            MovieRestService.getMovies(0, new MovieRestCallback() {
                @Override
                public void onSuccess(int resultCode, List<Movie> movies) {
                    super.onSuccess(resultCode, movies);
                    movieList = movies;
                    setRecyclerView();
                    dialog.dismiss();
                }
            });
        }
        else if (mType.equals(Constant.LOCAL_HOT)) {
            // 本地热点
            MovieRestService.getMoviesByLocation("广西壮族自治区梧州市", 0, new MovieRestCallback() {
                @Override
                public void onSuccess(int resultCode, List<Movie> movies) {
                    super.onSuccess(resultCode, movies);
                    movieList = movies;
                    setRecyclerView();
                    dialog.dismiss();
                }
            });
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
