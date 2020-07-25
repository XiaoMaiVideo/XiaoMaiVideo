/**
 * Author: 张俊杰、叶俊豪、李季东、方胜强
 * Create Time: 2020/7/10
 * Update Time: 2020/7/23
 * */

package com.edu.whu.xiaomaivideo.ui.activity;


import android.content.Intent;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;


import androidx.annotation.NonNull;

import androidx.annotation.Nullable;

import androidx.databinding.BindingAdapter;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import com.alibaba.fastjson.JSON;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.RequestOptions;
import com.edu.whu.xiaomaivideo.R;
import com.edu.whu.xiaomaivideo.adapter.UserInfoLabelAdapter;
import com.edu.whu.xiaomaivideo.databinding.ActivityUserInfoBinding;
import com.edu.whu.xiaomaivideo.model.MessageVO;
import com.edu.whu.xiaomaivideo.model.Movie;
import com.edu.whu.xiaomaivideo.model.User;
import com.edu.whu.xiaomaivideo.restcallback.UserRestCallback;
import com.edu.whu.xiaomaivideo.restservice.UserRestService;
import com.edu.whu.xiaomaivideo.ui.fragment.UserLikedVideoFragment;
import com.edu.whu.xiaomaivideo.ui.fragment.UserVideoWorksFragment;
import com.edu.whu.xiaomaivideo.ui.fragment.VideoNewsFragment;
import com.edu.whu.xiaomaivideo.util.CommonUtil;
import com.edu.whu.xiaomaivideo.util.Constant;
import com.edu.whu.xiaomaivideo.util.EventBusMessage;
import com.edu.whu.xiaomaivideo.viewModel.UserInfoViewModel;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.jiajie.load.LoadingDialog;
import com.lxj.xpopup.XPopup;
import com.lxj.xpopup.core.BasePopupView;

import org.greenrobot.eventbus.EventBus;
import org.parceler.Parcels;

import java.util.ArrayList;
import java.util.Objects;

import qiu.niorgai.StatusBarCompat;

import static android.widget.Toast.LENGTH_LONG;

public class UserInfoActivity extends FragmentActivity {

    private UserInfoViewModel userInfoViewModel;
    ActivityUserInfoBinding activityUserInfoBinding;

    @BindingAdapter("app:avatarSrc")
    public static void setAvatar(ImageView imageView, String url) {
        // 用户圆形头像
        Glide.with(imageView.getContext())
                .load(url)
                .apply(RequestOptions.bitmapTransform(new CircleCrop()))
                .into(imageView);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // userInfoViewModel = new ViewModelProvider(Objects.requireNonNull(this)).get(UserInfoViewModel.class);
        userInfoViewModel = new ViewModelProvider(Objects.requireNonNull(this)).get(UserInfoViewModel.class);

        User user = Parcels.unwrap(getIntent().getParcelableExtra("user"));
        userInfoViewModel.setUser(user);
        activityUserInfoBinding = DataBindingUtil.setContentView(this, R.layout.activity_user_info);
        activityUserInfoBinding.setViewmodel(userInfoViewModel);
        activityUserInfoBinding.setLifecycleOwner(this);

        StatusBarCompat.setStatusBarColor(this, getResources().getColor(R.color.gainsboro));

        BasePopupView popupView = new XPopup.Builder(this).asLoading().setTitle("加载中...").show();
        UserRestService.getUserByID(userInfoViewModel.getUser().getValue().getUserId(), new UserRestCallback() {
            @Override
            public void onSuccess(int resultCode, User user) {
                super.onSuccess(resultCode, user);
                userInfoViewModel.setUser(user);
                setVisibility();
                setTabs();
                setRecyclerView();
                setUserSFNumClickListener();
                setOnButtonClickListener();
                popupView.dismiss();
            }
        });
    }

    private void setVisibility() {
        if (userInfoViewModel.getUser().getValue().isPrivateUser()) {
            // 私密账户，看不到其他东西
            activityUserInfoBinding.userDescription.setVisibility(View.GONE);
            activityUserInfoBinding.recyclerView.setVisibility(View.GONE);
        }
        else {
            activityUserInfoBinding.userDescription.setVisibility(View.VISIBLE);
            activityUserInfoBinding.recyclerView.setVisibility(View.VISIBLE);
        }
    }

    private void setTabs() {
        ViewPager2 mViewPager2 = activityUserInfoBinding.viewPage2;
        TabLayout mTabLayout = activityUserInfoBinding.tabLayout;
        mViewPager2.setAdapter(new FragmentStateAdapter(this) {
            private int count = 3;

            @NonNull
            @Override
            public Fragment createFragment(int position) {
                Fragment fragment;
                switch (position) {
                    default:
                    case 0:
                        fragment = new UserVideoWorksFragment(userInfoViewModel.getUser().getValue());
                        break;
                    case 1:
                        fragment = new VideoNewsFragment(userInfoViewModel.getUser().getValue());
                        break;
                    case 2:
                        fragment = new UserLikedVideoFragment(userInfoViewModel.getUser().getValue());
                        break;
                }
                return fragment;
            }

            @Override
            public int getItemCount() {
                return count;
            }
        });
        new TabLayoutMediator(mTabLayout, mViewPager2, true,new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                User currentUser = userInfoViewModel.getUser().getValue();
                // 这里需要根据position修改tab的样式和文字等
                switch (position) {
                    default:
                    case 0:
                        tab.setText(currentUser.getMovies() == null? "作品 0": "作品 "+currentUser.getMovies().size());
                        break;
                    case 1:
                        tab.setText(currentUser.getShares() == null? "动态 0": "动态 "+currentUser.getShares().size());
                        break;
                    case 2:
                        tab.setText(currentUser.getLikeMovies() == null? "点赞 0": "点赞 "+currentUser.getLikeMovies().size());
                        break;
                }
            }
        }).attach();
    }

    private void setRecyclerView() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        activityUserInfoBinding.recyclerView.setLayoutManager(linearLayoutManager);
        activityUserInfoBinding.recyclerView.setAdapter(new UserInfoLabelAdapter(this, userInfoViewModel.getUserLabels()));
    }

    private void setUserSFNumClickListener() {
        activityUserInfoBinding.subscribeNum.setText(userInfoViewModel.getUser().getValue().getFollowing() == null? "0": String.valueOf(userInfoViewModel.getUser().getValue().getFollowing().size()));
        activityUserInfoBinding.followerNum.setText(userInfoViewModel.getUser().getValue().getFollowers() == null? "0": String.valueOf(userInfoViewModel.getUser().getValue().getFollowers().size()));
        activityUserInfoBinding.userSFNums.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (userInfoViewModel.getUser().getValue().isFollowListAccessible()) {
                    // 设置了允许看，就可以跳转关注和粉丝列表页面
                    Intent intent = new Intent(UserInfoActivity.this, FollowActivity.class);
                    User user = userInfoViewModel.getUser().getValue();
                    intent.putExtra("user", Parcels.wrap(user));
                    startActivity(intent);
                }
                else {
                    Toast.makeText(UserInfoActivity.this, "该用户设置了不允许看关注列表哦！", LENGTH_LONG).show();
                }
            }
        });
    }

    private void setOnButtonClickListener() {
        // 如果当前是自己，或者没登录。就不显示这两个Button
        if (userInfoViewModel.getUser().getValue().getUserId() == Constant.currentUser.getUserId()
            || Constant.currentUser.getUserId() == 0) {
            activityUserInfoBinding.subscribeButton.setVisibility(View.INVISIBLE);
            activityUserInfoBinding.chatButton.setVisibility(View.INVISIBLE);
        }
        else {
            activityUserInfoBinding.subscribeButton.setVisibility(View.VISIBLE);
            activityUserInfoBinding.chatButton.setVisibility(View.VISIBLE);
        }
        if (userInfoViewModel.getUser().getValue().isFollow()) {
            // 本来关注了，那按钮就是取消关注
            activityUserInfoBinding.subscribeButton.setText("取消关注");
            activityUserInfoBinding.subscribeButton.setIcon(getResources().getDrawable(R.drawable.cancel));
        }
        else {
            activityUserInfoBinding.subscribeButton.setText("关注");
            activityUserInfoBinding.subscribeButton.setIcon(getResources().getDrawable(R.drawable.follow));
        }
        // 关注功能
        activityUserInfoBinding.subscribeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (userInfoViewModel.getUser().getValue().isFollow()) {
                    MessageVO message = new MessageVO();
                    message.setMsgType("unfollow");
                    message.setSenderId(Constant.currentUser.getUserId());
                    message.setReceiverId(userInfoViewModel.getUser().getValue().getUserId());
                    EventBus.getDefault().post(new EventBusMessage(Constant.SEND_MESSAGE, JSON.toJSONString(message)));
                    activityUserInfoBinding.subscribeButton.setText("关注");
                    activityUserInfoBinding.subscribeButton.setIcon(getResources().getDrawable(R.drawable.follow));
                    userInfoViewModel.getUser().getValue().setFollow(false);
                }
                else {
                    // 本来没关注，那按钮就是关注
                    MessageVO message = new MessageVO();
                    message.setMsgType("follow");
                    message.setSenderId(Constant.currentUser.getUserId());
                    message.setReceiverId(userInfoViewModel.getUser().getValue().getUserId());
                    EventBus.getDefault().post(new EventBusMessage(Constant.SEND_MESSAGE, JSON.toJSONString(message)));
                    activityUserInfoBinding.subscribeButton.setText("取消关注");
                    activityUserInfoBinding.subscribeButton.setIcon(getResources().getDrawable(R.drawable.cancel));
                    userInfoViewModel.getUser().getValue().setFollow(true);
                }
            }
        });
        // 聊天功能
        activityUserInfoBinding.chatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Constant.currentChattingId = userInfoViewModel.getUser().getValue().getUserId();
                Constant.currentChattingName = userInfoViewModel.getUser().getValue().getNickname();
                Intent intent = new Intent(UserInfoActivity.this, ChatActivity.class);
                startActivity(intent);
            }
        });
    }
}
