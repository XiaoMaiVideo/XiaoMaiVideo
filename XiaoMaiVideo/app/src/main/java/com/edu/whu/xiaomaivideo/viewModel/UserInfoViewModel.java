/**
 * Author: 张俊杰、叶俊豪
 * Create Time: 2020/7/10
 * Update Time: 2020/7/12
 */

package com.edu.whu.xiaomaivideo.viewModel;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.edu.whu.xiaomaivideo.model.User;

public class UserInfoViewModel extends AndroidViewModel {
    // private SharedPreferences sp;
    // private SharedPreferences.Editor editor;
    // private MutableLiveData<String> username;
    public MutableLiveData<User> user;

    public LiveData<User> getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user.setValue(user);
    }

    public UserInfoViewModel(@NonNull Application application) {
        super(application);
        user = new MutableLiveData<>();
        // username=new MutableLiveData<>();
        // sp = getApplication().getSharedPreferences("data", Context.MODE_PRIVATE);
        // editor=sp.edit();
    }
}
