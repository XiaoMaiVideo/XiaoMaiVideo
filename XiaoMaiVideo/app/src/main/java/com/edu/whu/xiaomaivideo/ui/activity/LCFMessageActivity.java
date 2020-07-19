package com.edu.whu.xiaomaivideo.ui.activity;

import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.edu.whu.xiaomaivideo.R;
import com.edu.whu.xiaomaivideo.adapter.LCFMessageAdapter;
import com.edu.whu.xiaomaivideo.databinding.ActivityLcfMessageBinding;
import com.edu.whu.xiaomaivideo.model.MessageVO;
import com.edu.whu.xiaomaivideo.model.MessageVOPool;
import com.edu.whu.xiaomaivideo.model.User;
import com.edu.whu.xiaomaivideo.restcallback.UserRestCallback;
import com.edu.whu.xiaomaivideo.restservice.UserRestService;
import com.edu.whu.xiaomaivideo.viewModel.MentionedModel;
import com.jiajie.load.LoadingDialog;

import org.litepal.LitePal;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
/**
 * Author: 李季东、叶俊豪
 * Create Time: 2020/7/15
 * Update Time: 2020/7/18
 * 点赞/评论/新粉丝的提醒页面
 */
public class LCFMessageActivity extends AppCompatActivity {
    MentionedModel mentionedModel;
    ActivityLcfMessageBinding activityLcfMessageBinding;
    LCFMessageAdapter mAdapter;
    List<User> oldUsers, newUsers;
    List<MessageVO> oldMessageVOs, newMessageVOs;
    String mType;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mentionedModel =new ViewModelProvider(Objects.requireNonNull(this)).get(MentionedModel.class);
        activityLcfMessageBinding = DataBindingUtil.setContentView(this,R.layout.activity_lcf_message);
        activityLcfMessageBinding.setViewmodel(mentionedModel);
        activityLcfMessageBinding.setLifecycleOwner(this);
        LoadingDialog dialog = new LoadingDialog.Builder(this).loadText("加载中...").build();
        dialog.show();
        mType = getIntent().getStringExtra("type");
        // 从本地数据库获取旧的
        List<MessageVO> tempNewMsgList = MessageVOPool.getMessageVOs(mType);
        newMessageVOs = new ArrayList<>();
        oldMessageVOs = new ArrayList<>();
        List<MessageVO> tempMsgList = LitePal.where("msgType = ?", mType).find(MessageVO.class);
        for (int i=tempMsgList.size()-1; i>=0; i--) {
            if (tempMsgList.size()-i-1<tempNewMsgList.size()) {
                newMessageVOs.add(tempMsgList.get(i));
            }
            else {
                oldMessageVOs.add(tempMsgList.get(i));
            }
        }
        // 从Pool获取新的
        Log.e("LCFMessageActivity", newMessageVOs.size()+"_");

        // 访问网络
        List<Long> userIds = new ArrayList<>();
        for (MessageVO messageVO: oldMessageVOs) {
            userIds.add(messageVO.getSenderId());
        }
        for (MessageVO messageVO: newMessageVOs) {
            userIds.add(messageVO.getSenderId());
            // Log.e("LCFMessageActivity添加", newMessageVOs.size()+"_");
        }
        UserRestService.getUserSimpleInfoList(userIds, new UserRestCallback() {
            @Override
            public void onSuccess(int resultCode, List<User> users) {
                oldUsers = new ArrayList<>();
                newUsers = new ArrayList<>();
                for (int i=0;i<oldMessageVOs.size();i++) {
                    oldUsers.add(users.get(i));
                }
                for (int i=oldMessageVOs.size();i<users.size();i++) {
                    newUsers.add(users.get(i));
                }
                Log.e("LCFMessageActivity-newUsers", newUsers.size()+"_");
                Log.e("LCFMessageActivity-newMessageVOs", newMessageVOs.size()+"_");
                initAdapter();
                dialog.dismiss();
                MessageVOPool.clear(mType);
            }
        });

    }
    private void initAdapter() {
        mAdapter = new LCFMessageAdapter(this, oldMessageVOs, newMessageVOs, oldUsers, newUsers, mType);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        activityLcfMessageBinding.recyclerViewMentioned.setLayoutManager(linearLayoutManager);
        activityLcfMessageBinding.recyclerViewMentioned.setAdapter(mAdapter);
        activityLcfMessageBinding.stickyLayout.setSticky(true);
    }
}
