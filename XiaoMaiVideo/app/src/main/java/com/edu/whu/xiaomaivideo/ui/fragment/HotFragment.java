package com.edu.whu.xiaomaivideo.ui.fragment;

import android.os.Bundle;
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
import com.edu.whu.xiaomaivideo.adapter.SettingsFriendAdpater;
import com.edu.whu.xiaomaivideo.adapter.SettingsHotAdpater;
import com.edu.whu.xiaomaivideo.databinding.FragmentFriendBinding;
import com.edu.whu.xiaomaivideo.databinding.FragmentHotBinding;
import com.edu.whu.xiaomaivideo.viewModel.FriendViewModel;
import com.edu.whu.xiaomaivideo.viewModel.HotViewModel;

import java.util.Objects;

public class HotFragment extends Fragment {

    private HotViewModel hotViewModel;
    FragmentHotBinding fragmentHotBinding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        hotViewModel = new ViewModelProvider(Objects.requireNonNull(getActivity())).get(HotViewModel.class);
        fragmentHotBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_hot, container, false);
        fragmentHotBinding.setViewmodel(hotViewModel);
        fragmentHotBinding.setLifecycleOwner(getActivity());
        fragmentHotBinding.recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        fragmentHotBinding.recyclerView.setAdapter(new SettingsHotAdpater(getActivity(), new SettingsHotAdpater.OnItemClickListener()
        {
            @Override
            public void onClick(int pos) {
                Toast.makeText(getActivity(), "click..." + pos, Toast.LENGTH_SHORT).show();
            }
        }));
        return fragmentHotBinding.getRoot();
    }
    @Nullable
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }
}
