package com.edu.whu.xiaomaivideo.viewModel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class MessageViewModel extends ViewModel {

    public MutableLiveData<String> mText;

    public MessageViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("这是消息页面");
    }

    public LiveData<String> getmText() {
        return mText;
    }
    public void setMsg(){
        mText.setValue("测试");
    }
}