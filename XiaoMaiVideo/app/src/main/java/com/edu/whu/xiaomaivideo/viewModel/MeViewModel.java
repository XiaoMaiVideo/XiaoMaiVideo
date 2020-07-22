/**
 * Author: 叶俊豪
 * Create Time: 2020/7/10
 * Update Time: 2020/7/11
 */

package com.edu.whu.xiaomaivideo.viewModel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.edu.whu.xiaomaivideo.model.User;
import com.edu.whu.xiaomaivideo.util.Constant;

public class MeViewModel extends ViewModel {
    public MutableLiveData<User> user;

    public MeViewModel() {
        user = new MutableLiveData<>();
        user.setValue(Constant.currentUser);
    }

    public LiveData<User> getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user.setValue(user);
    }
}
