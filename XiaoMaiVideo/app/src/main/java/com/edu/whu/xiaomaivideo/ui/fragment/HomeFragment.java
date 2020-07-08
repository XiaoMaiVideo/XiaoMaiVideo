package com.edu.whu.xiaomaivideo.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.edu.whu.xiaomaivideo.databinding.FragmentHomeBinding;
import com.edu.whu.xiaomaivideo.ui.activity.LoginActivity;
import com.edu.whu.xiaomaivideo.ui.viewModel.HomeViewModel;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    FragmentHomeBinding fragmentHomeBinding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel=new HomeViewModel();
        fragmentHomeBinding=FragmentHomeBinding.inflate(inflater);
        fragmentHomeBinding.setViewmodel(homeViewModel);
        fragmentHomeBinding.setLifecycleOwner(this);
        fragmentHomeBinding.button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getActivity(), LoginActivity.class);
                startActivity(intent);
            }
        });
        return fragmentHomeBinding.getRoot();
    }
}