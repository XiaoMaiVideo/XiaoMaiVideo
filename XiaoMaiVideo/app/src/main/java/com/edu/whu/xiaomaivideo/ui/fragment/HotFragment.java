/**
 * Author: 付浩、叶俊豪
 * Create Time: 2020/7/9
 * Update Time: 2020/7/16
 */

package com.edu.whu.xiaomaivideo.ui.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.fastjson.JSON;
import com.downloader.Error;
import com.downloader.OnDownloadListener;
import com.downloader.OnProgressListener;
import com.downloader.PRDownloader;
import com.downloader.Progress;
import com.edu.whu.xiaomaivideo.R;
import com.edu.whu.xiaomaivideo.adapter.MovieAdapter;
import com.edu.whu.xiaomaivideo.databinding.FragmentHotBinding;
import com.edu.whu.xiaomaivideo.model.MessageVO;
import com.edu.whu.xiaomaivideo.model.Movie;
import com.edu.whu.xiaomaivideo.model.User;
import com.edu.whu.xiaomaivideo.restcallback.MovieRestCallback;
import com.edu.whu.xiaomaivideo.restcallback.RestCallback;
import com.edu.whu.xiaomaivideo.restservice.MovieRestService;
import com.edu.whu.xiaomaivideo.restservice.UserRestService;
import com.edu.whu.xiaomaivideo.ui.activity.TakeVideoActivity;
import com.edu.whu.xiaomaivideo.ui.activity.VideoDetailActivity;
import com.edu.whu.xiaomaivideo.ui.dialog.ProgressDialog;
import com.edu.whu.xiaomaivideo.util.Constant;
import com.edu.whu.xiaomaivideo.util.EventBusMessage;
import com.edu.whu.xiaomaivideo.util.InsertVideoUtil;
import com.edu.whu.xiaomaivideo.viewModel.HotViewModel;
import com.jiajie.load.LoadingDialog;
import com.lxj.xpopup.XPopup;
import com.lxj.xpopup.interfaces.OnSelectListener;
import com.sackcentury.shinebuttonlib.ShineButton;

import org.greenrobot.eventbus.EventBus;
import org.parceler.Parcels;

import java.io.File;
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
                Intent intent = new Intent(getActivity(), VideoDetailActivity.class);
                intent.putExtra("movie", Parcels.wrap(Movie.class, movieList.get(pos)));
                startActivity(intent);
            }

            @Override
            public void onLikeButtonClick(int pos, ShineButton shineButton, TextView likeNum) {
                // 按下点赞按钮
                // 如果用户没点赞，就是点赞
                if (shineButton.isChecked()) {
                    // TODO: 在界面更新点赞数
                    MessageVO message = new MessageVO();
                    message.setMsgType("like");
                    message.setSenderId(Constant.CurrentUser.getUserId());
                    message.setReceiverId(movieList.get(pos).getPublisher().getUserId());
                    message.setMovieId(movieList.get(pos).getMovieId());
                    EventBus.getDefault().post(new EventBusMessage(Constant.SEND_MESSAGE, JSON.toJSONString(message)));
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
            public void onShareButtonClick(int pos, ImageView imageView) {
                // 按下分享按钮
                new XPopup.Builder(getContext())
                        .atView(imageView)
                        .asAttachList(new String[]{"分享到动态", "分享到其他应用"},
                                new int[]{R.drawable.game, R.drawable.food},
                                new OnSelectListener() {
                                    @Override
                                    public void onSelect(int position, String text) {
                                        if (position == 0) {
                                            // TODO: 应用内分享
                                        }
                                        else {
                                            // TODO: 应用外分享
                                            String filePath = Environment.getExternalStorageDirectory().toString() + "/xiaomai/downloadvideo";
                                            String fileName = System.currentTimeMillis() + ".mp4";
                                            ProgressDialog progressDialog = new ProgressDialog(getActivity());
                                            new XPopup.Builder(getActivity())
                                                    .asCustom(progressDialog)
                                                    .show();
                                            int downloadId = PRDownloader.download(movieList.get(pos).getUrl(), filePath, fileName)
                                                    .build()
                                                    .setOnProgressListener(progress -> progressDialog.setProgress((int) (((float)progress.currentBytes/(float)progress.totalBytes)*100)))
                                                    .start(new OnDownloadListener() {
                                                        @Override
                                                        public void onDownloadComplete() {
                                                            progressDialog.dismissWith(new Runnable() {
                                                                @Override
                                                                public void run() {
                                                                    String path = new File(filePath).getPath()+"/"+fileName;
                                                                    Uri uri = InsertVideoUtil.insertVideo(getActivity(), path);
                                                                    Intent shareIntent = new Intent(Intent.ACTION_SEND);
                                                                    shareIntent.setType("video/*");
                                                                    shareIntent.putExtra(Intent.EXTRA_STREAM, uri);
                                                                    // TODO: 回调怎么办？分享到微信好像没回调
                                                                    startActivity(Intent.createChooser(shareIntent, "分享"));
                                                                }
                                                            });
                                                        }

                                                        @Override
                                                        public void onError(Error error) {

                                                        }
                                                    });

                                        }
                                    }
                                })
                        .show();
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
