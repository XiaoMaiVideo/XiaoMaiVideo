package com.edu.whu.xiaomaivideo.ui.fragment;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

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
import com.edu.whu.xiaomaivideo.adapter.SettingsAdapter;
import com.edu.whu.xiaomaivideo.adapter.UserVideoWorksAdapter;
import com.edu.whu.xiaomaivideo.databinding.UserVideoWorksFragmentBinding;
import com.edu.whu.xiaomaivideo.viewModel.UserVideoWorksViewModel;

import java.util.Objects;

public class UserVideoWorksFragment extends Fragment {

    private UserVideoWorksViewModel mViewModel;
    private UserVideoWorksFragmentBinding userVideoWorksFragmentBinding;

    public static UserVideoWorksFragment newInstance() {
        return new UserVideoWorksFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mViewModel = new ViewModelProvider(Objects.requireNonNull(getActivity())).get(UserVideoWorksViewModel.class);

        userVideoWorksFragmentBinding = DataBindingUtil.inflate(inflater, R.layout.user_video_works_fragment, container, false);
        userVideoWorksFragmentBinding.setViewmodel(mViewModel);
        userVideoWorksFragmentBinding.setLifecycleOwner(getActivity());

        userVideoWorksFragmentBinding.myVideoWorksFragmentRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        userVideoWorksFragmentBinding.myVideoWorksFragmentRecyclerView.setAdapter(new UserVideoWorksAdapter(getActivity(), new SettingsAdapter.OnItemClickListener()
        {
            @Override
            public void onClick(int pos)
            {
                Toast.makeText(getActivity(), "click..." + pos, Toast.LENGTH_SHORT).show();
            }
        }));

        return userVideoWorksFragmentBinding.getRoot();
    }

}