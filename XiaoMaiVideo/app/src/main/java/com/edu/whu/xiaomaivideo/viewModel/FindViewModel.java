/**
 * Author: 张俊杰
 * Create Time: 2020/7/10
 * Update Time: 2020/7/10
 */

package com.edu.whu.xiaomaivideo.viewModel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class FindViewModel extends ViewModel {
    private MutableLiveData<String> mText;

    public FindViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("这是发现页面");
    }

    public LiveData<String> getText() {
        return mText;
    }
}
