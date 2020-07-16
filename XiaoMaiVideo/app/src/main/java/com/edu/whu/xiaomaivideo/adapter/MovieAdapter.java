/**
 * Author: 付浩，叶俊豪
 * Create Time: 2020/7/15
 * Update Time: 2020/7/15
 */
package com.edu.whu.xiaomaivideo.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.RequestOptions;
import com.edu.whu.xiaomaivideo.R;
import com.edu.whu.xiaomaivideo.model.Movie;
import com.edu.whu.xiaomaivideo.util.Constant;
import com.sackcentury.shinebuttonlib.ShineButton;

import java.util.ArrayList;
import java.util.List;

import cn.jzvd.Jzvd;
import cn.jzvd.JzvdStd;


public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MyViewHolder> {

    public static final String TAG = "AdapterRecyclerView";
    // int[] videoIndexs = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9};
    List<Movie> mMovies;
    private Context context;
    private MovieAdapter.OnItemClickListener mListener;

    public MovieAdapter(Context context, List<Movie> movies, OnItemClickListener onItemClickListener) {
        this.context = context;
        this.mListener = onItemClickListener;
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
        ImageView userAvatar, shareButton;
        TextView userNickname, publishTime, movieDescription;
        ShineButton likeButton, starButton;
        ConstraintLayout videoInfoLayout;
        public MyViewHolder(View itemView) {
            super(itemView);
            userAvatar = itemView.findViewById(R.id.authorImage);
            userNickname = itemView.findViewById(R.id.authorText);
            publishTime = itemView.findViewById(R.id.postTime);
            movieDescription = itemView.findViewById(R.id.videoDescription);
            jzvdStd = itemView.findViewById(R.id.video);
            shareButton = itemView.findViewById(R.id.shareButton);
            likeButton = itemView.findViewById(R.id.likebutton);
            starButton = itemView.findViewById(R.id.starbutton);
            videoInfoLayout = itemView.findViewById(R.id.videoInfoLayout);
            videoInfoLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Log.e(TAG, "点击了"+getAdapterPosition());
                    mListener.onItemClick(getAdapterPosition());
                }
            });

            likeButton.setOnCheckStateChangeListener(new ShineButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(View view, boolean checked) {
                    mListener.onLikeButtonClick(getAdapterPosition(), likeButton);
                }
            });

            starButton.setOnCheckStateChangeListener(new ShineButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(View view, boolean checked) {
                    mListener.onStarButtonClick(getAdapterPosition(), starButton);
                }
            });

            shareButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mListener.onShareButtonClick(getAdapterPosition());
                }
            });
        }
    }

    public interface OnItemClickListener {
        void onItemClick(int pos);
        void onLikeButtonClick(int pos, ShineButton shineButton);
        void onStarButtonClick(int pos, ShineButton shineButton);
        void onShareButtonClick(int pos);
    }
}
