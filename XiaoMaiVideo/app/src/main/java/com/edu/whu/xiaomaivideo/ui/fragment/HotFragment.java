/**
 * Author: 付浩、叶俊豪、李季东
 * Create Time: 2020/7/9
 * Update Time: 2020/7/24
 */

package com.edu.whu.xiaomaivideo.ui.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.fastjson.JSON;
import com.downloader.Error;
import com.downloader.OnDownloadListener;
import com.downloader.OnProgressListener;
import com.downloader.PRDownloader;
import com.downloader.Progress;
import com.edu.whu.xiaomaivideo.R;
import com.edu.whu.xiaomaivideo.adapter.MovieAdapter;
import com.edu.whu.xiaomaivideo.databinding.FragmentHotBinding;
import com.edu.whu.xiaomaivideo.model.MessageVO;
import com.edu.whu.xiaomaivideo.model.Movie;
import com.edu.whu.xiaomaivideo.model.User;
import com.edu.whu.xiaomaivideo.restcallback.MovieRestCallback;
import com.edu.whu.xiaomaivideo.restcallback.RestCallback;
import com.edu.whu.xiaomaivideo.restservice.MovieRestService;
import com.edu.whu.xiaomaivideo.viewModel.HotViewModel;
import com.edu.whu.xiaomaivideo.widget.MovieRecyclerView;
import com.jiajie.load.LoadingDialog;
import com.lxj.xpopup.XPopup;
import com.lxj.xpopup.core.BasePopupView;
import com.lxj.xpopup.interfaces.OnSelectListener;
import com.sackcentury.shinebuttonlib.ShineButton;
import com.scwang.smart.refresh.footer.ClassicsFooter;
import com.scwang.smart.refresh.header.ClassicsHeader;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnLoadMoreListener;
import com.scwang.smart.refresh.layout.listener.OnRefreshListener;

import org.greenrobot.eventbus.EventBus;
import org.jetbrains.annotations.NotNull;
import org.parceler.Parcels;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import cn.jzvd.Jzvd;
import cn.jzvd.JzvdStd;

public class  HotFragment extends Fragment {

    private HotViewModel hotViewModel;
    FragmentHotBinding fragmentHotBinding;
    List<Movie> movieList;
    MovieRecyclerView movieRecyclerView;
    MovieAdapter mAdapter;
    int currentMaxPageNum = 0;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        hotViewModel = new ViewModelProvider(Objects.requireNonNull(getActivity())).get(HotViewModel.class);
        fragmentHotBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_hot, container, false);
        fragmentHotBinding.setViewmodel(hotViewModel);
        fragmentHotBinding.setLifecycleOwner(getActivity());
        BasePopupView popupView = new XPopup.Builder(getActivity()).asLoading().setTitle("加载中...").show();
        MovieRestService.getMovies(currentMaxPageNum, new MovieRestCallback() {
            @Override
            public void onSuccess(int resultCode, List<Movie> movies) {
                super.onSuccess(resultCode, movies);
                movieList = movies;
                setRecyclerView();
                popupView.dismiss();
                currentMaxPageNum++;
            }
        });

        return fragmentHotBinding.getRoot();
    }

    private void setRecyclerView() {
        mAdapter = new MovieAdapter(getActivity(), movieList);
        fragmentHotBinding.recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        fragmentHotBinding.recyclerView.setAdapter(mAdapter);
        movieRecyclerView = new MovieRecyclerView(fragmentHotBinding.recyclerView);
        //刷新
        fragmentHotBinding.refreshLayout.setRefreshHeader(new ClassicsHeader(getActivity()));
        fragmentHotBinding.refreshLayout.setRefreshFooter(new ClassicsFooter(getActivity()));
        fragmentHotBinding.refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NotNull RefreshLayout refreshlayout) {
                MovieRestService.getMovies(0, new MovieRestCallback() {
                    @Override
                    public void onSuccess(int resultCode, List<Movie> movies) {
                        super.onSuccess(resultCode, movies);
                        List<Movie> newMovies = new ArrayList<>();
                        for (Movie movie: movies) {
                            if (movie.getMovieId() > movieList.get(0).getMovieId()) {
                                newMovies.add(movie);
                            }
                        }
                        movieList.addAll(0, newMovies);
                        mAdapter.notifyItemRangeInserted(0, newMovies.size());
                        refreshlayout.finishRefresh();
                    }
                });
            }
        });
        fragmentHotBinding.refreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NotNull RefreshLayout refreshlayout) {
                MovieRestService.getMovies(currentMaxPageNum, new MovieRestCallback() {
                    @Override
                    public void onSuccess(int resultCode, List<Movie> movies) {
                        super.onSuccess(resultCode, movies);
                        List<Movie> newMovies = new ArrayList<>();
                        for (Movie movie: movies) {
                            if (movie.getMovieId() < movieList.get(movieList.size()-1).getMovieId()) {
                                newMovies.add(movie);
                            }
                        }
                        movieList.addAll(newMovies);
                        mAdapter.notifyItemRangeInserted(movieList.size()-newMovies.size(), newMovies.size());
                        currentMaxPageNum++;
                        refreshlayout.finishLoadMore();
                    }
                });
            }
        });
    }
}
