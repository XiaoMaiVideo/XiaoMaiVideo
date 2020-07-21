/**
 * Author: 李季东、张俊杰
 * Create Time: 2020/7/10
 * Update Time: 2020/7/14
 */

package com.edu.whu.xiaomaivideo.ui.fragment;

import android.graphics.Rect;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.edu.whu.xiaomaivideo.R;
import com.edu.whu.xiaomaivideo.adapter.MovieAdapter;
import com.edu.whu.xiaomaivideo.databinding.FragmentFriendBinding;
import com.edu.whu.xiaomaivideo.model.Movie;
import com.edu.whu.xiaomaivideo.restcallback.MovieRestCallback;
import com.edu.whu.xiaomaivideo.restservice.MovieRestService;
import com.edu.whu.xiaomaivideo.viewModel.FriendViewModel;
import com.edu.whu.xiaomaivideo.widget.MovieRecyclerView;
import com.jiajie.load.LoadingDialog;


import java.util.List;
import java.util.Objects;

import cn.jzvd.Jzvd;
import cn.jzvd.JzvdStd;

/**
 * Author: 李季东、付浩
 * Create Time: 2020/7/8
 * Update Time: 2020/7/19
 * 视频详情页面
 */
public class FriendFragment extends Fragment {

    private FriendViewModel friendViewModel;
    FragmentFriendBinding fragmentFriendBinding;
    List<Movie> movieList;
    MovieRecyclerView movieRecyclerView;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        friendViewModel = new ViewModelProvider(Objects.requireNonNull(getActivity())).get(FriendViewModel.class);
        fragmentFriendBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_friend, container, false);
        fragmentFriendBinding.setViewmodel(friendViewModel);
        fragmentFriendBinding.setLifecycleOwner(getActivity());
        LoadingDialog dialog = new LoadingDialog.Builder(getActivity()).loadText("加载中...").build();
        dialog.show();
        MovieRestService.getMovies(0, new MovieRestCallback() {
            @Override
            public void onSuccess(int resultCode, List<Movie> movies) {
                super.onSuccess(resultCode, movies);
                movieList = movies;
                setRecyclerView();
                dialog.dismiss();
            }
        });
        return fragmentFriendBinding.getRoot();
    }

    private void setRecyclerView() {
        fragmentFriendBinding.recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        fragmentFriendBinding.recyclerView.setAdapter(new MovieAdapter(getActivity(), movieList));
        movieRecyclerView = new MovieRecyclerView(fragmentFriendBinding.recyclerView);
    }
}
