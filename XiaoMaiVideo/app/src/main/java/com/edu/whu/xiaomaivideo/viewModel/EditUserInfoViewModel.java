/**
 * Author: 张俊杰、叶俊豪
 * Create Time: 2020/7/10
 * Update Time: 2020/7/14
 */

package com.edu.whu.xiaomaivideo.viewModel;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.edu.whu.xiaomaivideo.util.Constant;

import java.util.ArrayList;
import java.util.List;

public class EditUserInfoViewModel extends AndroidViewModel {
    static final String[] USER_ALL_INFO={"昵称", "个人简介", "工作单位", "性别", "生日", "地区"};

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
        List<InfoMap> list = new ArrayList<>();
        list.add(new InfoMap(USER_ALL_INFO[0], Constant.currentUser.getNickname()));
        list.add(new InfoMap(USER_ALL_INFO[1], Constant.currentUser.getDescription()));
        list.add(new InfoMap(USER_ALL_INFO[2], Constant.currentUser.getWorkplace()));
        list.add(new InfoMap(USER_ALL_INFO[3], Constant.currentUser.getGender()));
        list.add(new InfoMap(USER_ALL_INFO[4], Constant.currentUser.getBirthday()));
        list.add(new InfoMap(USER_ALL_INFO[5], Constant.currentUser.getArea()));
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
