/**
 * Author: 张俊杰
 * Create Time: 2020/7/20
 * Update Time: 2020/7/22
 */

package com.edu.whu.xiaomaivideo.ui.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;


import android.os.Bundle;
import android.view.View;

import com.edu.whu.xiaomaivideo.R;
import com.edu.whu.xiaomaivideo.adapter.ChatAdapter;
import com.edu.whu.xiaomaivideo.databinding.ActivityChatBinding;
import com.edu.whu.xiaomaivideo.viewModel.ChatViewModel;

import java.util.Objects;

public class ChatActivity extends AppCompatActivity {
    ChatViewModel chatViewModel;
    ActivityChatBinding activityChatBinding;
    ChatAdapter chatAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        chatViewModel = new ViewModelProvider(Objects.requireNonNull(this)).get(ChatViewModel.class);
        chatAdapter=new ChatAdapter(this);
        //chatAdapter.setMsgs(chatViewModel.getMsgs().getValue());
        activityChatBinding= DataBindingUtil.setContentView(this,R.layout.activity_chat);
        activityChatBinding.setViewmodel(chatViewModel);

        activityChatBinding.setLifecycleOwner(this);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        activityChatBinding.recyclerView.setLayoutManager(linearLayoutManager);
        activityChatBinding.recyclerView.setAdapter(new ChatAdapter(this));

//        chatViewModel.getMsgs().observe(this, msgs -> {
//            chatAdapter.setMsgs(msgs);
//            chatAdapter.notifyDataSetChanged();
//        });

        activityChatBinding.ivConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String msg = activityChatBinding.etInputMessage.getText().toString().trim();

            }
        });
    }
}