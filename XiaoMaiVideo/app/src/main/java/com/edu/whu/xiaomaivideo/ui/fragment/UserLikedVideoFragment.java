package com.edu.whu.xiaomaivideo.ui.fragment;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.edu.whu.xiaomaivideo.R;
import com.edu.whu.xiaomaivideo.adapter.SettingsFriendAdpater;
import com.edu.whu.xiaomaivideo.databinding.UserLikedVideoFragmentBinding;
import com.edu.whu.xiaomaivideo.viewModel.UserLikedVideoViewModel;
import com.edu.whu.xiaomaivideo.viewModel.UserVideoWorksViewModel;

import java.util.Objects;

public class UserLikedVideoFragment extends Fragment {

    private UserLikedVideoViewModel mViewModel;
    private UserLikedVideoFragmentBinding userLikedVideoFragmentBinding;

    public static UserLikedVideoFragment newInstance() {
        return new UserLikedVideoFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mViewModel = new ViewModelProvider(Objects.requireNonNull(getActivity())).get(UserLikedVideoViewModel.class);

        userLikedVideoFragmentBinding = DataBindingUtil.inflate(inflater, R.layout.user_liked_video_fragment, container, false);
        userLikedVideoFragmentBinding.setViewmodel(mViewModel);
        userLikedVideoFragmentBinding.setLifecycleOwner(getActivity());

        userLikedVideoFragmentBinding.userLikedVideoFragmentRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        userLikedVideoFragmentBinding.userLikedVideoFragmentRecyclerView.setAdapter(new SettingsFriendAdpater(getActivity(), new SettingsFriendAdpater.OnItemClickListener() {
            @Override
            public void onClick(int pos) {
                Toast.makeText(getActivity(), "click..." + pos, Toast.LENGTH_SHORT).show();
            }
        }));


        return userLikedVideoFragmentBinding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(UserLikedVideoViewModel.class);
        // TODO: Use the ViewModel
    }

}