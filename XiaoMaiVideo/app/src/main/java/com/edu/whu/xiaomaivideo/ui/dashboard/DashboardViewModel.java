package com.edu.whu.xiaomaivideo.ui.dashboard;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class DashboardViewModel extends ViewModel {

    private MutableLiveData<DashboardModel> mText;
    DashboardModel dashboardModel;

    public DashboardViewModel() {
        mText = new MutableLiveData<>();
        dashboardModel=new DashboardModel("This is dashboard fragment");
        mText.setValue(dashboardModel);
    }
    public LiveData<DashboardModel> getText() {
        return mText;
    }

    public void setMsg(String msg){
        dashboardModel=new DashboardModel(msg);
        mText.setValue(dashboardModel);
    }
}