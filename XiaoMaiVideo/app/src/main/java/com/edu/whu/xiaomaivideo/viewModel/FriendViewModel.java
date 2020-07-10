package com.edu.whu.xiaomaivideo.viewModel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class FriendViewModel extends ViewModel {
    public MutableLiveData<String> mText;

    public FriendViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is friend fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}
