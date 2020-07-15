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

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.edu.whu.xiaomaivideo.R;

import cn.jzvd.Jzvd;
import cn.jzvd.JzvdStd;


public class VideoAdapter extends RecyclerView.Adapter<VideoAdapter.MyViewHolder> {

    public static final String TAG = "AdapterRecyclerView";
    int[] videoIndexs = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9};
    private Context context;
    private VideoAdapter.OnItemClickListener mListener;

    public VideoAdapter(Context context, OnItemClickListener onItemClickListener) {
        this.context = context;
        this.mListener = onItemClickListener;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.video_item, parent, false));
    }


    @SuppressLint("LongLogTag")
    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Log.i(TAG, "onBindViewHolder [" + holder.jzvdStd.hashCode() + "] position=" + position);

        holder.jzvdStd.setUp("https://v-cdn.zjol.com.cn/280443.mp4", "", Jzvd.SCREEN_NORMAL);
        Glide.with(holder.jzvdStd.getContext()).load("https://v-cdn.zjol.com.cn/280443.mp4").into(holder.jzvdStd.posterImageView);
        holder.itemView.setOnClickListener(view -> mListener.onClick(position));
    }
    @Override
    public int getItemCount() {
        return videoIndexs.length;
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        JzvdStd jzvdStd;

        public MyViewHolder(View itemView) {
            super(itemView);
            jzvdStd = itemView.findViewById(R.id.video);
        }
    }

    public interface OnItemClickListener {
        void onClick(int pos);
    }
}
