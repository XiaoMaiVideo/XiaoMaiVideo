/**
 * Author: 叶俊豪
 * Create Time: 2020/7/18
 * Update Time: 2020/7/18
 */
package com.edu.whu.xiaomaivideo.widget;

import android.content.Context;
import android.graphics.Rect;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.edu.whu.xiaomaivideo.R;
import com.edu.whu.xiaomaivideo.adapter.MovieAdapter;

import cn.jzvd.Jzvd;
import cn.jzvd.JzvdStd;

public class MovieRecyclerView {

    public RecyclerView mRecyclerView;
    private JzvdStd videoView;
    private int firstVisibleItem = 0, lastVisibleItem = 0, VisibleCount = 0;

    public MovieRecyclerView() {
    }

    public MovieRecyclerView(RecyclerView recyclerView) {
        mRecyclerView = recyclerView;
        setRecyclerView();
    }

    private void setRecyclerView() {
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
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
        for(int i=0; i<VisibleCount; i++) {
            if (recyclerView != null && recyclerView.getChildAt(i) != null &&recyclerView.getChildAt(i).findViewById(R.id.video) != null){
                videoView = (JzvdStd) recyclerView.getChildAt(i).findViewById(R.id.video);
                Rect rect = new Rect();
                videoView.getLocalVisibleRect(rect);//获取视图本身的可见坐标，把值传入到rect对象中
                int videoHeight = videoView.getHeight();//获取视频的高度
                if(rect.top==0&&rect.bottom==videoHeight){
                    if (videoView.state == Jzvd.STATE_NORMAL||videoView.state==Jzvd.STATE_PAUSE){
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
