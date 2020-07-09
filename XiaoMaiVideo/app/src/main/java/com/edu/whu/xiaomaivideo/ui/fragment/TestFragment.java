package com.edu.whu.xiaomaivideo.ui.fragment;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.edu.whu.xiaomaivideo.R;
import com.edu.whu.xiaomaivideo.viewModel.TestViewModel;

public class TestFragment extends Fragment {

    private TestViewModel testViewModel;

    public static TestFragment newInstance() {
        return new TestFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        testViewModel = ViewModelProviders.of(this).get(TestViewModel.class);
        View root = inflater.inflate(R.layout.fragment_test, container, false);
        final TextView textView = root.findViewById(R.id.text_test);
        testViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        return root;
    }
}
