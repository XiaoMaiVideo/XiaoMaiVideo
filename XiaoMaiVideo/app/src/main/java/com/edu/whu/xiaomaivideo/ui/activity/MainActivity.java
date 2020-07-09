package com.edu.whu.xiaomaivideo.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.ViewGroup;


import com.edu.whu.xiaomaivideo.R;
import com.edu.whu.xiaomaivideo.ui.fragment.DashboardFragment;
import com.edu.whu.xiaomaivideo.ui.fragment.HomeFragment;
import com.edu.whu.xiaomaivideo.ui.notifications.NotificationsFragment;
import com.edu.whu.xiaomaivideo.ui.test.TestFragment;
import com.edu.whu.xiaomaivideo.util.MyViewPager;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.tabs.TabLayout;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends FragmentActivity {

    private MyViewPager mViewPager;
    private List<Fragment> mFragments;
    private FragmentPagerAdapter mAdapter;
    private TabLayout mTabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        mViewPager = findViewById(R.id.viewPager);
        mTabLayout = findViewById(R.id.tab_layout);
        mFragments = new ArrayList<>(5);
        mFragments.add(new DashboardFragment()); // 第一个tab
        mFragments.add(new HomeFragment()); // 第二个tab
        mFragments.add(new NotificationsFragment()); // 没用，占个位置
        mFragments.add(new NotificationsFragment()); // 第三个tab
        mFragments.add(new TestFragment()); // 第四个tab
        mAdapter = new MyAdapter(getSupportFragmentManager());
        mViewPager.setAdapter(mAdapter);
        mTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if (tab.getPosition() == 2) {
                    // 表示点击了中间的那个按钮，在这里处理中间按钮的点击触发事件
                    Log.e("MainActivity", "点击了中间按钮");
                }
                else {
                    // 切换到对应的tab
                    mViewPager.setCurrentItem(tab.getPosition());
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                if (tab.getPosition() == 2) {
                    // 表示点击了中间的那个按钮，在这里处理中间按钮的点击触发事件
                    Log.e("MainActivity", "点击了中间按钮");
                }
            }
        });
    }

    public class MyAdapter extends FragmentPagerAdapter {
        public MyAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public int getCount() {
            return 5;
        }

        @Override
        public Fragment getItem(int position) {
            return mFragments.get(position);
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            //super.destroyItem(container, position, object);
        }
    }
}