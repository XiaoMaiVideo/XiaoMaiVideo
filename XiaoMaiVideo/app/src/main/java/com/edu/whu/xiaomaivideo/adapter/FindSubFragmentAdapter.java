/**
 * Author: 何慷、叶俊豪
 * Create Time: 2020/7/14
 * Update Time: 2020/7/19
 */

package com.edu.whu.xiaomaivideo.adapter;

import android.content.Context;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.edu.whu.xiaomaivideo.ui.fragment.FindSubFragment;
import com.edu.whu.xiaomaivideo.util.Constant;

import java.util.ArrayList;

public class FindSubFragmentAdapter extends FragmentStatePagerAdapter {

    public ArrayList<Fragment> fragments;

    public Context mContext;

    private String[] titles;

    public FindSubFragmentAdapter(FragmentManager fm, Context context) {
        super(fm);
        mContext = context;
        initFragments();
    }

    public ArrayList<Fragment> getFragments() {
        return fragments;
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }

    /**
     * 填入相关的主题即可  可参考微博相关主题 Hots Local HotTopics
     */
    private void initFragments() {
        titles = new String[]{
                "猜你喜欢",
                "本地热点"
        };

        fragments = new ArrayList<>();
        fragments.add(FindSubFragment.newInstance(Constant.RECOMMEND));
        fragments.add(FindSubFragment.newInstance(Constant.LOCAL_HOT));
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return titles[position];
    }
}
