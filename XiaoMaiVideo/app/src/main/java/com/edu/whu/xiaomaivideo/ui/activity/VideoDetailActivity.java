/**
 * Author: 张俊杰
 * Create Time: 2020/7/16
 * Update Time: 2020/7/16
 */


package com.edu.whu.xiaomaivideo.ui.activity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.BindingAdapter;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import com.alibaba.fastjson.JSON;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.RequestOptions;
import com.donkingliang.labels.LabelsView;
import com.downloader.Error;
import com.downloader.OnDownloadListener;
import com.downloader.PRDownloader;
import com.edu.whu.xiaomaivideo.R;
import com.edu.whu.xiaomaivideo.databinding.ActivityVideoDetailBinding;
import com.edu.whu.xiaomaivideo.model.MessageVO;
import com.edu.whu.xiaomaivideo.model.User;
import com.edu.whu.xiaomaivideo.restcallback.MovieRestCallback;
import com.edu.whu.xiaomaivideo.restservice.MovieRestService;
import com.edu.whu.xiaomaivideo.ui.dialog.LikersDialog;
import com.edu.whu.xiaomaivideo.ui.dialog.ProgressDialog;
import com.edu.whu.xiaomaivideo.ui.dialog.ShareDialog;
import com.edu.whu.xiaomaivideo.ui.dialog.ShowCommentDialog;
import com.edu.whu.xiaomaivideo.util.Constant;
import com.edu.whu.xiaomaivideo.util.EventBusMessage;
import com.edu.whu.xiaomaivideo.util.InsertVideoUtil;
import com.lxj.xpopup.XPopup;
import com.edu.whu.xiaomaivideo.model.Movie;
import com.edu.whu.xiaomaivideo.viewModel.VideoDetailModel;
import com.lxj.xpopup.core.BasePopupView;
import com.lxj.xpopup.interfaces.OnSelectListener;
import com.sackcentury.shinebuttonlib.ShineButton;

import org.greenrobot.eventbus.EventBus;
import org.parceler.Parcels;

import java.io.File;
import java.util.List;
import java.util.Objects;

import cn.jzvd.Jzvd;
import cn.jzvd.JzvdStd;
import qiu.niorgai.StatusBarCompat;

/**
 * Author: 李季东 张俊杰 叶俊豪
 * Create Time: 2020/7/14
 * Update Time: 2020/7/23
 * 视频详情页面
 */
public class VideoDetailActivity extends AppCompatActivity {

    VideoDetailModel videoDetailModel;
    ActivityVideoDetailBinding activityVideoDetailBinding;

    @BindingAdapter("app:avatarSrc")
    public static void setAvatar(ImageView imageView, String url) {
        // 用户圆形头像
        Glide.with(imageView.getContext())
                .load(url)
                .apply(RequestOptions.bitmapTransform(new CircleCrop()))
                .into(imageView);
    }

    @BindingAdapter("app:videoUrl")
    public static void setVideoUrl(JzvdStd videoView, String url) {
        videoView.setUp(url, "", Jzvd.SCREEN_NORMAL);
        Jzvd.FULLSCREEN_ORIENTATION = ActivityInfo.SCREEN_ORIENTATION_SENSOR;
    }

    @BindingAdapter("app:labels")
    public static void setLabels(LabelsView labelsView, List<String> categoryList) {
        labelsView.setLabels(categoryList);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        videoDetailModel =new ViewModelProvider(Objects.requireNonNull(this)).get(VideoDetailModel.class);
        activityVideoDetailBinding = DataBindingUtil.setContentView(this,R.layout.activity_video_detail);
        activityVideoDetailBinding.setViewmodel(videoDetailModel);
        activityVideoDetailBinding.setLifecycleOwner(this);
        // Bundle bundle = getIntent().getExtras();

        StatusBarCompat.setStatusBarColor(this, getResources().getColor(R.color.gainsboro));
        // 当前页面显示的Movie对象，可以用它来做数据绑定之类的
        Movie movie = Parcels.unwrap(getIntent().getParcelableExtra("movie"));

        // 有些信息可能没获取到，需要再访问网络，就在这里访问一下
        videoDetailModel.setMovie(movie);

        BasePopupView popupView = new XPopup.Builder(this).asLoading().setTitle("加载中...").show();
        MovieRestService.getMovieByID(movie.getMovieId(), new MovieRestCallback() {
            @Override
            public void onSuccess(int resultCode, Movie movie) {
                super.onSuccess(resultCode, movie);
                if (resultCode == Constant.RESULT_SUCCESS) {
                    videoDetailModel.setMovie(movie);
                    setView();
                    popupView.dismiss();
                }
            }
        });
    }

    private void setView() {

        if (videoDetailModel.getMovie().getValue().getCategoryList().size() == 0) {
            activityVideoDetailBinding.detailTags.setVisibility(View.GONE);
        }

        if (videoDetailModel.getMovie().getValue().getDescription().equals("")) {
            activityVideoDetailBinding.detailVideoDescription.setVisibility(View.GONE);
        }

        if (videoDetailModel.getMovie().getValue().getLocation().equals("")) {
            activityVideoDetailBinding.detailLocationInfoButton.setVisibility(View.GONE);
        }

        activityVideoDetailBinding.detailLikeNum.setText(videoDetailModel.getMovie().getValue().getLikednum()+"");
        activityVideoDetailBinding.detailCommentNum.setText(videoDetailModel.getMovie().getValue().getCommentnum()+"");
        activityVideoDetailBinding.detailShareNum.setText(videoDetailModel.getMovie().getValue().getSharenum()+"");

        activityVideoDetailBinding.detailAuthorImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(VideoDetailActivity.this, UserInfoActivity.class);
                intent.putExtra("user", Parcels.wrap(User.class, videoDetailModel.getMovie().getValue().getPublisher()));
                startActivity(intent);
            }
        });

        activityVideoDetailBinding.detailTags.setOnLabelClickListener(new LabelsView.OnLabelClickListener() {
            @Override
            public void onLabelClick(TextView label, Object data, int position) {
                Intent intent=new Intent(VideoDetailActivity.this, MovieTypeActivity.class);
                intent.putExtra("type", String.valueOf(data));
                startActivity(intent);
            }
        });

        activityVideoDetailBinding.detailLikeButton.setChecked(videoDetailModel.getMovie().getValue().isLike());
        activityVideoDetailBinding.detailLikeButton.setOnCheckStateChangeListener(new ShineButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(View view, boolean checked) {
                if (Constant.currentUser.getUserId() == 0) {
                    // 没登录，不允许操作
                    Toast.makeText(VideoDetailActivity.this, "你尚未登录，不允许点赞哦...", Toast.LENGTH_LONG).show();
                    activityVideoDetailBinding.detailLikeButton.setChecked(false);
                }
                else {
                    Movie currentMovie = videoDetailModel.getMovie().getValue();
                    // 按下点赞按钮
                    // 如果用户没点赞，就是点赞
                    if (activityVideoDetailBinding.detailLikeButton.isChecked()) {
                        currentMovie.setLikednum(currentMovie.getLikednum()+1);
                        activityVideoDetailBinding.detailLikeNum.setText(currentMovie.getLikednum()+"");
                        MessageVO message = new MessageVO();
                        message.setMsgType("like");
                        message.setSenderId(Constant.currentUser.getUserId());
                        message.setReceiverId(currentMovie.getPublisher().getUserId());
                        message.setMovieId(currentMovie.getMovieId());
                        EventBus.getDefault().post(new EventBusMessage(Constant.SEND_MESSAGE, JSON.toJSONString(message)));
                    }
                    // 如果用户点了赞，就是取消点赞
                    else {
                        currentMovie.setLikednum(currentMovie.getLikednum()-1);
                        activityVideoDetailBinding.detailLikeNum.setText(currentMovie.getLikednum()+"");
                        MessageVO message = new MessageVO();
                        message.setMsgType("unlike");
                        message.setSenderId(Constant.currentUser.getUserId());
                        message.setReceiverId(currentMovie.getPublisher().getUserId());
                        message.setMovieId(currentMovie.getMovieId());
                        EventBus.getDefault().post(new EventBusMessage(Constant.SEND_MESSAGE, JSON.toJSONString(message)));
                    }
                }
            }
        });

        activityVideoDetailBinding.detailLike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new XPopup.Builder(VideoDetailActivity.this)
                        .asCustom(new LikersDialog(VideoDetailActivity.this, videoDetailModel.getMovie().getValue()))
                        .show();
            }
        });

        //评论
        activityVideoDetailBinding.detailComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Movie currentMovie = videoDetailModel.getMovie().getValue();
                new ShowCommentDialog(VideoDetailActivity.this, currentMovie, new ShowCommentDialog.OnAddCommentListener() {
                    @Override
                    public void onAddComment() {
                        currentMovie.setCommentnum(currentMovie.getCommentnum()+1);
                        activityVideoDetailBinding.detailCommentNum.setText(currentMovie.getCommentnum()+"");
                    }
                }).show();
            }
        });

        activityVideoDetailBinding.detailShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 按下分享按钮
                Movie currentMovie = videoDetailModel.getMovie().getValue();
                new XPopup.Builder(VideoDetailActivity.this)
                        .atView(activityVideoDetailBinding.detailShareButton)
                        .asAttachList(new String[]{"分享到动态", "分享到其他应用"},
                                new int[]{R.drawable.game, R.drawable.food},
                                new OnSelectListener() {
                                    @Override
                                    public void onSelect(int position, String text) {
                                        if (position == 0) {
                                            if (Constant.currentUser.getUserId() == 0) {
                                                // 没登录，不允许操作
                                                Toast.makeText(VideoDetailActivity.this, "你尚未登录，不允许分享哦...", Toast.LENGTH_LONG).show();
                                            }
                                            else {
                                                BasePopupView popupView = new XPopup.Builder(VideoDetailActivity.this)
                                                        .asCustom(new ShareDialog(VideoDetailActivity.this, currentMovie, new ShareDialog.OnShareMsgSendListener() {
                                                            @Override
                                                            public void onShareMsgSend() {
                                                                currentMovie.setSharenum(currentMovie.getSharenum()+1);
                                                                activityVideoDetailBinding.detailShareNum.setText(currentMovie.getSharenum()+"");
                                                            }
                                                        }))
                                                        .show();
                                            }
                                        }
                                        else {
                                            // TODO: 修复BUG
                                            String filePath = Environment.getExternalStorageDirectory().toString() + "/xiaomai/downloadvideo";
                                            String fileName = System.currentTimeMillis() + ".mp4";
                                            ProgressDialog progressDialog = new ProgressDialog(VideoDetailActivity.this, "加载中");
                                            new XPopup.Builder(VideoDetailActivity.this)
                                                    .asCustom(progressDialog)
                                                    .show();
                                            int downloadId = PRDownloader.download(currentMovie.getUrl(), filePath, fileName)
                                                    .build()
                                                    .setOnProgressListener(progress -> progressDialog.setProgress((int) (((float)progress.currentBytes/(float)progress.totalBytes)*100)))
                                                    .start(new OnDownloadListener() {
                                                        @Override
                                                        public void onDownloadComplete() {
                                                            progressDialog.dismissWith(new Runnable() {
                                                                @Override
                                                                public void run() {
                                                                    String path = new File(filePath).getPath()+"/"+fileName;
                                                                    Uri uri = InsertVideoUtil.insertVideo(VideoDetailActivity.this, path);
                                                                    Intent shareIntent = new Intent(Intent.ACTION_SEND);
                                                                    shareIntent.setType("video/*");
                                                                    shareIntent.putExtra(Intent.EXTRA_STREAM, uri);
                                                                    // TODO: 回调怎么办？分享到微信好像没回调
                                                                    VideoDetailActivity.this.startActivity(Intent.createChooser(shareIntent, "分享"));
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
        });
    }
}
