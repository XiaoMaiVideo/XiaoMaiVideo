package com.edu.whu.xiaomaivideo.ui.fragment;

import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.edu.whu.xiaomaivideo.R;
import com.edu.whu.xiaomaivideo.viewModel.MyLikedVideoViewModel;

public class MyLikedVideoFragment extends Fragment {

    private MyLikedVideoViewModel mViewModel;

    public static MyLikedVideoFragment newInstance() {
        return new MyLikedVideoFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.my_liked_video_fragment, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(MyLikedVideoViewModel.class);
        // TODO: Use the ViewModel
    }

}