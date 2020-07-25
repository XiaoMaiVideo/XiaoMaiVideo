/**
 * Author: 叶俊豪、张俊杰
 * Create Time: 2020/7/9
 * Update Time: 2020/7/23
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
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.RequestOptions;
import com.edu.whu.xiaomaivideo.R;
import com.edu.whu.xiaomaivideo.adapter.SettingsAdapter;
import com.edu.whu.xiaomaivideo.model.User;
import com.edu.whu.xiaomaivideo.restcallback.RestCallback;
import com.edu.whu.xiaomaivideo.restservice.UserRestService;
import com.edu.whu.xiaomaivideo.ui.activity.EditUserInfoActivity;
import com.edu.whu.xiaomaivideo.databinding.FragmentMeBinding;
import com.edu.whu.xiaomaivideo.ui.activity.LoginActivity;
import com.edu.whu.xiaomaivideo.ui.activity.UserInfoActivity;
import com.edu.whu.xiaomaivideo.ui.dialog.SettingDialog;
import com.edu.whu.xiaomaivideo.util.CleanMessageUtil;
import com.edu.whu.xiaomaivideo.util.Constant;
import com.edu.whu.xiaomaivideo.util.EventBusMessage;
import com.edu.whu.xiaomaivideo.viewModel.MeViewModel;
import com.lxj.xpopup.XPopup;
import com.lxj.xpopup.interfaces.OnConfirmListener;
import com.lxj.xpopup.interfaces.OnSelectListener;

import org.greenrobot.eventbus.EventBus;
import org.parceler.Parcels;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import de.mustafagercek.library.LoadingButton;

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
        if (Constant.currentUser.getUserId() != 0) {
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
            if (Constant.currentUser.getUserId() != 0) {
                if (pos == 0) {
                    // 设置个人信息
                    Intent intent = new Intent(getActivity(), EditUserInfoActivity.class);
                    startActivityForResult(intent, Constant.SET_USER_INFO);
                }
                else if (pos == 1) {
                    // 隐私设置
                    List<String> settingNameList = new ArrayList<>();
                    settingNameList.add("设置本账户为私密账户");
                    settingNameList.add("禁止其他人看到自己的关注/粉丝列表");
                    List<String> settingDescriptionList = new ArrayList<>();
                    settingDescriptionList.add("其他人点进你的个人主页，只能看到你的头像与昵称信息");
                    settingDescriptionList.add("其他人不能看到你关注的人以及关注你的人");
                    List<Boolean> statusList = new ArrayList<>();
                    statusList.add(Constant.currentUser.isPrivateUser());
                    statusList.add(!Constant.currentUser.isFollowListAccessible());
                    new XPopup.Builder(getActivity())
                            .asCustom(new SettingDialog(getActivity(), "隐私设置", settingNameList, settingDescriptionList, statusList, new SettingDialog.OnConfirmButtonClickListener() {
                                @Override
                                public void onClick(LoadingButton button, SettingDialog dialog, List<Boolean> statusList) {
                                    button.onStartLoading();
                                    Constant.currentUser.setPrivateUser(statusList.get(0));
                                    Constant.currentUser.setFollowListAccessible(!statusList.get(1));
                                    UserRestService.modifyUser(Constant.currentUser, new RestCallback() {
                                        @Override
                                        public void onSuccess(int resultCode) {
                                            button.onStopLoading();
                                            dialog.dismiss();
                                            Toast.makeText(getActivity(), "设置成功！", Toast.LENGTH_LONG).show();
                                        }
                                    });
                                }
                            })).show();
                }
                else if (pos == 2) {
                    // 通知设置
                    List<String> settingNameList = new ArrayList<>();
                    settingNameList.add("允许接收点赞的消息");
                    settingNameList.add("允许接收评论的消息");
                    settingNameList.add("允许接收新粉丝的消息");
                    List<String> settingDescriptionList = new ArrayList<>();
                    settingDescriptionList.add("当别人给你发布的视频点赞时，你可以接收到消息提醒");
                    settingDescriptionList.add("当别人给你发布的视频发表了评论时，你可以接收到消息提醒");
                    settingDescriptionList.add("当别人关注了你，你可以接收到消息提醒");
                    List<Boolean> statusList = new ArrayList<>();
                    statusList.add(Constant.currentUser.isCanAcceptLikeMessage());
                    statusList.add(Constant.currentUser.isCanAcceptCommentMessage());
                    statusList.add(Constant.currentUser.isCanAcceptFollowMessage());
                    new XPopup.Builder(getActivity())
                            .asCustom(new SettingDialog(getActivity(), "通知设置", settingNameList, settingDescriptionList, statusList, new SettingDialog.OnConfirmButtonClickListener() {
                                @Override
                                public void onClick(LoadingButton button, SettingDialog dialog, List<Boolean> statusList) {
                                    button.onStartLoading();
                                    Constant.currentUser.setCanAcceptLikeMessage(statusList.get(0));
                                    Constant.currentUser.setCanAcceptCommentMessage(statusList.get(1));
                                    Constant.currentUser.setCanAcceptFollowMessage(statusList.get(2));
                                    UserRestService.modifyUser(Constant.currentUser, new RestCallback() {
                                        @Override
                                        public void onSuccess(int resultCode) {
                                            button.onStopLoading();
                                            dialog.dismiss();
                                            Toast.makeText(getActivity(), "设置成功！", Toast.LENGTH_LONG).show();
                                        }
                                    });
                                }
                            })).show();
                }
                else if (pos == 3) {
                    // 清除缓存
                    new XPopup.Builder(getActivity())
                            .asConfirm("清除缓存吗？", "清除缓存可能会影响你的访问速度", new OnConfirmListener() {
                                @Override
                                public void onConfirm() {
                                    try {
                                        CleanMessageUtil.clearAllCache(Objects.requireNonNull(getActivity()).getApplicationContext());
                                        Toast.makeText(getActivity(), "清除缓存成功！", Toast.LENGTH_LONG).show();
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                }
                            })
                            .show();
                }
                else if (pos == 4) {
                    // 退出登录
                    new XPopup.Builder(getActivity())
                            .asConfirm("退出登录吗？", "你可以点击上方头像再次登录", new OnConfirmListener() {
                                @Override
                                public void onConfirm() {
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
                if (Constant.currentUser.getUserId() == 0) {
                    Intent intent = new Intent(getActivity(), LoginActivity.class);
                    startActivityForResult(intent, Constant.LOGIN_SUCCESS_RESULT);
                }
                // 已登录，跳转到用户个人信息
                else {
                    Intent intent = new Intent(getActivity(), UserInfoActivity.class);
                    intent.putExtra("user", Parcels.wrap(User.class, Constant.currentUser));
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
                meViewModel.setUser(Constant.currentUser);
            }
        }
    }

    // 退出登录
    private void onLogOut() {
        Constant.currentUser = User.Visitor();
        meViewModel.setUser(Constant.currentUser);
        // 去掉设置列表
        menuItems.clear();
        mAdapter.notifyDataSetChanged();

        Intent intent = new Intent();
        intent.setAction(Constant.SET_WEBSOCKET);
        intent.putExtra("status", "stop");
        getActivity().sendBroadcast(intent);

        // 提示更新消息列表
        // EventBus.getDefault().post(new EventBusMessage(Constant.UPDATE_MESSAGE_LIST, ""));
    }

    // 刚刚登录的操作
    private void onLogIn() {
        if (Constant.currentUser.getAvatar() == null || Constant.currentUser.getAvatar().equals("")) {
            // 没有头像的，就设置一个游客的头像
            Constant.currentUser.setAvatar(User.Visitor().getAvatar());
        }

        Intent intent = new Intent();
        intent.setAction(Constant.SET_WEBSOCKET);
        intent.putExtra("status", "start");
        getActivity().sendBroadcast(intent);
        Constant.currentUser.setEnter_uesr_center("进入个人中心>>");

        // 提示更新消息列表
        // EventBus.getDefault().post(new EventBusMessage(Constant.UPDATE_MESSAGE_LIST, ""));

        meViewModel.setUser(Constant.currentUser);
        menuItems.add(new Pair<>("设置个人信息", R.drawable.modify_user_info));
        menuItems.add(new Pair<>("隐私设置", R.drawable.privacy));
        menuItems.add(new Pair<>("通知设置", R.drawable.notification));
        menuItems.add(new Pair<>("清除缓存", R.drawable.clean));
        menuItems.add(new Pair<>("退出登录", R.drawable.logout));
        mAdapter.notifyDataSetChanged();
    }
}
