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

public class FollowersViewModel extends ViewModel {
    MutableLiveData<List<User>> followers;

    public FollowersViewModel() {
        followers=new MutableLiveData<>();
    }

    public MutableLiveData<List<User>> getFollowers() {
        List<User> users=Constant.currentUser.getFollowers();
        followers.setValue(users);
        return followers;
    }
}