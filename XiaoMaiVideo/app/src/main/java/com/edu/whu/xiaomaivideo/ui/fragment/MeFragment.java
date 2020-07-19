/**
 * Author: 叶俊豪、张俊杰
 * Create Time: 2020/7/9
 * Update Time: 2020/7/12
 */

package com.edu.whu.xiaomaivideo.ui.fragment;

import androidx.databinding.BindingAdapter;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.util.Log;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.RequestOptions;
import com.edu.whu.xiaomaivideo.R;
import com.edu.whu.xiaomaivideo.adapter.SettingsAdapter;
import com.edu.whu.xiaomaivideo.model.User;
import com.edu.whu.xiaomaivideo.ui.activity.EditUserInfoActivity;
import com.edu.whu.xiaomaivideo.databinding.FragmentMeBinding;
import com.edu.whu.xiaomaivideo.ui.activity.LoginActivity;
import com.edu.whu.xiaomaivideo.ui.activity.UserInfoActivity;
import com.edu.whu.xiaomaivideo.util.Constant;
import com.edu.whu.xiaomaivideo.viewModel.MeViewModel;
import com.lxj.xpopup.XPopup;

import org.parceler.Parcels;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static android.app.Activity.RESULT_OK;

public class MeFragment extends Fragment {

    private MeViewModel meViewModel;
    FragmentMeBinding fragmentMeBinding;
    List<Pair<String, Integer>> menuItems;
    SettingsAdapter mAdapter;

    public static MeFragment newInstance() {
        return new MeFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        meViewModel = new ViewModelProvider(Objects.requireNonNull(this)).get(MeViewModel.class);
        fragmentMeBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_me, container, false);
        fragmentMeBinding.setViewmodel(meViewModel);
        fragmentMeBinding.setLifecycleOwner(this);
        initAdapter();
        initView();
        // 自动登录成功
        if (Constant.CurrentUser.getUserId() != 0) {
            onLogIn();
        }
        return fragmentMeBinding.getRoot();
    }

    @BindingAdapter("app:avatarSrc")
    public static void setAvatar(ImageView imageView, String url) {
        //用户圆形头像
        Glide.with(imageView.getContext())
                .load(url)
                .apply(RequestOptions.bitmapTransform(new CircleCrop()))
                .into(imageView);
    }

    private void initAdapter() {
        menuItems = new ArrayList<>();
        mAdapter = new SettingsAdapter(getActivity(), pos -> {
            // 已登录
            if (Constant.CurrentUser.getUserId() != 0) {
                if (pos == 0) {
                    // 设置个人信息
                    Intent intent = new Intent(getActivity(), EditUserInfoActivity.class);
                    startActivityForResult(intent, Constant.SET_USER_INFO);
                }
                else if (pos == 1) {
                    // 我的收藏
                }
                else if (pos == 2) {
                    // 隐私设置
                }
                else if (pos == 3) {
                    // 通知设置
                }
                else if (pos == 4) {
                    // 清除缓存
                }
                else if (pos == 5) {
                    // 退出登录
                    new XPopup.Builder(getActivity())
                            .asBottomList("退出登录吗？", new String[]{"是", "否"},
                                    (position, text) -> {
                                        if (position == 0) {
                                            onLogOut();
                                        }
                                    })
                            .show();
                }
            }
            // 定义每个设置条目的操作
        }, menuItems);
    }

    private void initView() {
        fragmentMeBinding.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 未登录，跳转到登录页面
                if (Constant.CurrentUser.getUserId() == 0) {
                    Intent intent = new Intent(getActivity(), LoginActivity.class);
                    startActivityForResult(intent, Constant.LOGIN_SUCCESS_RESULT);
                }
                // 已登录，跳转到用户个人信息
                else {
                    Intent intent = new Intent(getActivity(), UserInfoActivity.class);
                    intent.putExtra("user", Parcels.wrap(User.class, Constant.CurrentUser));
                    startActivity(intent);
                }
            }
        });

        fragmentMeBinding.recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        fragmentMeBinding.recyclerView.setAdapter(mAdapter);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Constant.LOGIN_SUCCESS_RESULT) {
            if (resultCode == RESULT_OK) {
                onLogIn();
            }
        }
        else if (requestCode == Constant.SET_USER_INFO) {
            if (resultCode == RESULT_OK) {
                meViewModel.setUser(Constant.CurrentUser);
            }
        }
    }

    // 退出登录
    private void onLogOut() {
        Constant.CurrentUser = User.Visitor();
        meViewModel.setUser(Constant.CurrentUser);
        // 去掉设置列表
        menuItems.clear();
        mAdapter.notifyDataSetChanged();

        Intent intent = new Intent();
        intent.setAction(Constant.SET_WEBSOCKET);
        intent.putExtra("status", "stop");
        getActivity().sendBroadcast(intent);

    }

    // 刚刚登录的操作
    private void onLogIn() {
        if (Constant.CurrentUser.getAvatar() == null || Constant.CurrentUser.getAvatar().equals("")) {
            // 没有头像的，就设置一个游客的头像
            Constant.CurrentUser.setAvatar(User.Visitor().getAvatar());
        }

        Intent intent = new Intent();
        intent.setAction(Constant.SET_WEBSOCKET);
        intent.putExtra("status", "start");
        getActivity().sendBroadcast(intent);

        meViewModel.setUser(Constant.CurrentUser);
        menuItems.add(new Pair<>("设置个人信息", R.drawable.modify_user_info));
        menuItems.add(new Pair<>("我的收藏", R.drawable.collect));
        menuItems.add(new Pair<>("隐私设置", R.drawable.privacy));
        menuItems.add(new Pair<>("通知设置", R.drawable.notification));
        menuItems.add(new Pair<>("清除缓存", R.drawable.clean));
        menuItems.add(new Pair<>("退出登录", R.drawable.logout));
        mAdapter.notifyDataSetChanged();
    }
}
