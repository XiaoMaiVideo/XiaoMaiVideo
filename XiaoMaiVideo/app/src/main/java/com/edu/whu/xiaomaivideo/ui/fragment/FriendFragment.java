/**
 * Author: 李季东、张俊杰
 * Create Time: 2020/7/10
 * Update Time: 2020/7/11
 */

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
import com.edu.whu.xiaomaivideo.databinding.FragmentFriendBinding;
import com.edu.whu.xiaomaivideo.viewModel.FriendViewModel;


import java.util.Objects;

public class FriendFragment extends Fragment {

    private  FriendViewModel friendViewModel;
    FragmentFriendBinding fragmentFriendBinding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        friendViewModel = new ViewModelProvider(Objects.requireNonNull(getActivity())).get(FriendViewModel.class);
        fragmentFriendBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_friend, container, false);
        fragmentFriendBinding.setViewmodel(friendViewModel);
        fragmentFriendBinding.setLifecycleOwner(getActivity());
//        fragmentFriendBinding.imageButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent().setClass(getActivity(), LoginActivity.class);
//                startActivity(intent);
//            }
//        });

        fragmentFriendBinding.recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        fragmentFriendBinding.recyclerView.setAdapter(new SettingsFriendAdpater(getActivity(), new SettingsFriendAdpater.OnItemClickListener()
        {
            @Override
            public void onClick(int pos)
            {
                Toast.makeText(getActivity(), "click..." + pos, Toast.LENGTH_SHORT).show();
            }
        }));
        return fragmentFriendBinding.getRoot();
    }
}
