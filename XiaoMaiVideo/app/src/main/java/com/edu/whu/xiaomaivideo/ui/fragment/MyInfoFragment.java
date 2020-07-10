package com.edu.whu.xiaomaivideo.ui.fragment;


import android.content.Intent;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import androidx.annotation.NonNull;
import android.widget.Toast;

import androidx.annotation.Nullable;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import com.edu.whu.xiaomaivideo.R;
import com.edu.whu.xiaomaivideo.adapter.UserInfoLabelAdapter;
import com.edu.whu.xiaomaivideo.databinding.MyInfoFragmentBinding;
import com.edu.whu.xiaomaivideo.ui.activity.EditUserInfoActivity;
import com.edu.whu.xiaomaivideo.ui.activity.LoginActivity;
import com.edu.whu.xiaomaivideo.viewModel.MyInfoViewModel;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.Objects;

public class MyInfoFragment extends Fragment {

    private MyInfoViewModel myInfoViewModel;
    MyInfoFragmentBinding myInfoFragmentBinding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        myInfoViewModel = new ViewModelProvider(Objects.requireNonNull(getActivity())).get(MyInfoViewModel.class);

        myInfoFragmentBinding = DataBindingUtil.inflate(inflater, R.layout.my_info_fragment, container, false);
        myInfoFragmentBinding.setViewmodel(myInfoViewModel);
        myInfoFragmentBinding.setLifecycleOwner(getActivity());
        myInfoFragmentBinding.imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent().setClass(getActivity(), LoginActivity.class);
                startActivity(intent);
            }
        });

//        myInforFragmentBinding.settingRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
//        myInforFragmentBinding.settingRecyclerView.setAdapter(new SettingsAdapter(getActivity(), new SettingsAdapter.OnItemClickListener()
//        {
//            @Override
//            public void onClick(int pos)
//            {
//                Toast.makeText(getActivity(), "click..." + pos, Toast.LENGTH_SHORT).show();
//            }
//        }));


        ViewPager2 mViewPager2 = myInfoFragmentBinding.viewPage2;
        TabLayout mTabLayout = myInfoFragmentBinding.tabLayout;
        mViewPager2.setAdapter(new FragmentStateAdapter(this) {
            private int count = 3;

            @NonNull
            @Override
            public Fragment createFragment(int position) {
                Fragment fragment;
                switch (position) {
                    default:
                    case 0:
                        fragment = new MyVideoWorksFragment();
                        break;
                    case 1:
                        fragment = new VideoNewsFragment();
                        break;
                    case 2:
                        fragment = new MyLikedVideoFragment();
                        break;
                }
                return fragment;
            }

            @Override
            public int getItemCount() {
                return count;
            }
        });
        new TabLayoutMediator(mTabLayout, mViewPager2, true,new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                // 这里需要根据position修改tab的样式和文字等
                switch (position) {
                    default:
                    case 0:
                        tab.setText("作品 0");
                        break;
                    case 1:
                        tab.setText("动态 0");
                        break;
                    case 2:
                        tab.setText("喜欢 0");
                        break;
                }
            }
        }).attach();



        myInfoFragmentBinding.editInfoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent().setClass(getActivity(), EditUserInfoActivity.class);
                startActivity(intent);
            }
        });

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        myInfoFragmentBinding.recyclerView.setLayoutManager(linearLayoutManager);
        myInfoFragmentBinding.recyclerView.setAdapter(new UserInfoLabelAdapter(getActivity(), new UserInfoLabelAdapter.OnItemClickListener()
        {
            @Override
            public void onClick(int pos)
            {
                Toast.makeText(getActivity(), "这是一个标签", Toast.LENGTH_SHORT).show();
            }
        }));

        return myInfoFragmentBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }
}
