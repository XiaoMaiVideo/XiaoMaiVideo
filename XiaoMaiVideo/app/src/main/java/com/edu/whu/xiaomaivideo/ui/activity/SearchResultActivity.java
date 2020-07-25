/**
 * Author: 叶俊豪
 * Create Time: 2020/7/22
 * Update Time: 2020/7/24
 */
package com.edu.whu.xiaomaivideo.ui.activity;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.graphics.Rect;
import android.os.Bundle;
import android.widget.SearchView;

import com.edu.whu.xiaomaivideo.R;
import com.edu.whu.xiaomaivideo.adapter.UserAdapter;
import com.edu.whu.xiaomaivideo.adapter.MovieAdapter;
import com.edu.whu.xiaomaivideo.model.Movie;
import com.edu.whu.xiaomaivideo.model.User;
import com.edu.whu.xiaomaivideo.restcallback.MovieRestCallback;
import com.edu.whu.xiaomaivideo.restcallback.UserRestCallback;
import com.edu.whu.xiaomaivideo.restservice.MovieRestService;
import com.edu.whu.xiaomaivideo.restservice.UserRestService;
import com.jiajie.load.LoadingDialog;
import com.llollox.androidtoggleswitch.widgets.ToggleSwitch;
import com.lxj.xpopup.XPopup;
import com.lxj.xpopup.core.BasePopupView;

import java.util.List;

import cn.jzvd.Jzvd;
import cn.jzvd.JzvdStd;
import qiu.niorgai.StatusBarCompat;

public class SearchResultActivity extends Activity {

    String mKeyword, mType;
    List<Movie> movieList;
    List<User> userList;
    UserAdapter mUserAdapter;
    Movie mMovieAdapter;
    RecyclerView mRecyclerView;
    ToggleSwitch mSearchType;
    SearchView mSearchView;
    boolean isOnScrollListenerSet = false;

    public int firstVisibleItem = 0, lastVisibleItem = 0, VisibleCount = 0;
    public JzvdStd videoView;
    RecyclerView.OnScrollListener mScrollListener = new RecyclerView.OnScrollListener() {
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
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_result);
        mKeyword = getIntent().getStringExtra("keyword");
        mType = getIntent().getStringExtra("type");
        mRecyclerView = findViewById(R.id.searchResultRecyclerView);
        mSearchView = findViewById(R.id.searchView);
        mSearchType = findViewById(R.id.searchType);
        init();
        initSearch();
        StatusBarCompat.setStatusBarColor(this, getResources().getColor(R.color.gainsboro));
    }

    private void init() {
        BasePopupView popupView = new XPopup.Builder(this).asLoading().setTitle("加载中...").show();
        if (mType.equals("movie")) {
            MovieRestService.searchMovie(mKeyword, new MovieRestCallback() {
                @Override
                public void onSuccess(int resultCode, List<Movie> movies) {
                    super.onSuccess(resultCode, movies);
                    movieList = movies;
                    setMovieRecyclerView();
                    popupView.dismiss();
                }
            });
        }
        else {
            UserRestService.searchUser(mKeyword, new UserRestCallback() {
                @Override
                public void onSuccess(int resultCode, List<User> users) {
                    super.onSuccess(resultCode, users);
                    userList = users;
                    setUserRecyclerView();
                    popupView.dismiss();
                }
            });
        }
    }

    private void initSearch() {
        if (mType.equals("movie")) {
            mSearchType.setCheckedPosition(0);
        }
        else {
            mSearchType.setCheckedPosition(1);
        }
        mSearchView.setQuery(mKeyword, false);
        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                // 不用管这个报错，我也不知道为什么这里报错
                int type = mSearchType.getCheckedPosition();
                if (type == 0) {
                    mKeyword = s;
                    mType = "movie";
                    init();
                }
                else {
                    mKeyword = s;
                    mType = "user";
                    init();
                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }
        });
    }

    private void setMovieRecyclerView() {
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(new MovieAdapter(this, movieList));
        if (!isOnScrollListenerSet) {
            mRecyclerView.addOnScrollListener(mScrollListener);
            isOnScrollListenerSet = true;
        }
    }

    private void setUserRecyclerView() {
        mUserAdapter = new UserAdapter(this, true);
        mUserAdapter.setUsers(userList);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(mUserAdapter);
        if (isOnScrollListenerSet) {
            mRecyclerView.removeOnScrollListener(mScrollListener);
            isOnScrollListenerSet = false;
        }
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
