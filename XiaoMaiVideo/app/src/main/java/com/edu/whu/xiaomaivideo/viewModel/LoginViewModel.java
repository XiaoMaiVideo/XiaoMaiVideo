package com.edu.whu.xiaomaivideo.viewModel;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

public class LoginViewModel extends AndroidViewModel {
    private MutableLiveData<String> username;
    private MutableLiveData<String> password;
    private SharedPreferences sp;
    private SharedPreferences.Editor editor;

    public LoginViewModel(@NonNull Application application) {
        super(application);
        username=new MutableLiveData<>();
        password=new MutableLiveData<>();
        sp = getApplication().getSharedPreferences("data", Context.MODE_PRIVATE);
        editor=sp.edit();
    }

    public MutableLiveData<String> getUsername() {
        try {
            if (sp.getString("password","") != null&&sp.getString("username","") != null) {
                username.setValue(sp.getString("username",""));
            }
        } catch (Exception e) {

        }
        return username;
    }

    public MutableLiveData<String> getPassword() {
        try {
            if (sp.getString("password","") != null&&sp.getString("username","") != null) {
                password.setValue(sp.getString("password",""));
            }
        } catch (Exception e) {

        }
        return password;
    }

    public void commit(String username,String password){
        editor.putString("username",username);
        editor.putString("password",password);
        editor.apply();
    }
}
