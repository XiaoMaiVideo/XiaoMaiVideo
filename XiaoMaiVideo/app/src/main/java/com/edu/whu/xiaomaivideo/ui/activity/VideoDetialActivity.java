package com.edu.whu.xiaomaivideo.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Pair;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.edu.whu.xiaomaivideo.R;
import com.edu.whu.xiaomaivideo.adapter.CommentAdapter;
import com.edu.whu.xiaomaivideo.adapter.SettingsAdapter;
import com.edu.whu.xiaomaivideo.adapter.SettingsFriendAdpater;
import com.edu.whu.xiaomaivideo.databinding.ActivityVideoDetailBinding;
import com.edu.whu.xiaomaivideo.util.Constant;
import com.edu.whu.xiaomaivideo.viewModel.LoginViewModel;
import com.edu.whu.xiaomaivideo.viewModel.VideoDatailModel;

import java.net.URI;
import java.util.ArrayList;
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
    CommentAdapter mAdapter;
    //保存视频的URI
    URI uri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_detail);
/*
        videoDatailModel =new ViewModelProvider(Objects.requireNonNull(this)).get(VideoDatailModel.class);
        activityVideoDetailBinding = DataBindingUtil.setContentView(this,R.layout.activity_video_detail);
        activityVideoDetailBinding.setViewmodel(videoDatailModel);
        activityVideoDetailBinding.setLifecycleOwner(this);
        initAdapter();
        */


    }
    private void initAdapter() {
        mAdapter=new CommentAdapter(this, new CommentAdapter.OnItemClickListener()
        {
            @Override
            public void onClick(int pos)
            {
                Toast.makeText(VideoDetialActivity.this, "click..." + pos, Toast.LENGTH_SHORT).show();
            }
        });
        activityVideoDetailBinding.recyclerView2.setAdapter(mAdapter);
    }

}
