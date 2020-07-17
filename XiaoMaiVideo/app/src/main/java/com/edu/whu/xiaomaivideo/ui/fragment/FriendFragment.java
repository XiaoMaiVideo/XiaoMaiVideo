/**
 * Author: 李季东、张俊杰
 * Create Time: 2020/7/10
 * Update Time: 2020/7/14
 */

package com.edu.whu.xiaomaivideo.ui.fragment;

import android.graphics.Rect;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.edu.whu.xiaomaivideo.R;
import com.edu.whu.xiaomaivideo.adapter.MovieAdapter;
import com.edu.whu.xiaomaivideo.adapter.SettingsFriendAdpater;
import com.edu.whu.xiaomaivideo.databinding.FragmentFriendBinding;
import com.edu.whu.xiaomaivideo.databinding.FragmentHotBinding;
import com.edu.whu.xiaomaivideo.model.Movie;
import com.edu.whu.xiaomaivideo.model.User;
import com.edu.whu.xiaomaivideo.restcallback.MovieRestCallback;
import com.edu.whu.xiaomaivideo.restservice.MovieRestService;
import com.edu.whu.xiaomaivideo.util.Constant;
import com.edu.whu.xiaomaivideo.viewModel.FriendViewModel;
import com.edu.whu.xiaomaivideo.viewModel.HotViewModel;
import com.jiajie.load.LoadingDialog;


import java.util.List;
import java.util.Objects;

import cn.jzvd.Jzvd;
import cn.jzvd.JzvdStd;

/**
 * Author: 李季东
 * Create Time: 2020/7/8
 * Update Time: 2020/7/14
 * 视频详情页面
 */
public class FriendFragment extends Fragment {

    private FriendViewModel friendViewModel;
    FragmentFriendBinding fragmentFriendBinding;
    List<Movie> movieList;

    public int firstVisibleItem = 0, lastVisibleItem = 0, VisibleCount = 0;
    public JzvdStd videoView;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        friendViewModel = new ViewModelProvider(Objects.requireNonNull(getActivity())).get(FriendViewModel.class);
        fragmentFriendBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_friend, container, false);
        fragmentFriendBinding.setViewmodel(friendViewModel);
        fragmentFriendBinding.setLifecycleOwner(getActivity());
        LoadingDialog dialog = new LoadingDialog.Builder(getActivity()).loadText("加载中...").build();
        dialog.show();
        MovieRestService.getMovies(0, new MovieRestCallback() {
            @Override
            public void onSuccess(int resultCode, List<Movie> movies) {
                super.onSuccess(resultCode, movies);
                movieList = movies;
                setRecyclerView();
                dialog.dismiss();
            }
        });
        return fragmentFriendBinding.getRoot();
    }

    private void setRecyclerView() {
        fragmentFriendBinding.recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        fragmentFriendBinding.recyclerView.setAdapter(new MovieAdapter(getActivity(), movieList));
        fragmentFriendBinding.recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
                firstVisibleItem = layoutManager.findFirstVisibleItemPosition();
                lastVisibleItem = layoutManager.findLastVisibleItemPosition();
                VisibleCount = lastVisibleItem - firstVisibleItem;
            }

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                switch (newState) {
                    case RecyclerView.SCROLL_STATE_IDLE://停止滚动
                        /**在这里执行，视频的自动播放与停止*/
                        autoPlayVideo(recyclerView);
                        break;
                    case RecyclerView.SCROLL_STATE_DRAGGING://拖动
                        autoPlayVideo(recyclerView);
                        break;
                    case RecyclerView.SCROLL_STATE_SETTLING://性滑动
                        JzvdStd.releaseAllVideos();
                        break;
                }

            }
        });
    }

    @Nullable
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    private void autoPlayVideo(RecyclerView recyclerView) {
        for(int i=0;i<VisibleCount;i++) {
            if (recyclerView != null && recyclerView.getChildAt(i) != null &&recyclerView.getChildAt(i).findViewById(R.id.video) != null){
                videoView = (JzvdStd) recyclerView.getChildAt(i).findViewById(R.id.video);
                Rect rect = new Rect();
                videoView.getLocalVisibleRect(rect);//获取视图本身的可见坐标，把值传入到rect对象中
                int videoHeight = videoView.getHeight();//获取视频的高度
                if(rect.top==0&&rect.bottom==videoHeight){
                    if(videoView.state == Jzvd.STATE_NORMAL||videoView.state==Jzvd.STATE_PAUSE){
                        videoView.startVideo();
                    }
                    return;
                }
                videoView.releaseAllVideos();
            } else{
                if(videoView!=null){
                    videoView.releaseAllVideos();
                }
            }
        }
    }
}
/*public class FriendFragment extends Fragment {

    private  FriendViewModel friendViewModel;
    FragmentFriendBinding fragmentFriendBinding;
    SettingsFriendAdpater mAdapter;

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
        initAdapter();
        fragmentFriendBinding.recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        return fragmentFriendBinding.getRoot();
    }
    private void initAdapter() {
        mAdapter=new SettingsFriendAdpater(getActivity(), new SettingsFriendAdpater.OnItemClickListener()
        {
            @Override
            public void onClick(int pos)
            {
                //点击item发生的事情
                //Toast.makeText(getActivity(), "click..." + pos, Toast.LENGTH_SHORT).show();
//                Intent intent =new Intent(getActivity(), VideoDetialActivity.class);
//                startActivity(intent);
            }
        });
        fragmentFriendBinding.recyclerView.setAdapter(mAdapter);
    }
}*/
