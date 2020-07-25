/**
 * Author:
 * Create Time:
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
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.RecyclerView;

import com.edu.whu.xiaomaivideo.R;
import com.edu.whu.xiaomaivideo.ui.activity.LCFMessageActivity;
import com.edu.whu.xiaomaivideo.util.Constant;
import com.makeramen.roundedimageview.RoundedImageView;

/**
 * Author:李季东，叶俊豪
 * Create Time: 2020/7/15
 * Update Time: 2020/7/23
 * 消息adapter。
 * item分为“@我的”，“评论”，“赞”，“订阅消息”和其他用户私聊。
 */
public class MessageAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context mContext;
    public MessageAdapter(Context context) {
        this.mContext = context;
    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new MessageViewHolder(LayoutInflater.from(mContext).inflate(R.layout.message_item_1, viewGroup,false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {

        if (i==0) {
            ((MessageViewHolder)viewHolder).textView.setText("赞");
            ((MessageViewHolder)viewHolder).image.setImageResource(R.drawable.commend);
            ((MessageViewHolder)viewHolder).icon.setImageResource(R.drawable.ic_action_go);
            Constant.currentLikeMessage.observe((LifecycleOwner) mContext, new Observer<Integer>() {
                @Override
                public void onChanged(Integer integer) {
                    if (integer == 0) {
                        ((MessageViewHolder)viewHolder).indicator.setVisibility(View.INVISIBLE);
                    }
                    else {
                        ((MessageViewHolder)viewHolder).indicator.setVisibility(View.VISIBLE);
                    }
                }
            });
            viewHolder.itemView.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View view)
                {
                    Intent intent = new Intent(mContext, LCFMessageActivity.class);
                    intent.putExtra("type", "like");
                    mContext.startActivity(intent);
                }
            });
        } else if (i==1) {
            ((MessageViewHolder) viewHolder).textView.setText("评论");
            ((MessageViewHolder) viewHolder).image.setImageResource(R.drawable.comment);
            ((MessageViewHolder) viewHolder).icon.setImageResource(R.drawable.ic_action_go);
            Constant.currentCommentMessage.observe((LifecycleOwner) mContext, new Observer<Integer>() {
                @Override
                public void onChanged(Integer integer) {
                    if (integer == 0) {
                        ((MessageViewHolder)viewHolder).indicator.setVisibility(View.INVISIBLE);
                    }
                    else {
                        ((MessageViewHolder)viewHolder).indicator.setVisibility(View.VISIBLE);
                    }
                }
            });
            viewHolder.itemView.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View view)
                {
                    Intent intent = new Intent(mContext, LCFMessageActivity.class);
                    intent.putExtra("type", "comment");
                    mContext.startActivity(intent);
                }
            });
        } else if (i==2){
            ((MessageViewHolder) viewHolder).textView.setText("新粉丝");
            ((MessageViewHolder) viewHolder).image.setImageResource(R.drawable.fans);
            ((MessageViewHolder) viewHolder).icon.setImageResource(R.drawable.ic_action_go);
            Constant.currentFollowMessage.observe((LifecycleOwner) mContext, new Observer<Integer>() {
                @Override
                public void onChanged(Integer integer) {
                    if (integer == 0) {
                        ((MessageViewHolder)viewHolder).indicator.setVisibility(View.INVISIBLE);
                    }
                    else {
                        ((MessageViewHolder)viewHolder).indicator.setVisibility(View.VISIBLE);
                    }
                }
            });
            viewHolder.itemView.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View view)
                {
                    Intent intent = new Intent(mContext, LCFMessageActivity.class);
                    intent.putExtra("type", "follow");
                    mContext.startActivity(intent);
                }
            });
        }
    }

    @Override
    public int getItemCount()
    {return 3; }

    class MessageViewHolder extends RecyclerView.ViewHolder
    {

        private TextView textView;
        private ImageView image;
        private ImageView icon;
        private RoundedImageView indicator;

        public MessageViewHolder(@NonNull View itemView)
        {
            super(itemView);
            textView = itemView.findViewById(R.id.message_item_text);
            image= itemView.findViewById(R.id.message_item_image);
            icon= itemView.findViewById(R.id.message_item_icon);
            indicator = itemView.findViewById(R.id.message_item_indicator);
        }
    }
}