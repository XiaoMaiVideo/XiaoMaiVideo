package com.edu.whu.xiaomaivideo.ui.activity;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import com.edu.whu.xiaomaivideo.R;
import com.edu.whu.xiaomaivideo.databinding.ActivityVideoDetailBinding;
import com.edu.whu.xiaomaivideo.viewModel.LoginViewModel;
import com.edu.whu.xiaomaivideo.viewModel.VideoDatailModel;

import java.util.Objects;

/**
 * Author: 李季东
 * Create Time: 2020/7/14
 * Update Time: 2020/7/14
 * 视频详情页面
 */
public class VideoDetialActivity extends AppCompatActivity {

    VideoDatailModel videoDatailModel;
    ActivityVideoDetailBinding activityVideoDetailBinding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_detail);

        /*
        videoDatailModel =new ViewModelProvider(Objects.requireNonNull(this)).get(VideoDatailModel.class);
        activityVideoDetailBinding = DataBindingUtil.setContentView(this,R.layout.activity_video_detail);
        activityVideoDetailBinding.setViewmodel(videoDatailModel);
        activityVideoDetailBinding.setLifecycleOwner(this);
        */
    }

}
