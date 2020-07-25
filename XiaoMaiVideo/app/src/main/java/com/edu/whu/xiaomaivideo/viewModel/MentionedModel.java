package com.edu.whu.xiaomaivideo.viewModel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class MentionedModel extends ViewModel {
    public MutableLiveData<String> mText;

    public MentionedModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is Hot fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}
