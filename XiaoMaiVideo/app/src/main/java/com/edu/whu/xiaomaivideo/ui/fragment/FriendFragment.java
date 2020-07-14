package com.edu.whu.xiaomaivideo.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.edu.whu.xiaomaivideo.R;
import com.edu.whu.xiaomaivideo.adapter.SettingsAdapter;
import com.edu.whu.xiaomaivideo.adapter.SettingsFriendAdpater;
import com.edu.whu.xiaomaivideo.databinding.FragmentFriendBinding;
import com.edu.whu.xiaomaivideo.ui.activity.EditUserInfoActivity;
import com.edu.whu.xiaomaivideo.ui.activity.VideoDetialActivity;
import com.edu.whu.xiaomaivideo.util.Constant;
import com.edu.whu.xiaomaivideo.viewModel.FriendViewModel;


import java.util.ArrayList;
import java.util.Objects;
/**
 * Author: 李季东
 * Create Time: 2020/7/8
 * Update Time: 2020/7/14
 * 视频详情页面
 */
public class FriendFragment extends Fragment {

    private  FriendViewModel friendViewModel;
    FragmentFriendBinding fragmentFriendBinding;
    SettingsFriendAdpater mAdapter;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        friendViewModel = new ViewModelProvider(Objects.requireNonNull(getActivity())).get(FriendViewModel.class);
        fragmentFriendBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_friend, container, false);
        fragmentFriendBinding.setViewmodel(friendViewModel);
        fragmentFriendBinding.setLifecycleOwner(getActivity());
//        fragmentFriendBinding.imageButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent().setClass(getActivity(), LoginActivity.class);
//                startActivity(intent);
//            }
//        });
        initAdapter();
        fragmentFriendBinding.recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        return fragmentFriendBinding.getRoot();
    }
    private void initAdapter() {
        mAdapter=new SettingsFriendAdpater(getActivity(), new SettingsFriendAdpater.OnItemClickListener()
        {
            @Override
            public void onClick(int pos)
            {
                //点击item发生的事情
                //Toast.makeText(getActivity(), "click..." + pos, Toast.LENGTH_SHORT).show();
                Intent intent =new Intent(getActivity(), VideoDetialActivity.class);
                startActivity(intent);
            }
        });
        fragmentFriendBinding.recyclerView.setAdapter(mAdapter);
    }
    @Nullable
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // ImageView imageView = getActivity().findViewById(R.id.userAvatar);
        //Glide.with(getActivity()).load().into(imageView);
    }
}
