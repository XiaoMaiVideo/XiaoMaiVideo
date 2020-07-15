/**
 * author:何慷
 * createTime:2020/7/14
 */

package com.edu.whu.xiaomaivideo.adapter;

import android.content.Context;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.edu.whu.xiaomaivideo.R;
import com.edu.whu.xiaomaivideo.ui.fragment.SearchTabFragment;

import java.util.ArrayList;

public class SearchTabFragmentAdapter extends FragmentStatePagerAdapter {

    public ArrayList<Fragment> fragments;

    public Context mContext;

    private String[] titles;

    public SearchTabFragmentAdapter(FragmentManager fm, Context context) {
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
                mContext.getResources().getString(R.string.test_1),
                mContext.getResources().getString(R.string.test_2),
                mContext.getResources().getString(R.string.test_3),
                mContext.getResources().getString(R.string.test_4),
        };

        fragments = new ArrayList<>();
        for ( int i=0; i < titles.length; i++ ){
            Fragment fragment = SearchTabFragment.newInstance();
            fragments.add(fragment);
        }
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return titles[position];
    }
}
