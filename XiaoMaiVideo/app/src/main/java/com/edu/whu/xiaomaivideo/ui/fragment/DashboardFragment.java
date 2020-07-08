package com.edu.whu.xiaomaivideo.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import com.edu.whu.xiaomaivideo.R;
import com.edu.whu.xiaomaivideo.databinding.FragmentDashboardBinding;
import com.edu.whu.xiaomaivideo.viewModel.DashboardViewModel;

import java.util.Objects;

public class DashboardFragment extends Fragment {

    private DashboardViewModel dashboardViewModel;
    private FragmentDashboardBinding fragmentDashboardBinding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        dashboardViewModel= new ViewModelProvider(Objects.requireNonNull(getActivity())).get(DashboardViewModel.class);
        fragmentDashboardBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_dashboard,container,false);
        fragmentDashboardBinding.setViewmodel(dashboardViewModel);
        fragmentDashboardBinding.setLifecycleOwner(getActivity());
        return fragmentDashboardBinding.getRoot();
    }
}