/**
 * Author: 张俊杰
 * Create Time: 2020/7/17
 * Update Time: 2020/7/17
 */


package com.edu.whu.xiaomaivideo.ui.fragment;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.edu.whu.xiaomaivideo.R;
import com.edu.whu.xiaomaivideo.adapter.FollowersAndFollowingAdapter;
import com.edu.whu.xiaomaivideo.databinding.FollowersFragmentBinding;
import com.edu.whu.xiaomaivideo.databinding.FollowingFragmentBinding;
import com.edu.whu.xiaomaivideo.viewModel.FollowersViewModel;
import com.edu.whu.xiaomaivideo.viewModel.FollowingViewModel;

import java.util.Objects;

public class FollowingFragment extends Fragment {
    private FollowingViewModel followingViewModel;
    private FollowingFragmentBinding followingFragmentBinding;
    private FollowersAndFollowingAdapter followersAndFollowingAdapter;

    public static FollowingFragment newInstance() {
        return new FollowingFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        followingViewModel= new ViewModelProvider(Objects.requireNonNull(getActivity())).get(FollowingViewModel.class);
        followingFragmentBinding = DataBindingUtil.inflate(inflater, R.layout.following_fragment,container,false);
        followingFragmentBinding.setViewmodel(followingViewModel);
        followingFragmentBinding.setLifecycleOwner(getActivity());

        followingFragmentBinding.recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        followersAndFollowingAdapter = new FollowersAndFollowingAdapter(getActivity(), new FollowersAndFollowingAdapter.OnItemClickListener() {

            @Override
            public void onClick(int pos) {

            }
        },false);
        followingFragmentBinding.recyclerView.setAdapter(followersAndFollowingAdapter);

        followingViewModel.getFollowers().observe(getViewLifecycleOwner(),users -> {
            followersAndFollowingAdapter.setUsers(users);
            followersAndFollowingAdapter.notifyDataSetChanged();
        });

        return followingFragmentBinding.getRoot();
    }

}