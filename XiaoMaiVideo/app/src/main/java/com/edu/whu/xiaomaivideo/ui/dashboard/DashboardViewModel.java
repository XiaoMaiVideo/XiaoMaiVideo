package com.edu.whu.xiaomaivideo.ui.dashboard;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class DashboardViewModel extends ViewModel {

    public MutableLiveData<String> mText;

    public DashboardViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is dashboard fragment");
    }

    public LiveData<String> getmText() {
        return mText;
    }
    public void setMsg(){
        md=new  model("测试");
        mText.setValue(md);
    }
    public void setMsg(){
        mText.setValue("测试");
    }
}