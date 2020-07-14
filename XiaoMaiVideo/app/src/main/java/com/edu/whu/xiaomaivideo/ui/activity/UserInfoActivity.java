/**
 * Author: 张俊杰、叶俊豪
 * Create Time: 2020/7/10
 * Update Time: 2020/7/12
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

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.RequestOptions;
import com.edu.whu.xiaomaivideo.R;
import com.edu.whu.xiaomaivideo.adapter.UserInfoLabelAdapter;
import com.edu.whu.xiaomaivideo.databinding.ActivityUserInfoBinding;
import com.edu.whu.xiaomaivideo.model.User;
import com.edu.whu.xiaomaivideo.ui.fragment.UserLikedVideoFragment;
import com.edu.whu.xiaomaivideo.ui.fragment.UserVideoWorksFragment;
import com.edu.whu.xiaomaivideo.ui.fragment.VideoNewsFragment;
import com.edu.whu.xiaomaivideo.viewModel.UserInfoViewModel;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

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

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        activityUserInfoBinding.recyclerView.setLayoutManager(linearLayoutManager);
        activityUserInfoBinding.recyclerView.setAdapter(new UserInfoLabelAdapter(this, new UserInfoLabelAdapter.OnItemClickListener()
        {
            @Override
            public void onClick(int pos)
            {
            }
        }));
    }
}
