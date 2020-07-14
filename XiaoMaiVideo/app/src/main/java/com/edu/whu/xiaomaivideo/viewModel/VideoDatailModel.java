package com.edu.whu.xiaomaivideo.viewModel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

/**
 * Author: 李季东 张俊杰
 * Create Time: 2020/7/14
 * Update Time: 2020/7/14
 */
public class VideoDatailModel extends ViewModel {
    private MutableLiveData<String> mText;
     public  VideoDatailModel(){
         mText=new MutableLiveData<>();
         mText.setValue("this is a video detail activity");
     }
     public MutableLiveData<String> getmText() {return mText;}
}
