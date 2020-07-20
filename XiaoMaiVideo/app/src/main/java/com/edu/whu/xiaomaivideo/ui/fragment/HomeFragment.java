/**
 * Author: 张俊杰、李季东
 * Create Time: 2020/7/9
 * Update Time: 2020/7/10
 */

package com.edu.whu.xiaomaivideo.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import com.bumptech.glide.Glide;
import com.edu.whu.xiaomaivideo.R;
import com.edu.whu.xiaomaivideo.databinding.FragmentHomeBinding;
import com.edu.whu.xiaomaivideo.viewModel.HomeViewModel;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.Objects;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    FragmentHomeBinding fragmentHomeBinding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel= new ViewModelProvider(Objects.requireNonNull(getActivity())).get(HomeViewModel.class);
        fragmentHomeBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_home,container,false);
        fragmentHomeBinding.setViewmodel(homeViewModel);
        fragmentHomeBinding.setLifecycleOwner(getActivity());

        ViewPager2 mViewPager2 = fragmentHomeBinding.viewPage;
        TabLayout mTabLayout = fragmentHomeBinding.tabLayout;
        mViewPager2.setAdapter(new FragmentStateAdapter(this) {
            private int count = 2;

            @NonNull
            @Override
            public Fragment createFragment(int position) {
                Fragment fragment;
                switch (position) {
                    default:
                    case 0:
                        fragment = new HotFragment();
                        break;
                    case 1:
                        fragment = new FriendFragment();
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
                        tab.setText("热点");
                        break;
                    case 1:
                        tab.setText("朋友");
                        break;
                }
            }
        }).attach();
        Glide.with(getActivity()).load(android.R.drawable.ic_media_play).into(fragmentHomeBinding.imageView3);
        Glide.with(getActivity()).load(android.R.drawable.ic_media_ff).into(fragmentHomeBinding.imageView4);
//        fragmentHomeBinding.button2.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent=new Intent(getActivity(), LoginActivity.class);
//                startActivity(intent);
//            }
//        });
        return fragmentHomeBinding.getRoot();
    }
}