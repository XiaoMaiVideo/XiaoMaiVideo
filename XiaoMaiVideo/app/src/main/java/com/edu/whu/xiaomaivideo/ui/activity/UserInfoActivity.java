/**
 * Author: 张俊杰、叶俊豪
 * Create Time: 2020/7/10
 * Update Time: 2020/7/18
 */

package com.edu.whu.xiaomaivideo.ui.activity;


import android.content.Intent;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;


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
import com.edu.whu.xiaomaivideo.model.User;
import com.edu.whu.xiaomaivideo.restcallback.UserRestCallback;
import com.edu.whu.xiaomaivideo.restservice.UserRestService;
import com.edu.whu.xiaomaivideo.ui.fragment.UserLikedVideoFragment;
import com.edu.whu.xiaomaivideo.ui.fragment.UserVideoWorksFragment;
import com.edu.whu.xiaomaivideo.ui.fragment.VideoNewsFragment;
import com.edu.whu.xiaomaivideo.util.CommonUtils;
import com.edu.whu.xiaomaivideo.util.Constant;
import com.edu.whu.xiaomaivideo.util.EventBusMessage;
import com.edu.whu.xiaomaivideo.viewModel.UserInfoViewModel;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.jiajie.load.LoadingDialog;

import org.greenrobot.eventbus.EventBus;
import org.parceler.Parcels;

import java.util.Objects;

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

        LoadingDialog dialog = new LoadingDialog.Builder(this).loadText("加载中...").build();
        dialog.show();
        UserRestService.getUserByID(userInfoViewModel.getUser().getValue().getUserId(), new UserRestCallback() {
            @Override
            public void onSuccess(int resultCode, User user) {
                super.onSuccess(resultCode, user);
                // TODO: 把用户的作品/动态/点赞列表分开，赋给三个tab
                setTabs();
                setRecyclerView();
                setUserSFNumClickListener();
                setOnButtonClickListener();
                dialog.dismiss();
            }
        });
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
                        fragment = new UserVideoWorksFragment();
                        break;
                    case 1:
                        fragment = new VideoNewsFragment();
                        break;
                    case 2:
                        fragment = new UserLikedVideoFragment();
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
                // 这里需要根据position修改tab的样式和文字等
                switch (position) {
                    default:
                    case 0:
                        tab.setText("作品 0");
                        break;
                    case 1:
                        tab.setText("动态 0");
                        break;
                    case 2:
                        tab.setText("喜欢 0");
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
        activityUserInfoBinding.userSFNums.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 跳转关注和粉丝列表页面
                Intent intent=new Intent(UserInfoActivity.this,FollowActivity.class);
                startActivity(intent);
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
        if (CommonUtils.isUserFollowedByCurrentUser(userInfoViewModel.getUser().getValue())) {
            // 本来关注了，那按钮就是取消关注
            activityUserInfoBinding.subscribeButton.setText("取消关注");
        }
        else {
            activityUserInfoBinding.subscribeButton.setText("关注");
        }
        // 关注功能
        activityUserInfoBinding.subscribeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (CommonUtils.isUserFollowedByCurrentUser(userInfoViewModel.getUser().getValue())) {
                    MessageVO message = new MessageVO();
                    message.setMsgType("unfollow");
                    message.setSenderId(Constant.currentUser.getUserId());
                    message.setReceiverId(userInfoViewModel.getUser().getValue().getUserId());
                    EventBus.getDefault().post(new EventBusMessage(Constant.SEND_MESSAGE, JSON.toJSONString(message)));
                    activityUserInfoBinding.subscribeButton.setText("关注");
                    // TODO: 让后端接口再做个User的关注数和粉丝数，获赞不做了
                }
                else {
                    // 本来没关注，那按钮就是关注
                    MessageVO message = new MessageVO();
                    message.setMsgType("follow");
                    message.setSenderId(Constant.currentUser.getUserId());
                    message.setReceiverId(userInfoViewModel.getUser().getValue().getUserId());
                    EventBus.getDefault().post(new EventBusMessage(Constant.SEND_MESSAGE, JSON.toJSONString(message)));
                    activityUserInfoBinding.subscribeButton.setText("取消关注");
                }
                // TODO: 因为是根据CurrentUser的关注列表判断是否关注的，所以此处可能会有BUG，后面再修了
            }
        });
        // 聊天功能
        activityUserInfoBinding.chatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO: 聊天功能
            }
        });
    }
}
