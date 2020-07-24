/**
 * Author: 李季东
 * Create Time: 2020/7/10
 * Update Time: 2020/7/10
 */

package com.edu.whu.xiaomaivideo.viewModel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.scwang.smart.refresh.layout.api.RefreshLayout;

public class FriendViewModel extends ViewModel {
    public MutableLiveData<String> mText;
    public RefreshLayout refreshLayout;

    public FriendViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is friend fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}
