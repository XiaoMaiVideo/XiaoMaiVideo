/**
 * Author: 付浩，叶俊豪
 * Create Time: 2020/7/15
 * Update Time: 2020/7/22
 */
package com.edu.whu.xiaomaivideo.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
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
import com.edu.whu.xiaomaivideo.model.Share;
import com.edu.whu.xiaomaivideo.model.User;
import com.edu.whu.xiaomaivideo.ui.activity.VideoDetailActivity;
import com.edu.whu.xiaomaivideo.ui.dialog.ProgressDialog;
import com.edu.whu.xiaomaivideo.util.CommonUtil;
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
    List<Share> mShares;
    User mUser;
    private Context context;

    public VideoNewsAdapter(Context context, List<Share> shares, User user) {
        this.context = context;
        this.mShares = shares;
        this.mUser = user;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.video_news_item, parent, false));
    }


    @SuppressLint("LongLogTag")
    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Log.i(TAG, "onBindViewHolder [" + holder.jzvdStd.hashCode() + "] position=" + position);

        holder.jzvdStd.setUp(mShares.get(position).getMovie().getUrl(), "", Jzvd.SCREEN_NORMAL);
        Jzvd.FULLSCREEN_ORIENTATION = ActivityInfo.SCREEN_ORIENTATION_SENSOR;

        Glide.with(context)
                .load(mShares.get(position).getMovie().getPublisher().getAvatar())
                .apply(RequestOptions.bitmapTransform(new CircleCrop()))
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .into(holder.originator_Avatar);
        holder.originator_Name.setText(mShares.get(position).getMovie().getPublisher().getNickname());
        holder.originator_publishTime.setText(mShares.get(position).getMovie().getPublishTime());
        if (mShares.get(position).getMovie().getDescription() == null || mShares.get(position).getMovie().getDescription().equals("")) {
            holder.originator_movieDescription.setVisibility(View.GONE);
        }
        else {
            holder.originator_movieDescription.setText(mShares.get(position).getMovie().getDescription());
        }

        Glide.with(context)
                .load(mUser.getAvatar())
                .apply(RequestOptions.bitmapTransform(new CircleCrop()))
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .into(holder.Forwarder_Avatar);
        holder.Forwarder_Nickname.setText(mUser.getNickname());
        holder.Forwarder_publishTime.setText(CommonUtil.convertTimeToDateString(mShares.get(position).getShareDate().getTime()));
        if (mShares.get(position).getMsg() == null || mShares.get(position).getMsg().equals("")) {
            holder.Forwarder_movieDescription.setVisibility(View.GONE);
        }
        else {
            holder.Forwarder_movieDescription.setText(mShares.get(position).getMsg());
        }
    }
    @Override
    public int getItemCount() {
        return mShares.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        JzvdStd jzvdStd;
        ImageView Forwarder_Avatar, shareButton,originator_Avatar;
        TextView Forwarder_Nickname, Forwarder_publishTime, Forwarder_movieDescription, originator_Name, originator_publishTime, originator_movieDescription;
        ConstraintLayout videoInfoLayout;
        CardView originVideo;
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
            originVideo = itemView.findViewById(R.id.originVideoCardView);

            originVideo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, VideoDetailActivity.class);
                    intent.putExtra("movie", Parcels.wrap(Movie.class, mShares.get(getAdapterPosition()).getMovie()));
                    context.startActivity(intent);
                }
            });
        }
    }
}
