package com.edu.whu.xiaomaivideo.ui.fragment;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.databinding.BindingAdapter;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

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
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

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
        return fragmentMeBinding.getRoot();
    }

    @BindingAdapter("app:avatarSrc")
    public static void setAvatar(ImageView imageView, String url) {
        // 用户圆形头像
        Glide.with(imageView.getContext())
                .load(url)
                .apply(RequestOptions.bitmapTransform(new CircleCrop()))
                .into(imageView);
    }

    private void initAdapter() {
        menuItems = new ArrayList<>();
        menuItems.add(new Pair<>("设置条目一", R.drawable.modify_user_info));
        mAdapter = new SettingsAdapter(getActivity(), new SettingsAdapter.OnItemClickListener() {
            @Override
            public void onClick(int pos) {
                // 已登录
                if (Constant.CurrentUser.getUserId() != 0) {
                    // 设置个人信息
                    if (pos == Constant.SETTING_ITEM_COUNT) {
                        Intent intent = new Intent(getActivity(), EditUserInfoActivity.class);
                        startActivity(intent);
                    }
                    // 退出登录
                    else if (pos == Constant.SETTING_ITEM_COUNT + 1) {
                        onLogOut();
                    }
                }
                // 定义每个设置条目的操作
                Toast.makeText(getActivity(), "click..." + pos, Toast.LENGTH_SHORT).show();
            }
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
                Constant.CurrentUser.setAvatar("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1594446309411&di=95ec8df149f572361cbe0a8b3ad60113&imgtype=0&src=http%3A%2F%2Fwww.jf258.com%2Fuploads%2F2014-08-16%2F224526452.jpg");
                meViewModel.setUser(Constant.CurrentUser);
                menuItems.add(new Pair<>("设置个人信息", R.drawable.modify_user_info));
                menuItems.add(new Pair<>("退出登录", R.drawable.logout));
                mAdapter.notifyDataSetChanged();
            }
        }
    }

    // 退出登录
    private void onLogOut() {
        Constant.CurrentUser = User.Visitor();
        meViewModel.setUser(Constant.CurrentUser);
        // 去掉最后两个
        menuItems.remove(menuItems.size() - 1);
        menuItems.remove(menuItems.size() - 1);
        mAdapter.notifyDataSetChanged();
    }
}
