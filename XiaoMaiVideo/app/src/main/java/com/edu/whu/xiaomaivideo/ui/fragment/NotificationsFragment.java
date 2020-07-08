package com.edu.whu.xiaomaivideo.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.edu.whu.xiaomaivideo.databinding.FragmentNotificationsBinding;
import com.edu.whu.xiaomaivideo.ui.viewModel.NotificationsViewModel;

public class NotificationsFragment extends Fragment {

    private NotificationsViewModel notificationsViewModel;
    FragmentNotificationsBinding fragmentNotificationsBinding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        notificationsViewModel=new NotificationsViewModel();
        fragmentNotificationsBinding= FragmentNotificationsBinding.inflate(inflater);
        fragmentNotificationsBinding.setViewmodel(notificationsViewModel);
        fragmentNotificationsBinding.setLifecycleOwner(this);
        return fragmentNotificationsBinding.getRoot();
    }
}