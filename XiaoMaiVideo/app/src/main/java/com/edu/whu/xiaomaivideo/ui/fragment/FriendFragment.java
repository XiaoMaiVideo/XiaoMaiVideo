/**
 * Author: 李季东、张俊杰
 * Create Time: 2020/7/10
 * Update Time: 2020/7/14
 */

package com.edu.whu.xiaomaivideo.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.edu.whu.xiaomaivideo.R;
import com.edu.whu.xiaomaivideo.adapter.MovieAdapter;
import com.edu.whu.xiaomaivideo.databinding.FragmentFriendBinding;
import com.edu.whu.xiaomaivideo.model.Movie;
import com.edu.whu.xiaomaivideo.restcallback.MovieRestCallback;
import com.edu.whu.xiaomaivideo.restservice.MovieRestService;
import com.edu.whu.xiaomaivideo.util.Constant;
import com.edu.whu.xiaomaivideo.viewModel.FriendViewModel;
import com.edu.whu.xiaomaivideo.widget.MovieRecyclerView;
import com.jiajie.load.LoadingDialog;
import com.scwang.smart.refresh.footer.ClassicsFooter;
import com.scwang.smart.refresh.header.ClassicsHeader;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnLoadMoreListener;
import com.scwang.smart.refresh.layout.listener.OnRefreshListener;


import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Author: 李季东、付浩
 * Create Time: 2020/7/8
 * Update Time: 2020/7/24
 */
public class FriendFragment extends Fragment {

    private FriendViewModel friendViewModel;
    FragmentFriendBinding fragmentFriendBinding;
    List<Movie> movieList;
    MovieRecyclerView movieRecyclerView;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        friendViewModel = new ViewModelProvider(Objects.requireNonNull(getActivity())).get(FriendViewModel.class);
        fragmentFriendBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_friend, container, false);
        fragmentFriendBinding.setViewmodel(friendViewModel);
        fragmentFriendBinding.setLifecycleOwner(getActivity());
        LoadingDialog dialog = new LoadingDialog.Builder(getActivity()).loadText("加载中...").build();
        dialog.show();
        if (Constant.currentUser.getUserId() != 0) {
            //已登录
            MovieRestService.getRelatedMovies(Constant.currentUser.getUserId(), new MovieRestCallback() {
                @Override
                public void onSuccess(int resultCode, List<Movie> movies) {
                    super.onSuccess(resultCode, movies);
                    movieList = movies;
                    setRecyclerView();
                    dialog.dismiss();
                }
            });
        } else {
            movieList = new ArrayList<Movie>();
            Toast.makeText(getActivity(), "你尚未登录，不能查看好友视频哦！", Toast.LENGTH_LONG).show();
            dialog.dismiss();
        }
        return fragmentFriendBinding.getRoot();
    }

    private void setRecyclerView() {
        fragmentFriendBinding.recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        fragmentFriendBinding.recyclerView.setAdapter(new MovieAdapter(getActivity(), movieList));
        movieRecyclerView = new MovieRecyclerView(fragmentFriendBinding.recyclerView);
        //刷新
        fragmentFriendBinding.refreshLayout.setRefreshHeader(new ClassicsHeader(getActivity()));
        fragmentFriendBinding.refreshLayout.setRefreshFooter(new ClassicsFooter(getActivity()));
        fragmentFriendBinding.refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                refreshlayout.finishRefresh(2000/*,false*/);//传入false表示刷新失败
            }
        });
        fragmentFriendBinding.refreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(RefreshLayout refreshlayout) {
                refreshlayout.finishLoadMore(2000/*,false*/);//传入false表示加载失败
            }
        });
    }
}
