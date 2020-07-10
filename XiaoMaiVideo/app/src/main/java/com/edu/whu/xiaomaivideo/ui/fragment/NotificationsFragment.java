package com.edu.whu.xiaomaivideo.ui.fragment;


import android.content.Intent;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import androidx.annotation.NonNull;

import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.edu.whu.xiaomaivideo.R;
import com.edu.whu.xiaomaivideo.adapter.SettingsAdapter;
import com.edu.whu.xiaomaivideo.databinding.FragmentNotificationsBinding;
import com.edu.whu.xiaomaivideo.ui.activity.LoginActivity;
import com.bumptech.glide.Glide;
import com.edu.whu.xiaomaivideo.viewModel.NotificationsViewModel;

import java.util.Objects;

public class NotificationsFragment extends Fragment {

    private NotificationsViewModel notificationsViewModel;
    FragmentNotificationsBinding fragmentNotificationsBinding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        notificationsViewModel = new ViewModelProvider(Objects.requireNonNull(getActivity())).get(NotificationsViewModel.class);
        fragmentNotificationsBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_notifications, container, false);
        fragmentNotificationsBinding.setViewmodel(notificationsViewModel);
        fragmentNotificationsBinding.setLifecycleOwner(getActivity());
        fragmentNotificationsBinding.imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent().setClass(getActivity(), LoginActivity.class);
                startActivity(intent);
            }
        });

        fragmentNotificationsBinding.settingRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        fragmentNotificationsBinding.settingRecyclerView.setAdapter(new SettingsAdapter(getActivity(), new SettingsAdapter.OnItemClickListener()
        {
            @Override
            public void onClick(int pos)
            {
                Toast.makeText(getActivity(), "click..." + pos, Toast.LENGTH_SHORT).show();
            }
        }));
        return fragmentNotificationsBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // ImageView imageView = getActivity().findViewById(R.id.userAvatar);
        //Glide.with(getActivity()).load().into(imageView);
    }
}
