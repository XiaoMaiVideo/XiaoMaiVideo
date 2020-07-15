package com.edu.whu.xiaomaivideo.ui.activity;

import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.edu.whu.xiaomaivideo.R;
import com.edu.whu.xiaomaivideo.adapter.MentionedAdapter;
import com.edu.whu.xiaomaivideo.databinding.ActivityMentionedBinding;
import com.edu.whu.xiaomaivideo.viewModel.MentionedModel;

import java.util.Objects;
/**
 * Author: 李季东
 * Create Time: 2020/7/15
 * Update Time: 2020/7/15
 * 被@到的消息界面
 */
public class MessageMentionedActivity extends AppCompatActivity {
    MentionedModel mentionedModel;
    ActivityMentionedBinding activityMentionedBinding;
    MentionedAdapter mAdapter;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       // setContentView(R.layout.activity_mentioned);
        mentionedModel =new ViewModelProvider(Objects.requireNonNull(this)).get(MentionedModel.class);
        activityMentionedBinding = DataBindingUtil.setContentView(this,R.layout.activity_mentioned);
        activityMentionedBinding.setViewmodel(mentionedModel);
        activityMentionedBinding.setLifecycleOwner(this);
        initAdapter();
    }
    private void initAdapter() {
        mAdapter=new MentionedAdapter(this, new MentionedAdapter.OnItemClickListener()
        {
            @Override
            public void onClick(int pos)
            {
                Toast.makeText(MessageMentionedActivity.this, "click..." + pos, Toast.LENGTH_SHORT).show();
            }
        });
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        activityMentionedBinding.recyclerViewMentioned.setLayoutManager(linearLayoutManager);
        activityMentionedBinding.recyclerViewMentioned.setAdapter(mAdapter);
    }
}
