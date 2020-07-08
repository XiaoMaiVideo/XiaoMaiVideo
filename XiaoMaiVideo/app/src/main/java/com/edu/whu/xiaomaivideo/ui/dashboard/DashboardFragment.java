package com.edu.whu.xiaomaivideo.ui.dashboard;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.edu.whu.xiaomaivideo.R;
import com.edu.whu.xiaomaivideo.databinding.FragmentDashboardBinding;

public class DashboardFragment extends Fragment {

    private DashboardViewModel dashboardViewModel;
    private FragmentDashboardBinding fragmentDashboardBinding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        dashboardViewModel=new DashboardViewModel();
        fragmentDashboardBinding = FragmentDashboardBinding.inflate(inflater);
        fragmentDashboardBinding.setViewmodel(dashboardViewModel);
        fragmentDashboardBinding.setLifecycleOwner(this);
        return fragmentDashboardBinding.getRoot();
    }
}