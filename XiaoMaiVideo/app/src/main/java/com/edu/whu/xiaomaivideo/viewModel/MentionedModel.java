/**
 * Author:
 * Create Time:
 * Update Time: 2020/7/25
 */
package com.edu.whu.xiaomaivideo.viewModel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class MentionedModel extends ViewModel {
    private MutableLiveData<String> mText;
    public  MentionedModel(){
        mText=new MutableLiveData<>();
        mText.setValue("this is a mentioned activity");
    }
    public MutableLiveData<String> getmText() {return mText;}
}
