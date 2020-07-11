package com.edu.whu.xiaomaivideo.ui.fragment;

import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.edu.whu.xiaomaivideo.R;
import com.edu.whu.xiaomaivideo.ui.activity.EditUserInfoActivity;
import com.edu.whu.xiaomaivideo.ui.activity.UserInfoActivity;
import com.edu.whu.xiaomaivideo.viewModel.MeViewModel;

public class MeFragment extends Fragment {

    private MeViewModel mViewModel;

    public static MeFragment newInstance() {
        return new MeFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_me, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(MeViewModel.class);
        // TODO: Use the ViewModel
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Button button = getActivity().findViewById(R.id.btnToUserInfo);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.e("MeFragment", "点击了按钮！");
                Intent intent = new Intent().setClass(getActivity(), UserInfoActivity.class);
                startActivity(intent);
            }
        });
    }
}
