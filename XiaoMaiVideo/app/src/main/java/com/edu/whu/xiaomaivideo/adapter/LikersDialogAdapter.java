/**
 * Author: 叶俊豪
 * Create Time: 2020/7/22
 * Update Time: 2020/7/25
 */
package com.edu.whu.xiaomaivideo.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.RequestOptions;
import com.donkingliang.groupedadapter.holder.BaseViewHolder;
import com.edu.whu.xiaomaivideo.R;
import com.edu.whu.xiaomaivideo.model.User;
import com.edu.whu.xiaomaivideo.util.Constant;

import java.util.List;

public class LikersDialogAdapter extends RecyclerView.Adapter<BaseViewHolder> {

    Context mContext;
    List<User> mLikers;

    public LikersDialogAdapter(Context context, List<User> likers) {
        mContext = context;
        mLikers = likers;
    }

    @NonNull
    @Override
    public BaseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new BaseViewHolder(LayoutInflater.from(mContext).inflate(R.layout.likers_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull BaseViewHolder holder, int position) {
        Glide.with(mContext)
                .load(mLikers.get(position).getAvatar())
                .apply(RequestOptions.bitmapTransform(new CircleCrop()))
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .into((ImageView) holder.get(R.id.likerAvatar));
        ((TextView) holder.get(R.id.likerName)).setText(mLikers.get(position).getNickname());
        ((TextView) holder.get(R.id.likerDescription)).setText(mLikers.get(position).getDescription());
    }

    @Override
    public int getItemCount() {
        return mLikers.size();
    }
}
