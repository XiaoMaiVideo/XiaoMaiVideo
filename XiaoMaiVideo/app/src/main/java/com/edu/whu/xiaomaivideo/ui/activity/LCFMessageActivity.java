/**
 * Author:
 * Create Time:
 * Update Time: 2020/7/25
 */
package com.edu.whu.xiaomaivideo.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.donkingliang.groupedadapter.adapter.GroupedRecyclerViewAdapter;
import com.donkingliang.groupedadapter.holder.BaseViewHolder;
import com.edu.whu.xiaomaivideo.R;
import com.edu.whu.xiaomaivideo.adapter.LCFMessageAdapter;
import com.edu.whu.xiaomaivideo.databinding.ActivityLcfMessageBinding;
import com.edu.whu.xiaomaivideo.model.MessageVO;
import com.edu.whu.xiaomaivideo.model.Movie;
import com.edu.whu.xiaomaivideo.model.User;
import com.edu.whu.xiaomaivideo.restcallback.UserRestCallback;
import com.edu.whu.xiaomaivideo.restservice.UserRestService;
import com.edu.whu.xiaomaivideo.util.Constant;
import com.edu.whu.xiaomaivideo.viewModel.MentionedModel;
import com.lxj.xpopup.XPopup;
import com.lxj.xpopup.core.BasePopupView;

import org.litepal.LitePal;
import org.parceler.Parcels;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import qiu.niorgai.StatusBarCompat;

/**
 * Author: 李季东、叶俊豪、方胜强
 * Create Time: 2020/7/15
 * Update Time: 2020/7/23
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

        StatusBarCompat.setStatusBarColor(this, getResources().getColor(R.color.gainsboro));

        mType = getIntent().getStringExtra("type");
        setTitle();

        BasePopupView popupView = new XPopup.Builder(this).asLoading().setTitle("加载中...").show();
        // 从本地数据库获取对应的信息，把后面几个当成新的消息来看待
        // List<MessageVO> tempNewMsgList = MessageVOPool.getMessageVOs(mType);
        int newMessageCount = 0;
        if (mType.equals("like")) {
            newMessageCount = Constant.currentLikeMessage.getValue();
        }
        else if (mType.equals("comment")) {
            newMessageCount = Constant.currentCommentMessage.getValue();
        }
        else if (mType.equals("follow")) {
            newMessageCount = Constant.currentFollowMessage.getValue();
        }
        newMessageVOs = new ArrayList<>();
        oldMessageVOs = new ArrayList<>();
        List<MessageVO> tempMsgList = LitePal.where("msgType = ? and receiverId = ?", mType, String.valueOf(Constant.currentUser.getUserId())).find(MessageVO.class);
        for (int i=tempMsgList.size()-1; i>=0; i--) {
            if (tempMsgList.size()-i-1<newMessageCount) {
                newMessageVOs.add(tempMsgList.get(i));
            }
            else {
                oldMessageVOs.add(tempMsgList.get(i));
            }
        }

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
                if (mType.equals("like")) {
                    Constant.currentLikeMessage.setValue(0);
                }
                else if (mType.equals("comment")) {
                        Constant.currentCommentMessage.setValue(0);
                }
                else if (mType.equals("follow")) {
                    Constant.currentFollowMessage.setValue(0);
                }
                popupView.dismiss();
            }
        });

    }
    private void initAdapter() {
        mAdapter = new LCFMessageAdapter(this, oldMessageVOs, newMessageVOs, oldUsers, newUsers, mType);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        activityLcfMessageBinding.recyclerViewMentioned.setLayoutManager(linearLayoutManager);
        activityLcfMessageBinding.recyclerViewMentioned.setAdapter(mAdapter);
        activityLcfMessageBinding.stickyLayout.setSticky(true);
        mAdapter.setOnChildClickListener(new GroupedRecyclerViewAdapter.OnChildClickListener() {
            @Override
            public void onChildClick(GroupedRecyclerViewAdapter adapter, BaseViewHolder holder, int groupPosition, int childPosition) {
                if (mType.equals("follow")) {
                    // 关注消息，跳转关注粉丝列表
                    Intent intent = new Intent(LCFMessageActivity.this, FollowActivity.class);
                    intent.putExtra("user", Parcels.wrap(User.class, Constant.currentUser));
                    startActivity(intent);
                }
                else {
                    // 点赞和评论消息，点击进入视频详情页面
                    if (groupPosition == 0) {
                        // 新消息
                        Intent intent = new Intent(LCFMessageActivity.this, VideoDetailActivity.class);
                        intent.putExtra("movie", Parcels.wrap(Movie.class, new Movie(newMessageVOs.get(childPosition).getMovieId())));
                        startActivity(intent);
                    }
                    else {
                        // 旧消息
                        Intent intent = new Intent(LCFMessageActivity.this, VideoDetailActivity.class);
                        intent.putExtra("movie", Parcels.wrap(Movie.class, new Movie(oldMessageVOs.get(childPosition).getMovieId())));
                        startActivity(intent);
                    }
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.lcf_toolbar_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.clearMessage) {
            new XPopup.Builder(this).asConfirm("注意", "这将会清除页面中所有的点赞通知消息，且不可恢复",
                    () -> {
                        // 清除本地数据库内的消息
                        LitePal.deleteAll(MessageVO.class, "msgType = ?", mType);
                        // 清除界面内的
                        oldMessageVOs.clear();
                        newMessageVOs.clear();
                        oldUsers.clear();
                        newUsers.clear();
                        mAdapter.notifyDataChanged();
                    })
                .show();
        }
        return true;
    }

    private void setTitle() {
        setSupportActionBar(findViewById(R.id.lcfMessageToolbar));
        if (mType.equals("like")) {
            getSupportActionBar().setTitle("赞");
        }
        else if (mType.equals("comment")) {
            getSupportActionBar().setTitle("评论");
        }
        else if (mType.equals("follow")) {
            getSupportActionBar().setTitle("新粉丝");
        }
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
}
