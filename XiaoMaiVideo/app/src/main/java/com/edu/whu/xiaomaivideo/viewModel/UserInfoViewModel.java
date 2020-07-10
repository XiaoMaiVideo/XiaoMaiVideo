package com.edu.whu.xiaomaivideo.viewModel;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

public class UserInfoViewModel extends AndroidViewModel {
    private SharedPreferences sp;
    private SharedPreferences.Editor editor;
    private MutableLiveData<String> username;

    public MutableLiveData<String> getUsername() {
        username.setValue(sp.getString("username",""));
        return username;
    }

    public UserInfoViewModel(@NonNull Application application) {
        super(application);
        username=new MutableLiveData<>();
        sp = getApplication().getSharedPreferences("data", Context.MODE_PRIVATE);
        editor=sp.edit();
    }
}
