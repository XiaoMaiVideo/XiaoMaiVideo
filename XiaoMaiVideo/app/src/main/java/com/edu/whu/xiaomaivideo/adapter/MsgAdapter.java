/**
 * Author: 张俊杰
 * Create Time: 2020/7/22
 * Update Time: 2020/7/23
 */

package com.edu.whu.xiaomaivideo.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.RequestOptions;
import com.edu.whu.xiaomaivideo.R;
import com.edu.whu.xiaomaivideo.model.Message;
import com.edu.whu.xiaomaivideo.viewModel.MessageViewModel;

import java.util.List;
import java.util.Map;

public class MsgAdapter  extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    List<MessageViewModel.ShowMsg> showMsgs;
    Context mContext;

    public MsgAdapter(Context mContext) {
        this.mContext=mContext;
    }

    public void setShowMsgs(List<MessageViewModel.ShowMsg> showMsgs) {
        this.showMsgs = showMsgs;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MsgViewHolder(LayoutInflater.from(mContext).inflate(R.layout.msg_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ((MsgViewHolder)holder).username.setText(showMsgs.get(position).getUsername());
        ((MsgViewHolder)holder).msg.setText(showMsgs.get(position).getMsg());
        ((MsgViewHolder)holder).date.setText(showMsgs.get(position).getDate().toString());
        Glide.with(mContext)
                .load(showMsgs.get(position).getAvatar())
                .apply(RequestOptions.bitmapTransform(new CircleCrop()))
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .into((((MsgViewHolder)holder).avatar));
    }

    @Override
    public int getItemCount() {
        return showMsgs.size();
    }

    class MsgViewHolder extends RecyclerView.ViewHolder
    {

        private TextView username;
        private TextView msg;
        private TextView date;
        private ImageView avatar;


        public MsgViewHolder(@NonNull View itemView) {
            super(itemView);
            username = itemView.findViewById(R.id.username);
            msg= itemView.findViewById(R.id.msg);
            date= itemView.findViewById(R.id.date);
            avatar = itemView.findViewById(R.id.avatar);
        }
    }
}
