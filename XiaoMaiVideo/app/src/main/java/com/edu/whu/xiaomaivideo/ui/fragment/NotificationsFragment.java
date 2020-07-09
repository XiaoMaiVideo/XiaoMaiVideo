package com.edu.whu.xiaomaivideo.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.edu.whu.xiaomaivideo.R;
import com.edu.whu.xiaomaivideo.databinding.FragmentNotificationsBinding;
import com.edu.whu.xiaomaivideo.ui.activity.LoginActivity;
import com.edu.whu.xiaomaivideo.viewModel.NotificationsViewModel;

import java.util.Objects;

public class NotificationsFragment extends Fragment {

    private NotificationsViewModel notificationsViewModel;
    FragmentNotificationsBinding fragmentNotificationsBinding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        notificationsViewModel= new ViewModelProvider(Objects.requireNonNull(getActivity())).get(NotificationsViewModel.class);
        fragmentNotificationsBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_notifications,container,false);
        fragmentNotificationsBinding.setViewmodel(notificationsViewModel);
        fragmentNotificationsBinding.setLifecycleOwner(getActivity());
        fragmentNotificationsBinding.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent().setClass(getActivity(), LoginActivity.class);
                startActivity(intent);
            }
        });
        return fragmentNotificationsBinding.getRoot();
    }
}