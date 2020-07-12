/**
 * Author: 张俊杰、叶俊豪
 * Create Time: 2020/7/10
 * Update Time: 2020/7/12
 */

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
import com.edu.whu.xiaomaivideo.databinding.VideoNewsFragmentBinding;
import com.edu.whu.xiaomaivideo.viewModel.UserLikedVideoViewModel;
import com.edu.whu.xiaomaivideo.viewModel.VideoNewsViewModel;

import java.util.Objects;

public class VideoNewsFragment extends Fragment {

    private VideoNewsViewModel mViewModel;
    private VideoNewsFragmentBinding videoNewsFragmentBinding;

    public static VideoNewsFragment newInstance() {
        return new VideoNewsFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mViewModel = new ViewModelProvider(Objects.requireNonNull(getActivity())).get(VideoNewsViewModel.class);

        videoNewsFragmentBinding = DataBindingUtil.inflate(inflater, R.layout.video_news_fragment, container, false);
        videoNewsFragmentBinding.setViewmodel(mViewModel);
        videoNewsFragmentBinding.setLifecycleOwner(getActivity());

        videoNewsFragmentBinding.videoNewsFragmentRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        videoNewsFragmentBinding.videoNewsFragmentRecyclerView.setAdapter(new SettingsFriendAdpater(getActivity(), new SettingsFriendAdpater.OnItemClickListener() {
            @Override
            public void onClick(int pos) {
                Toast.makeText(getActivity(), "click..." + pos, Toast.LENGTH_SHORT).show();
            }
        }));


        return videoNewsFragmentBinding.getRoot();
    }
}