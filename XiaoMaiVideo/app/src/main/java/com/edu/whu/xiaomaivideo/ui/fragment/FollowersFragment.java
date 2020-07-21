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
import com.edu.whu.xiaomaivideo.adapter.EditUserInfoAdapter;
import com.edu.whu.xiaomaivideo.adapter.FollowersAndFollowingAdapter;
import com.edu.whu.xiaomaivideo.databinding.FollowersFragmentBinding;
import com.edu.whu.xiaomaivideo.model.User;
import com.edu.whu.xiaomaivideo.ui.activity.EditUserInfoActivity;
import com.edu.whu.xiaomaivideo.viewModel.FindViewModel;
import com.edu.whu.xiaomaivideo.viewModel.FollowersViewModel;
import com.edu.whu.xiaomaivideo.viewModel.FollowingViewModel;
import com.lxj.xpopup.XPopup;
import com.lxj.xpopupext.listener.CityPickerListener;
import com.lxj.xpopupext.listener.TimePickerListener;
import com.lxj.xpopupext.popup.CityPickerPopup;
import com.lxj.xpopupext.popup.TimePickerPopup;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;

public class FollowersFragment extends Fragment {

    private FollowersViewModel followersViewModel;
    private FollowersFragmentBinding followersFragmentBinding;
    private FollowersAndFollowingAdapter followersAndFollowingAdapter;
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
        followersAndFollowingAdapter = new FollowersAndFollowingAdapter(getActivity(), new FollowersAndFollowingAdapter.OnItemClickListener() {

            @Override
            public void onClick(int pos) {

            }
        },true);
        followersFragmentBinding.recyclerView.setAdapter(followersAndFollowingAdapter);

        followersViewModel.getFollowers().observe(getViewLifecycleOwner(),users -> {
            followersAndFollowingAdapter.setUsers(user.getFollowers());
            followersAndFollowingAdapter.notifyDataSetChanged();
        });

        return followersFragmentBinding.getRoot();
    }

}