/**
 * Author: 张俊杰，叶俊豪
 * Create Time: 2020/7/22
 * Update Time: 2020/7/25
 */

package com.edu.whu.xiaomaivideo.adapter;

import android.content.Context;
import android.content.Intent;
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
import com.edu.whu.xiaomaivideo.ui.activity.ChatActivity;
import com.edu.whu.xiaomaivideo.ui.activity.LCFMessageActivity;
import com.edu.whu.xiaomaivideo.util.Constant;
import com.edu.whu.xiaomaivideo.util.TimeUtil;
import com.edu.whu.xiaomaivideo.viewModel.MessageViewModel;

import java.util.List;

public class MsgAdapter extends RecyclerView.Adapter<MsgAdapter.MsgViewHolder> {
    List<MessageViewModel.ShowMsg> showMsgs;
    Context mContext;

    public MsgAdapter(Context mContext, List<MessageViewModel.ShowMsg> showMsgs) {
        this.mContext = mContext;
        this.showMsgs = showMsgs;
    }

    public void setShowMsgs(List<MessageViewModel.ShowMsg> showMsgs) {
        this.showMsgs = showMsgs;
    }

    @NonNull
    @Override
    public MsgAdapter.MsgViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MsgViewHolder(LayoutInflater.from(mContext).inflate(R.layout.msg_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MsgAdapter.MsgViewHolder holder, int position) {
        ((MsgViewHolder)holder).username.setText(showMsgs.get(position).getUsername());
        ((MsgViewHolder)holder).msg.setText(showMsgs.get(position).getMsg());
        ((MsgViewHolder)holder).date.setText(TimeUtil.getRecentTimeSpanByNow(showMsgs.get(position).getDate().getTime()));
        Glide.with(mContext)
                .load(showMsgs.get(position).getAvatar())
                .apply(RequestOptions.bitmapTransform(new CircleCrop()))
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .into((((MsgViewHolder)holder).avatar));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MessageViewModel.ShowMsg currentMsg = showMsgs.get(position);
                // 进入聊天页面
                Constant.currentChattingId = currentMsg.getUserId();
                Constant.currentChattingName = currentMsg.getUsername();
                Intent intent = new Intent(mContext, ChatActivity.class);
                mContext.startActivity(intent);
            }
        });
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
