/**
 * author:何慷
 * createTime: 2020/7/14
 */

package com.edu.whu.xiaomaivideo.ui.activity;

import android.animation.LayoutTransition;
import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.os.Handler;
import android.view.KeyEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;
import com.edu.whu.xiaomaivideo.R;
import com.edu.whu.xiaomaivideo.adapter.SearchTabFragmentAdapter;
import com.edu.whu.xiaomaivideo.ui.fragment.SearchTabFragment;
import com.google.android.material.appbar.AppBarLayout;
import com.ogaclejapan.smarttablayout.SmartTabLayout;

public class SearchActivity extends AppCompatActivity {

        //fragment的适配器
        private SearchTabFragmentAdapter searchTabFragmentAdapter;

        //viewpager
        private ViewPager mViewPager;

        //AppBarLayout
        private AppBarLayout mAppBarLayout;

        //顶部HeaderLayout
        private LinearLayout headerLayout;

        private SmartTabLayout mTabs;

        //是否隐藏了头部
        private boolean isHideHeaderLayout = false;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_search);
            init();
        }

        //初始化方法
        private void init() {
            mTabs = findViewById(R.id.tabs);
            searchTabFragmentAdapter = new SearchTabFragmentAdapter(getSupportFragmentManager(), this);
            mViewPager = findViewById(R.id.viewpager);
            mViewPager.setAdapter(searchTabFragmentAdapter);
            mTabs.setViewPager(mViewPager);

            mViewPager.setOffscreenPageLimit(searchTabFragmentAdapter.getCount());
            headerLayout = findViewById(R.id.ll_header_layout);
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
            mAppBarLayout = findViewById(R.id.appbar);
            mAppBarLayout.setLayoutTransition(mTransition);

            mAppBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
                @Override
                public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                    verticalOffset = Math.abs(verticalOffset);
                    if (verticalOffset >= headerHeight) {
                        isHideHeaderLayout = true;
                        Toast.makeText(SearchActivity.this, "隐藏窗口", Toast.LENGTH_SHORT).show();

                        //当偏移量超过顶部layout的高度时，认为已经完全移动出屏幕了
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                AppBarLayout.LayoutParams mParams = (AppBarLayout.LayoutParams) headerLayout.getLayoutParams();
                                mParams.setScrollFlags(0);
                                headerLayout.setLayoutParams(mParams);
                                headerLayout.setVisibility(View.GONE);
                            }
                        }, 100);
                    }
                }
            });
        }

        @Override
        public boolean onKeyDown(int keyCode, KeyEvent event) {
            if (keyCode == KeyEvent.KEYCODE_BACK) {
                //监听返回键
                if (isHideHeaderLayout) {
                    isHideHeaderLayout = false;
                    ((SearchTabFragment) searchTabFragmentAdapter.getFragments().get(0)).getRvList().scrollToPosition(0);
                    headerLayout.setVisibility(View.VISIBLE);

                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            AppBarLayout.LayoutParams mParams = (AppBarLayout.LayoutParams) headerLayout.getLayoutParams();
                            mParams.setScrollFlags(AppBarLayout.LayoutParams.SCROLL_FLAG_SCROLL |
                                    AppBarLayout.LayoutParams.SCROLL_FLAG_EXIT_UNTIL_COLLAPSED);
                            headerLayout.setLayoutParams(mParams);
                        }
                    }, 300);
                } else {
                    finish();
                }
                return true;
            }
            return super.onKeyDown(keyCode, event);
        }
}


