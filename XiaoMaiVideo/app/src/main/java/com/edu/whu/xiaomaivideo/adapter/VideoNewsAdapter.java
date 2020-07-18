/**
 * Author: 付浩，叶俊豪
 * Create Time: 2020/7/15
 * Update Time: 2020/7/15
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
import com.edu.whu.xiaomaivideo.ui.activity.VideoDetailActivity;
import com.edu.whu.xiaomaivideo.ui.dialog.ProgressDialog;
import com.edu.whu.xiaomaivideo.util.Constant;
import com.edu.whu.xiaomaivideo.util.EventBusMessage;
import com.edu.whu.xiaomaivideo.util.InsertVideoUtil;
import com.lxj.xpopup.XPopup;
import com.lxj.xpopup.interfaces.OnSelectListener;
import com.sackcentury.shinebuttonlib.ShineButton;

import org.greenrobot.eventbus.EventBus;
import org.parceler.Parcels;

import java.io.File;
import java.util.List;

import cn.jzvd.Jzvd;
import cn.jzvd.JzvdStd;


public class VideoNewsAdapter extends RecyclerView.Adapter<VideoNewsAdapter.MyViewHolder> {

    public static final String TAG = "AdapterRecyclerView";
    // int[] videoIndexs = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9};
    List<Movie> mMovies;
    private Context context;

    public VideoNewsAdapter(Context context, List<Movie> movies) {
        this.context = context;
        this.mMovies = movies;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.video_news_item, parent, false));
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
                .into(holder.originator_Avatar);
        holder.originator_Name.setText(mMovies.get(position).getPublisher().getNickname());
        holder.originator_publishTime.setText(mMovies.get(position).getPublishTime());
        holder.originator_movieDescription.setText(mMovies.get(position).getDescription());

        // TODO: 其他消息暂未显示到item上
        holder.likeButton.setChecked(Constant.CurrentUser.isLikeMovie(mMovies.get(position).getMovieId()));
        // holder.itemView.setOnClickListener(view -> mListener.onClick(position));
    }
    @Override
    public int getItemCount() {
        return mMovies.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        JzvdStd jzvdStd;
        ImageView Forwarder_Avatar, shareButton,originator_Avatar;
        TextView Forwarder_Nickname, Forwarder_publishTime, Forwarder_movieDescription, likeNum, starNum, commentNum,originator_Name,originator_publishTime,originator_movieDescription;
        ShineButton likeButton, starButton;
        ConstraintLayout videoInfoLayout;
        public MyViewHolder(View itemView) {
            super(itemView);
            Forwarder_Avatar = itemView.findViewById(R.id.forwarderImage);
            Forwarder_Nickname = itemView.findViewById(R.id.forwarderText);
            Forwarder_publishTime = itemView.findViewById(R.id.forwarderPostTime);
            Forwarder_movieDescription = itemView.findViewById(R.id.forwarderVideoDescription);
            originator_Avatar = itemView.findViewById(R.id.originatorImage);
            originator_Name = itemView.findViewById(R.id.originatorText);
            originator_publishTime = itemView.findViewById(R.id.originatorPostTime);
            originator_movieDescription = itemView.findViewById(R.id.originatorVideoDescription);
            jzvdStd = itemView.findViewById(R.id.originatorVideo);
            shareButton = itemView.findViewById(R.id.shareButton);
            likeButton = itemView.findViewById(R.id.likebutton);
            starButton = itemView.findViewById(R.id.starbutton);
            likeNum = itemView.findViewById(R.id.textView6);
            starNum = itemView.findViewById(R.id.textView7);
            commentNum = itemView.findViewById(R.id.textView8);
            videoInfoLayout = itemView.findViewById(R.id.videoInfoLayout);

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

            likeButton.setOnCheckStateChangeListener(new ShineButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(View view, boolean checked) {
                    // mListener.onLikeButtonClick(getAdapterPosition(), likeButton, likeNum);
                    // 按下点赞按钮
                    // 如果用户没点赞，就是点赞
                    if (likeButton.isChecked()) {
                        // TODO: 在界面更新点赞数
                        MessageVO message = new MessageVO();
                        message.setMsgType("like");
                        message.setSenderId(Constant.CurrentUser.getUserId());
                        message.setReceiverId(mMovies.get(getAdapterPosition()).getPublisher().getUserId());
                        message.setMovieId(mMovies.get(getAdapterPosition()).getMovieId());
                        EventBus.getDefault().post(new EventBusMessage(Constant.SEND_MESSAGE, JSON.toJSONString(message)));
                    }
                    // 如果用户点了赞，就是取消点赞
                    else {

                    }
                }
            });

            starButton.setOnCheckStateChangeListener(new ShineButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(View view, boolean checked) {
                    // mListener.onStarButtonClick(getAdapterPosition(), starButton, starNum);
                }
            });

            shareButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // mListener.onShareButtonClick(getAdapterPosition(), shareButton);
                    // 按下分享按钮
                    new XPopup.Builder(context)
                            .atView(shareButton)
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
                                                ProgressDialog progressDialog = new ProgressDialog(context);
                                                new XPopup.Builder(context)
                                                        .asCustom(progressDialog)
                                                        .show();
                                                int downloadId = PRDownloader.download(mMovies.get(getAdapterPosition()).getUrl(), filePath, fileName)
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
