/**
 * Author: 张俊杰
 * Create Time: 2020/7/10
 * Update Time: 2020/7/10
 */

package com.edu.whu.xiaomaivideo.ui.fragment;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import android.animation.LayoutTransition;
import android.animation.ObjectAnimator;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.edu.whu.xiaomaivideo.R;
import com.edu.whu.xiaomaivideo.adapter.SearchTabFragmentAdapter;
import com.edu.whu.xiaomaivideo.databinding.FindFragmentBinding;
import com.edu.whu.xiaomaivideo.ui.activity.SearchActivity;
import com.edu.whu.xiaomaivideo.viewModel.FindViewModel;
import com.google.android.material.appbar.AppBarLayout;

import java.util.Objects;

public class FindFragment extends Fragment {

    private FindViewModel findViewModel;
    private FindFragmentBinding findFragmentBinding;

    //是否隐藏了头部
    private boolean isHideHeaderLayout = false;

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
        init();
        return findFragmentBinding.getRoot();
    }

    //初始化方法
    private void init() {
        SearchTabFragmentAdapter searchTabFragmentAdapter = new SearchTabFragmentAdapter(getActivity().getSupportFragmentManager(), getContext());
        findFragmentBinding.viewpager.setAdapter(searchTabFragmentAdapter);
        findFragmentBinding.tabs.setViewPager(findFragmentBinding.viewpager);

        findFragmentBinding.viewpager.setOffscreenPageLimit(searchTabFragmentAdapter.getCount());
        initAppBarLayout();
    }

    // 初始化AppBarLayout
    private void initAppBarLayout() {
        LayoutTransition mTransition = new LayoutTransition();
        /**
         * 添加View时过渡动画效果
         */
        ObjectAnimator addAnimator = ObjectAnimator.ofFloat(null, "translationY", 0, 1.f).
                setDuration(mTransition.getDuration(LayoutTransition.APPEARING));
        mTransition.setAnimator(LayoutTransition.APPEARING, addAnimator);

        //header layout height
        final int headerHeight = getResources().getDimensionPixelOffset(R.dimen.header_height);
        findFragmentBinding.appbar.setLayoutTransition(mTransition);

        findFragmentBinding.appbar.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                verticalOffset = Math.abs(verticalOffset);
                if (verticalOffset >= headerHeight) {
                    isHideHeaderLayout = true;
                    Toast.makeText(getActivity(), "隐藏窗口", Toast.LENGTH_SHORT).show();

                    //当偏移量超过顶部layout的高度时，认为已经完全移动出屏幕了
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            AppBarLayout.LayoutParams mParams = (AppBarLayout.LayoutParams) findFragmentBinding.llHeaderLayout.getLayoutParams();
                            mParams.setScrollFlags(0);
                            findFragmentBinding.llHeaderLayout.setLayoutParams(mParams);
                            findFragmentBinding.llHeaderLayout.setVisibility(View.GONE);
                        }
                    }, 100);
                }
            }
        });
    }
}
