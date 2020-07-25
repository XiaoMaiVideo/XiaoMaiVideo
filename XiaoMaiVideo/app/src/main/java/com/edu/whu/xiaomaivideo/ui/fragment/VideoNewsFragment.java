/**
 * Author: 张俊杰、叶俊豪、付浩
 * Create Time: 2020/7/10
 * Update Time: 2020/7/19
 */

package com.edu.whu.xiaomaivideo.ui.fragment;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import android.graphics.Rect;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.edu.whu.xiaomaivideo.R;
import com.edu.whu.xiaomaivideo.adapter.VideoNewsAdapter;
import com.edu.whu.xiaomaivideo.databinding.VideoNewsFragmentBinding;
import com.edu.whu.xiaomaivideo.model.Movie;
import com.edu.whu.xiaomaivideo.model.User;
import com.edu.whu.xiaomaivideo.restcallback.MovieRestCallback;
import com.edu.whu.xiaomaivideo.restservice.MovieRestService;
import com.edu.whu.xiaomaivideo.viewModel.VideoNewsViewModel;
import com.jiajie.load.LoadingDialog;

import java.util.List;
import java.util.Objects;

import cn.jzvd.Jzvd;
import cn.jzvd.JzvdStd;

public class VideoNewsFragment extends Fragment {

    private VideoNewsViewModel videoNewsViewModel;
    VideoNewsFragmentBinding videoNewsFragmentBinding;
    List<Movie> movieList;

    public int firstVisibleItem = 0, lastVisibleItem = 0, VisibleCount = 0;
    public JzvdStd videoView;

    User mUser;

    public VideoNewsFragment(User user) {
        mUser = user;
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        videoNewsViewModel = new ViewModelProvider(Objects.requireNonNull(getActivity())).get(VideoNewsViewModel.class);
        videoNewsFragmentBinding = DataBindingUtil.inflate(inflater, R.layout.video_news_fragment, container, false);
        videoNewsFragmentBinding.setViewmodel(videoNewsViewModel);
        videoNewsFragmentBinding.setLifecycleOwner(getActivity());
        setRecyclerView();
        return videoNewsFragmentBinding.getRoot();
    }

    private void setRecyclerView() {
        videoNewsFragmentBinding.videoNewsFragmentRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        videoNewsFragmentBinding.videoNewsFragmentRecyclerView.setAdapter(new VideoNewsAdapter(getActivity(), mUser.getShares(), mUser));
        videoNewsFragmentBinding.videoNewsFragmentRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
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