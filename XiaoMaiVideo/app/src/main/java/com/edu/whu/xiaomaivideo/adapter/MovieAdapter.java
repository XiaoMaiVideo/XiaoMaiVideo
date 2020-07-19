/**
 * Author: 付浩，叶俊豪、李季东
 * Create Time: 2020/7/15
 * Update Time: 2020/7/18
 */
package com.edu.whu.xiaomaivideo.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.fastjson.JSON;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.RequestOptions;
import com.downloader.Error;
import com.downloader.OnDownloadListener;
import com.downloader.PRDownloader;
import com.edu.whu.xiaomaivideo.R;
import com.edu.whu.xiaomaivideo.model.MessageVO;
import com.edu.whu.xiaomaivideo.model.Movie;
import com.edu.whu.xiaomaivideo.model.User;
import com.edu.whu.xiaomaivideo.ui.activity.UserInfoActivity;
import com.edu.whu.xiaomaivideo.ui.activity.VideoDetailActivity;
import com.edu.whu.xiaomaivideo.ui.dialog.ProgressDialog;
import com.edu.whu.xiaomaivideo.ui.dialog.ShowCommentDialog;
import com.edu.whu.xiaomaivideo.ui.dialog.SimpleBottomDialog;
import com.edu.whu.xiaomaivideo.util.Constant;
import com.edu.whu.xiaomaivideo.util.EventBusMessage;
import com.edu.whu.xiaomaivideo.util.InsertVideoUtil;
import com.google.android.material.button.MaterialButton;
import com.lxj.xpopup.XPopup;
import com.lxj.xpopup.core.BasePopupView;
import com.lxj.xpopup.interfaces.OnSelectListener;
import com.sackcentury.shinebuttonlib.ShineButton;

import org.greenrobot.eventbus.EventBus;
import org.parceler.Parcels;

import java.io.File;
import java.util.List;

import cn.jzvd.Jzvd;
import cn.jzvd.JzvdStd;


public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MyViewHolder> {

    public static final String TAG = "AdapterRecyclerView";
    // int[] videoIndexs = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9};
    List<Movie> mMovies;
    private Context context;

    public MovieAdapter(Context context, List<Movie> movies) {
        this.context = context;
        this.mMovies = movies;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.video_item, parent, false));
    }


    @SuppressLint("LongLogTag")
    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Log.i(TAG, "onBindViewHolder [" + holder.jzvdStd.hashCode() + "] position=" + position);

        holder.jzvdStd.setUp(mMovies.get(position).getUrl(), "", Jzvd.SCREEN_NORMAL);

        Glide.with(context)
                .load(mMovies.get(position).getPublisher().getAvatar())
                .apply(RequestOptions.bitmapTransform(new CircleCrop()))
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .into(holder.userAvatar);
        holder.userNickname.setText(mMovies.get(position).getPublisher().getNickname());
        holder.publishTime.setText(mMovies.get(position).getPublishTime());
        holder.movieDescription.setText(mMovies.get(position).getDescription());

        // TODO: 类别的标签还没显示
        holder.likeButton.setChecked(Constant.CurrentUser.isLikeMovie(mMovies.get(position).getMovieId()));
        holder.shareNum.setText(mMovies.get(position).getSharenum()+"");
        holder.commentNum.setText(mMovies.get(position).getCommentnum()+"");
        holder.likeNum.setText(mMovies.get(position).getLikednum()+"");
        if (mMovies.get(position).getLocation().equals("")) {
            holder.locationInfoButton.setVisibility(View.GONE);
        }
        else {
            holder.locationInfoButton.setText(mMovies.get(position).getLocation());
        }
    }
    @Override
    public int getItemCount() {
        return mMovies.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        JzvdStd jzvdStd;
        ImageView userAvatar, shareButton, commentButton;
        TextView userNickname, publishTime, movieDescription, likeNum, commentNum, shareNum;
        ShineButton likeButton;
        ConstraintLayout videoInfoLayout;
        MaterialButton locationInfoButton;
        public MyViewHolder(View itemView) {
            super(itemView);
            userAvatar = itemView.findViewById(R.id.authorImage);
            userNickname = itemView.findViewById(R.id.authorText);
            publishTime = itemView.findViewById(R.id.postTime);
            movieDescription = itemView.findViewById(R.id.videoDescription);
            jzvdStd = itemView.findViewById(R.id.video);
            shareButton = itemView.findViewById(R.id.shareButton);
            commentButton = itemView.findViewById(R.id.commentButton);
            likeButton = itemView.findViewById(R.id.likeButton);
            likeNum = itemView.findViewById(R.id.likeNum);
            commentNum = itemView.findViewById(R.id.commentNum);
            shareNum = itemView.findViewById(R.id.shareNum);
            videoInfoLayout = itemView.findViewById(R.id.videoInfoLayout);
            locationInfoButton = itemView.findViewById(R.id.locationInfoButton);




            videoInfoLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // mListener.onItemClick(getAdapterPosition());
                    // 跳转进入详情页面
                    Intent intent = new Intent(context, VideoDetailActivity.class);
                    intent.putExtra("movie", Parcels.wrap(Movie.class, mMovies.get(getAdapterPosition())));
                    context.startActivity(intent);
                }
            });

            userAvatar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, UserInfoActivity.class);
                    intent.putExtra("user", Parcels.wrap(User.class, mMovies.get(getAdapterPosition()).getPublisher()));
                    context.startActivity(intent);
                }
            });

            likeButton.setOnCheckStateChangeListener(new ShineButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(View view, boolean checked) {
                    if (Constant.CurrentUser.getUserId() == 0) {
                        // 没登录，不允许操作
                        BasePopupView popupView = new XPopup.Builder(context)
                                .asCustom(new SimpleBottomDialog(context, R.drawable.success, "没有登录，不能点赞哦"))
                                .show();
                        popupView.delayDismiss(1500);
                        likeButton.setChecked(false);
                    }
                    else {
                        Movie currentMovie = mMovies.get(getAdapterPosition());
                        // 按下点赞按钮
                        // 如果用户没点赞，就是点赞
                        if (likeButton.isChecked()) {
                            currentMovie.setLikednum(currentMovie.getLikednum()+1);
                            likeNum.setText(currentMovie.getLikednum()+"");
                            MessageVO message = new MessageVO();
                            message.setMsgType("like");
                            message.setSenderId(Constant.CurrentUser.getUserId());
                            message.setReceiverId(currentMovie.getPublisher().getUserId());
                            message.setMovieId(currentMovie.getMovieId());
                            EventBus.getDefault().post(new EventBusMessage(Constant.SEND_MESSAGE, JSON.toJSONString(message)));
                        }
                        // 如果用户点了赞，就是取消点赞
                        else {
                            mMovies.get(getAdapterPosition()).setLikednum(currentMovie.getLikednum()-1);
                            likeNum.setText(currentMovie.getLikednum()+"");
                            MessageVO message = new MessageVO();
                            message.setMsgType("unlike");
                            message.setSenderId(Constant.CurrentUser.getUserId());
                            message.setReceiverId(currentMovie.getPublisher().getUserId());
                            message.setMovieId(currentMovie.getMovieId());
                            EventBus.getDefault().post(new EventBusMessage(Constant.SEND_MESSAGE, JSON.toJSONString(message)));
                        }
                    }
                }
            });

            //评论
            commentButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Movie currentMovie = mMovies.get(getAdapterPosition());
                    new ShowCommentDialog(context, mMovies.get(getAdapterPosition()), new ShowCommentDialog.OnAddCommentListener() {
                        @Override
                        public void onAddComment() {
                            currentMovie.setCommentnum(currentMovie.getCommentnum()+1);
                            commentNum.setText(currentMovie.getCommentnum()+"");
                        }
                    }).show();
                }
            });

            shareButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // mListener.onShareButtonClick(getAdapterPosition(), shareButton);
                    // 按下分享按钮
                    Movie currentMovie = mMovies.get(getAdapterPosition());
                    new XPopup.Builder(context)
                            .atView(shareButton)
                            .asAttachList(new String[]{"分享到动态", "分享到其他应用"},
                                    new int[]{R.drawable.game, R.drawable.food},
                                    new OnSelectListener() {
                                        @Override
                                        public void onSelect(int position, String text) {
                                            if (position == 0) {
                                                // TODO: 应用内分享
                                                if (Constant.CurrentUser.getUserId() == 0) {
                                                    // 没登录，不允许操作
                                                    BasePopupView popupView = new XPopup.Builder(context)
                                                            .asCustom(new SimpleBottomDialog(context, R.drawable.success, "没有登录，不能分享哦"))
                                                            .show();
                                                    popupView.delayDismiss(1500);
                                                }
                                                else {
                                                    currentMovie.setSharenum(currentMovie.getSharenum()+1);
                                                    shareNum.setText(currentMovie.getSharenum()+"");
                                                    // TODO: 应用内分享，发送后端请求
                                                }
                                            }
                                            else {
                                                // TODO: 应用外分享
                                                String filePath = Environment.getExternalStorageDirectory().toString() + "/xiaomai/downloadvideo";
                                                String fileName = System.currentTimeMillis() + ".mp4";
                                                ProgressDialog progressDialog = new ProgressDialog(context);
                                                new XPopup.Builder(context)
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
                                                                        Uri uri = InsertVideoUtil.insertVideo(context, path);
                                                                        Intent shareIntent = new Intent(Intent.ACTION_SEND);
                                                                        shareIntent.setType("video/*");
                                                                        shareIntent.putExtra(Intent.EXTRA_STREAM, uri);
                                                                        // TODO: 回调怎么办？分享到微信好像没回调
                                                                        context.startActivity(Intent.createChooser(shareIntent, "分享"));
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
}
