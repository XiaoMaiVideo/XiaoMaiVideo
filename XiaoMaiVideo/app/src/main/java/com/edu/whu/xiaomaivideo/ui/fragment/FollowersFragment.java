/**
 * Author: 张俊杰、李季东
 * Create Time: 2020/7/17
 * Update Time: 2020/7/21
 */


package com.edu.whu.xiaomaivideo.ui.fragment;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.edu.whu.xiaomaivideo.R;
import com.edu.whu.xiaomaivideo.adapter.UserAdapter;
import com.edu.whu.xiaomaivideo.databinding.FollowersFragmentBinding;
import com.edu.whu.xiaomaivideo.model.User;
import com.edu.whu.xiaomaivideo.viewModel.FollowersViewModel;

import java.util.Objects;

public class FollowersFragment extends Fragment {

    private FollowersViewModel followersViewModel;
    private FollowersFragmentBinding followersFragmentBinding;
    private UserAdapter userAdapter;
    private User user;

    public FollowersFragment(User user){
        this.user=user;
    }

    public static FollowersFragment newInstance(User user) { return new FollowersFragment(user); }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        followersViewModel= new ViewModelProvider(Objects.requireNonNull(getActivity())).get(FollowersViewModel.class);
        followersFragmentBinding = DataBindingUtil.inflate(inflater, R.layout.followers_fragment,container,false);
        followersFragmentBinding.setViewmodel(followersViewModel);
        followersFragmentBinding.setLifecycleOwner(getActivity());



        followersFragmentBinding.recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        userAdapter = new UserAdapter(getActivity(), true);
        followersFragmentBinding.recyclerView.setAdapter(userAdapter);

        followersViewModel.getFollowers().observe(getViewLifecycleOwner(),users -> {
            userAdapter.setUsers(user.getFollowers());
            userAdapter.notifyDataSetChanged();
        });

        return followersFragmentBinding.getRoot();
    }

}