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
import com.edu.whu.xiaomaivideo.databinding.MyVideoWorksFragmentBinding;
import com.edu.whu.xiaomaivideo.viewModel.MyVideoWorksViewModel;

import java.util.Objects;

public class MyVideoWorksFragment extends Fragment {

    private MyVideoWorksViewModel mViewModel;
    private MyVideoWorksFragmentBinding myVideoWorksFragmentBinding;

    public static MyVideoWorksFragment newInstance() {
        return new MyVideoWorksFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mViewModel = new ViewModelProvider(Objects.requireNonNull(getActivity())).get(MyVideoWorksViewModel.class);

        myVideoWorksFragmentBinding = DataBindingUtil.inflate(inflater, R.layout.my_video_works_fragment, container, false);
        myVideoWorksFragmentBinding.setViewmodel(mViewModel);
        myVideoWorksFragmentBinding.setLifecycleOwner(getActivity());

        myVideoWorksFragmentBinding.myVideoWorksFragmentRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        myVideoWorksFragmentBinding.myVideoWorksFragmentRecyclerView.setAdapter(new SettingsAdapter(getActivity(), new SettingsAdapter.OnItemClickListener()
        {
            @Override
            public void onClick(int pos)
            {
                Toast.makeText(getActivity(), "click..." + pos, Toast.LENGTH_SHORT).show();
            }
        }));

        return myVideoWorksFragmentBinding.getRoot();
    }

}