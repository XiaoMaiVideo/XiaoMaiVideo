package com.edu.whu.xiaomaivideo.ui.fragment;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.edu.whu.xiaomaivideo.R;
import com.edu.whu.xiaomaivideo.databinding.FindFragmentBinding;
import com.edu.whu.xiaomaivideo.viewModel.FindViewModel;

import java.util.Objects;

public class FindFragment extends Fragment {

    private FindViewModel findViewModel;
    private FindFragmentBinding findFragmentBinding;

    public static FindFragment newInstance() {
        return new FindFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        findViewModel= new ViewModelProvider(Objects.requireNonNull(getActivity())).get(FindViewModel.class);
        findFragmentBinding = DataBindingUtil.inflate(inflater, R.layout.find_fragment,container,false);
        findFragmentBinding.setViewmodel(findViewModel);
        findFragmentBinding.setLifecycleOwner(getActivity());

        return findFragmentBinding.getRoot();
    }
}
