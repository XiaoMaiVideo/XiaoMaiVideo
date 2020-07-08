package com.edu.whu.xiaomaivideo.ui.dashboard;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.edu.whu.xiaomaivideo.R;

public class DashboardFragment extends Fragment {

    private DashboardViewModel dashboardViewModel;
    private Button button;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        dashboardViewModel =
                ViewModelProviders.of(this).get(DashboardViewModel.class);
        View root = inflater.inflate(R.layout.fragment_dashboard, container, false);
        final TextView textView = root.findViewById(R.id.text_dashboard);
        button=root.findViewById(R.id.button);
        dashboardViewModel.getText().observe(getViewLifecycleOwner(), new Observer<DashboardModel>() {
            @Override
            public void onChanged(DashboardModel dashboardModel) {
                textView.setText(dashboardModel.getMsg());
            }
        });
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dashboardViewModel.setMsg("测试");
            }
        });
        return root;
    }
}