/**
 * Author: 张俊杰、叶俊豪、付浩
 * Create Time: 2020/7/10
 * Update Time: 2020/7/19
 */

package com.edu.whu.xiaomaivideo.ui.fragment;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import android.graphics.Rect;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.edu.whu.xiaomaivideo.R;
import com.edu.whu.xiaomaivideo.databinding.UserLikedVideoFragmentBinding;
import com.edu.whu.xiaomaivideo.model.User;
import com.edu.whu.xiaomaivideo.viewModel.UserLikedVideoViewModel;

import androidx.recyclerview.widget.RecyclerView;

import com.edu.whu.xiaomaivideo.adapter.MovieAdapter;
import com.edu.whu.xiaomaivideo.model.Movie;
import com.edu.whu.xiaomaivideo.restcallback.MovieRestCallback;
import com.edu.whu.xiaomaivideo.restservice.MovieRestService;
import com.edu.whu.xiaomaivideo.widget.MovieRecyclerView;
import com.jiajie.load.LoadingDialog;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import cn.jzvd.Jzvd;
import cn.jzvd.JzvdStd;

public class UserLikedVideoFragment extends Fragment {

    private UserLikedVideoViewModel userLikedVideoViewModel;
    UserLikedVideoFragmentBinding fragmentUserLikedVideoBinding;
    List<Movie> movieList;
    MovieRecyclerView movieRecyclerView;
    User mUser;

    public UserLikedVideoFragment(User user) {
        mUser = user;
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        userLikedVideoViewModel = new ViewModelProvider(Objects.requireNonNull(getActivity())).get(UserLikedVideoViewModel.class);
        fragmentUserLikedVideoBinding = DataBindingUtil.inflate(inflater, R.layout.user_liked_video_fragment, container, false);
        fragmentUserLikedVideoBinding.setViewmodel(userLikedVideoViewModel);
        fragmentUserLikedVideoBinding.setLifecycleOwner(getActivity());
        movieList = mUser.getLikeMovies() == null? new ArrayList<>(): mUser.getLikeMovies();
        setRecyclerView();
        return fragmentUserLikedVideoBinding.getRoot();
    }

    private void setRecyclerView() {
        fragmentUserLikedVideoBinding.userLikedVideoFragmentRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        fragmentUserLikedVideoBinding.userLikedVideoFragmentRecyclerView.setAdapter(new MovieAdapter(getActivity(), movieList));
        movieRecyclerView = new MovieRecyclerView(fragmentUserLikedVideoBinding.userLikedVideoFragmentRecyclerView);
    }
}