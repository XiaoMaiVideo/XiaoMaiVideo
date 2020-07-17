/**
 * Author: 张俊杰
 * Create Time: 2020/7/10
 * Update Time: 2020/7/10
 */

package com.edu.whu.xiaomaivideo.viewModel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class UserVideoWorksViewModel extends ViewModel {
    public MutableLiveData<String> mText;

    public UserVideoWorksViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is UserLikedVideo fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}

/*public class UserVideoWorksViewModel extends ViewModel {
    // TODO: Implement the ViewModel
}*/