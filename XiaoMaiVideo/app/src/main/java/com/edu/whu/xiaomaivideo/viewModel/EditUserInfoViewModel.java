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
import androidx.lifecycle.MutableLiveData;

import java.util.ArrayList;
import java.util.List;

public class EditUserInfoViewModel extends AndroidViewModel {
    static final String[] USER_ALL_INFO={"昵称", "性别", "个人简介"};

    private SharedPreferences sp;
    private SharedPreferences.Editor editor;

    MutableLiveData<List<InfoMap>> userInfoList;

    public EditUserInfoViewModel(@NonNull Application application) {
        super(application);
        sp=getApplication().getSharedPreferences("data", Context.MODE_PRIVATE);
        userInfoList=new MutableLiveData<>();
        editor=sp.edit();
    }

    public MutableLiveData<List<InfoMap>> getAllUserInfo() {
        List<InfoMap> list=new ArrayList<>();
        for (int i = 0; i < USER_ALL_INFO.length; i++) {
            InfoMap infoMap = new InfoMap(USER_ALL_INFO[i], sp.getString(USER_ALL_INFO[i], ""));
            list.add(infoMap);
        }
        userInfoList.setValue(list);
        return userInfoList;
    }

    public static class InfoMap {
        public String key;
        public String value;

        public InfoMap(String key, String value) {
            this.key = key;
            this.value = value;
        }
    }

}
