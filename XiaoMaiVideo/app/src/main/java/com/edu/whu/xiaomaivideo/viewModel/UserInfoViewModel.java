/**
 * Author: 张俊杰、叶俊豪
 * Create Time: 2020/7/10
 * Update Time: 2020/7/15
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

import java.util.ArrayList;
import java.util.List;

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

    public List<String> getUserLabels() {
        List<String> labels = new ArrayList<>();
        if (user.getValue().getGender() != null && !user.getValue().getGender().equals("")) {
            labels.add(user.getValue().getGender());
        }
        if (user.getValue().getBirthday() != null && !user.getValue().getBirthday().equals("")) {
            labels.add("生日是"+user.getValue().getBirthday());
        }
        if (user.getValue().getArea() != null && !user.getValue().getArea().equals("")) {
            labels.add(user.getValue().getArea().trim());
        }
        if (user.getValue().getWorkplace() != null && !user.getValue().getWorkplace().equals("")) {
            labels.add(user.getValue().getWorkplace());
        }
        return labels;
    }
}
