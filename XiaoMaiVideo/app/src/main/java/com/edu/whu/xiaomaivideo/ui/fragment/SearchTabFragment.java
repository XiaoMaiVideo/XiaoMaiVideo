/**
 * author:何慷
 * createTime:2020/7/14
 */

package com.edu.whu.xiaomaivideo.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.edu.whu.xiaomaivideo.R;
import com.edu.whu.xiaomaivideo.adapter.TestRvAdapter;

public class SearchTabFragment extends Fragment {

    public static SearchTabFragment newInstance() {
        return new SearchTabFragment();
    }

    private RecyclerView mRvList;

    private View rootView;

    private TestRvAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
         rootView = inflater.inflate(R.layout.fragment_search_tab,container,false);
     //   rootView = inflater.inflate(R.layout.activity_search,container,false);
        initWidget();
        return rootView;
    }

    public void initWidget(){
        adapter = new TestRvAdapter(getActivity());
        mRvList = rootView.findViewById(R.id.rv_list);
        mRvList.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRvList.setAdapter(adapter);
    }

    public RecyclerView getRvList(){
        return mRvList;
    }

}
