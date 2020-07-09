package com.edu.whu.xiaomaivideo.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.bumptech.glide.Glide;
import com.edu.whu.xiaomaivideo.R;
import com.edu.whu.xiaomaivideo.databinding.FragmentNotificationsBinding;
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
        return fragmentNotificationsBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ImageView imageView = getActivity().findViewById(R.id.userAvatar);
        Glide.with(getActivity()).load(R.drawable.ic_dashboard_black_24dp).into(imageView);
    }
}