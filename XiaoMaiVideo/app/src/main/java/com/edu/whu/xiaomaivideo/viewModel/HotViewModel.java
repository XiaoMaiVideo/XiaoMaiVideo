/**
 * Author: 张俊杰
 * Create Time: 2020/7/10
 * Update Time: 2020/7/10
 */
package com.edu.whu.xiaomaivideo.viewModel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class HotViewModel extends ViewModel {
    public MutableLiveData<String> mText;

    public HotViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is Hot fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}
