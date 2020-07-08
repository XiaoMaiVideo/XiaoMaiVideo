package com.edu.whu.xiaomaivideo.ui.dashboard;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class DashboardViewModel extends ViewModel {

    public MutableLiveData<model> mText;
    public model md;

    public DashboardViewModel() {
        mText = new MutableLiveData<>();
        md=new model("This is dashboard fragment");
        mText.setValue(md);
    }
    public LiveData<model> getmText() {
        return mText;
    }
    public void setMsg(){
        md=new  model("测试");
        mText.setValue(md);
    }

    public class model{
        String msg;

        public String getMsg() {
            return msg;
        }

        model(String msg){
            this.msg=msg;
        }
    }
}