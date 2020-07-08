package com.edu.whu.xiaomaivideo.ui.notifications;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.edu.whu.xiaomaivideo.R;
import com.edu.whu.xiaomaivideo.databinding.FragmentHomeBinding;
import com.edu.whu.xiaomaivideo.databinding.FragmentNotificationsBinding;
import com.edu.whu.xiaomaivideo.ui.home.HomeViewModel;

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