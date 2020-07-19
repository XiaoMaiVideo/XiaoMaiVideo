/**
 * Author: 张俊杰、叶俊豪
 * Create Time: 2020/7/10
 * Update Time: 2020/7/12
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

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.edu.whu.xiaomaivideo.R;
import com.edu.whu.xiaomaivideo.databinding.UserVideoWorksFragmentBinding;
import com.edu.whu.xiaomaivideo.viewModel.UserVideoWorksViewModel;

import androidx.recyclerview.widget.RecyclerView;

import com.edu.whu.xiaomaivideo.adapter.MovieAdapter;
import com.edu.whu.xiaomaivideo.model.Movie;
import com.edu.whu.xiaomaivideo.restcallback.MovieRestCallback;
import com.edu.whu.xiaomaivideo.restservice.MovieRestService;
import com.jiajie.load.LoadingDialog;

import java.util.List;
import java.util.Objects;

import cn.jzvd.Jzvd;
import cn.jzvd.JzvdStd;

public class UserVideoWorksFragment extends Fragment {

    private UserVideoWorksViewModel userVideoWorksViewModel;
    UserVideoWorksFragmentBinding fragmentUserVideoWorksBinding;
    List<Movie> movieList;

    public int firstVisibleItem = 0, lastVisibleItem = 0, VisibleCount = 0;
    public JzvdStd videoView;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        userVideoWorksViewModel = new ViewModelProvider(Objects.requireNonNull(getActivity())).get(UserVideoWorksViewModel.class);
        fragmentUserVideoWorksBinding = DataBindingUtil.inflate(inflater, R.layout.user_video_works_fragment, container, false);
        fragmentUserVideoWorksBinding.setViewmodel(userVideoWorksViewModel);
        fragmentUserVideoWorksBinding.setLifecycleOwner(getActivity());
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
        return fragmentUserVideoWorksBinding.getRoot();
    }

    private void setRecyclerView() {
        fragmentUserVideoWorksBinding.myVideoWorksFragmentRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        fragmentUserVideoWorksBinding.myVideoWorksFragmentRecyclerView.setAdapter(new MovieAdapter(getActivity(), movieList));
        fragmentUserVideoWorksBinding.myVideoWorksFragmentRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
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
/*public class UserVideoWorksFragment extends Fragment {

    private UserVideoWorksViewModel mViewModel;
    private UserVideoWorksFragmentBinding userVideoWorksFragmentBinding;

    public static UserVideoWorksFragment newInstance() {
        return new UserVideoWorksFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mViewModel = new ViewModelProvider(Objects.requireNonNull(getActivity())).get(UserVideoWorksViewModel.class);

        userVideoWorksFragmentBinding = DataBindingUtil.inflate(inflater, R.layout.user_video_works_fragment, container, false);
        userVideoWorksFragmentBinding.setViewmodel(mViewModel);
        userVideoWorksFragmentBinding.setLifecycleOwner(getActivity());

        userVideoWorksFragmentBinding.myVideoWorksFragmentRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        userVideoWorksFragmentBinding.myVideoWorksFragmentRecyclerView.setAdapter(new SettingsFriendAdpater(getActivity(), new SettingsFriendAdpater.OnItemClickListener()
        {
            @Override
            public void onClick(int pos)
            {
                Toast.makeText(getActivity(), "click..." + pos, Toast.LENGTH_SHORT).show();
            }
        }));

        return userVideoWorksFragmentBinding.getRoot();
    }

}*/