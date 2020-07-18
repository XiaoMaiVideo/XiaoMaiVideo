package com.edu.whu.xiaomaivideo.ui.activity;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.edu.whu.xiaomaivideo.R;
import com.edu.whu.xiaomaivideo.adapter.LSCMessageAdapter;
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
public class LSFMessageActivity extends AppCompatActivity {
    MentionedModel mentionedModel;
    ActivityLcfMessageBinding activityLcfMessageBinding;
    LSCMessageAdapter mAdapter;
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
        oldMessageVOs = LitePal.where("msgType = ?", mType).find(MessageVO.class);
        // 从Pool获取新的
        newMessageVOs = MessageVOPool.getMessageVOs(mType);
        MessageVOPool.clear(mType);
        // 访问网络
        List<Long> userIds = new ArrayList<>();
        for (MessageVO messageVO: oldMessageVOs) {
            userIds.add(messageVO.getSenderId());
        }
        for (MessageVO messageVO: newMessageVOs) {
            userIds.add(messageVO.getSenderId());
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
                initAdapter();
                dialog.dismiss();
            }
        });

    }
    private void initAdapter() {
        mAdapter = new LSCMessageAdapter(this, mType, oldUsers, newUsers, oldMessageVOs, newMessageVOs);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        activityLcfMessageBinding.recyclerViewMentioned.setLayoutManager(linearLayoutManager);
        activityLcfMessageBinding.recyclerViewMentioned.setAdapter(mAdapter);
        activityLcfMessageBinding.recyclerViewMentioned.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
    }
}
