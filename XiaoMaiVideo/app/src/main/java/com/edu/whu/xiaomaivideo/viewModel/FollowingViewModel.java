/**
 * Author: 张俊杰
 * Create Time: 2020/7/17
 * Update Time: 2020/7/17
 */


package com.edu.whu.xiaomaivideo.viewModel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.edu.whu.xiaomaivideo.model.User;
import com.edu.whu.xiaomaivideo.util.Constant;

import java.util.List;

public class FollowingViewModel extends ViewModel {
    MutableLiveData<List<User>> followering;

    public FollowingViewModel() {
        followering=new MutableLiveData<>();
    }

    public MutableLiveData<List<User>> getFollowers() {
        List<User> users= Constant.currentUser.getFollowing();
        followering.setValue(users);
        return followering;
    }
}