package com.edu.whu.xiaomaivideo.ui.fragment;

import android.graphics.Rect;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.edu.whu.xiaomaivideo.R;
import com.edu.whu.xiaomaivideo.adapter.MovieAdapter;
import com.edu.whu.xiaomaivideo.databinding.FragmentHotBinding;
import com.edu.whu.xiaomaivideo.model.Movie;
import com.edu.whu.xiaomaivideo.restcallback.MovieRestCallback;
import com.edu.whu.xiaomaivideo.restcallback.RestCallback;
import com.edu.whu.xiaomaivideo.restservice.MovieRestService;
import com.edu.whu.xiaomaivideo.restservice.UserRestService;
import com.edu.whu.xiaomaivideo.util.Constant;
import com.edu.whu.xiaomaivideo.viewModel.HotViewModel;
import com.jiajie.load.LoadingDialog;
import com.sackcentury.shinebuttonlib.ShineButton;

import java.util.List;
import java.util.Objects;

import cn.jzvd.Jzvd;
import cn.jzvd.JzvdStd;

public class HotFragment extends Fragment {

    private HotViewModel hotViewModel;
    FragmentHotBinding fragmentHotBinding;
    List<Movie> movieList;

    public int firstVisibleItem = 0, lastVisibleItem = 0, VisibleCount = 0;
    public JzvdStd videoView;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        hotViewModel = new ViewModelProvider(Objects.requireNonNull(getActivity())).get(HotViewModel.class);
        fragmentHotBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_hot, container, false);
        fragmentHotBinding.setViewmodel(hotViewModel);
        fragmentHotBinding.setLifecycleOwner(getActivity());
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
        return fragmentHotBinding.getRoot();
    }

    private void setRecyclerView() {
        fragmentHotBinding.recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        fragmentHotBinding.recyclerView.setAdapter(new MovieAdapter(getActivity(), movieList, new MovieAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int pos) {
                // 跳转进入详情页面
            }

            @Override
            public void onLikeButtonClick(int pos, ShineButton shineButton, TextView likeNum) {
                // 按下点赞按钮
                // 如果用户没点赞，就是点赞
                if (shineButton.isChecked()) {
                    movieList.get(pos).addLiker(Constant.CurrentUser);
                    // Constant.CurrentUser.addLikeMovies(movieList.get(pos));
                    UserRestService.addUserLike(movieList.get(pos).getMovieId(), new RestCallback() {
                        @Override
                        public void onSuccess(int resultCode) {
                            // 点赞数加1即可
                            likeNum.setText(movieList.get(pos).getLikers().size()+"");
                        }
                    });
                }
                // 如果用户点了赞，就是取消点赞
                else {

                }
            }

            @Override
            public void onStarButtonClick(int pos, ShineButton shineButton, TextView starNum) {
                // 按下收藏按钮
            }

            @Override
            public void onShareButtonClick(int pos) {
                // 按下分享按钮
            }
        }));
        fragmentHotBinding.recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
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
