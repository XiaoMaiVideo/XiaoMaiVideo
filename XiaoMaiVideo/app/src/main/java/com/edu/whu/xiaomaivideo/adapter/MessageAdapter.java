/**
 * Author: 张俊杰
 * Create Time: 2020/7/20
 * Update Time: 2020/7/22
 */

package com.edu.whu.xiaomaivideo.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.edu.whu.xiaomaivideo.R;
import com.edu.whu.xiaomaivideo.ui.activity.ChatActivity;
import com.edu.whu.xiaomaivideo.ui.activity.MessageChatActivity;
import com.edu.whu.xiaomaivideo.ui.activity.MessageMentionedActivity;
import com.edu.whu.xiaomaivideo.ui.activity.MessageCommentActivity;
import com.edu.whu.xiaomaivideo.ui.activity.MessageLikeActivity;
import com.edu.whu.xiaomaivideo.ui.activity.MessageSubscribeActivity;
import com.edu.whu.xiaomaivideo.viewModel.ChatViewModel;

/**
 * Author:李季东
 * Create Time: 2020/7/15
 * Update Time: 2020/7/15
 * 消息adapter。
 * item分为“@我的”，“评论”，“赞”，“订阅消息”和其他用户私聊。
 */
public class MessageAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context mContext;
    private OnItemClickListener mListener;
    public MessageAdapter(Context context, OnItemClickListener listener)
    {
        this.mContext = context;
        this.mListener = listener;
    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            return new SettingsViewHolder(LayoutInflater.from(mContext).inflate(R.layout.message_item, viewGroup,false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {

        if (i==0){
            ((SettingsViewHolder)viewHolder).textView.setText("@我的");
            ((SettingsViewHolder)viewHolder).image.setImageResource(R.drawable.me);
            ((SettingsViewHolder)viewHolder).icon.setImageResource(R.drawable.ic_action_go);
            viewHolder.itemView.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View view)
                {
                    mListener.onClick(i);
                    Toast.makeText(mContext, "click..." + i, Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(mContext, ChatActivity.class);
                    mContext.startActivity(intent);
                }
            });
        }else if (i==1){
            ((SettingsViewHolder) viewHolder).textView.setText("评论");
            ((SettingsViewHolder) viewHolder).image.setImageResource(R.drawable.message);
            ((SettingsViewHolder) viewHolder).icon.setImageResource(R.drawable.ic_action_go);
            viewHolder.itemView.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View view)
                {
                    mListener.onClick(i);
                    Toast.makeText(mContext, "click..." + i, Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(mContext, MessageCommentActivity.class);
                    mContext.startActivity(intent);
                }
            });
        }else if (i==2){
            ((SettingsViewHolder) viewHolder).textView.setText("赞");
            ((SettingsViewHolder) viewHolder).image.setImageResource(R.drawable.like);
            ((SettingsViewHolder) viewHolder).icon.setImageResource(R.drawable.ic_action_go);
            viewHolder.itemView.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View view)
                {
                    mListener.onClick(i);
                    Toast.makeText(mContext, "click..." + i, Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(mContext, MessageLikeActivity.class);
                    mContext.startActivity(intent);
                }
            });
        }else if (i==3){
            ((SettingsViewHolder) viewHolder).textView.setText("订阅消息");
            ((SettingsViewHolder) viewHolder).image.setImageResource(R.drawable.star);
            ((SettingsViewHolder) viewHolder).icon.setImageResource(R.drawable.ic_action_go);
            viewHolder.itemView.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View view)
                {
                    mListener.onClick(i);
                    Toast.makeText(mContext, "click..." + i, Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(mContext, MessageSubscribeActivity.class);
                    mContext.startActivity(intent);
                }
            });
        }
        else {
            ((SettingsViewHolder) viewHolder).textView.setText("雷军");
            ((SettingsViewHolder) viewHolder).image.setImageResource(R.drawable.ic_launcher_background);
            ((SettingsViewHolder) viewHolder).icon.setImageResource(R.drawable.ic_action_go);
            viewHolder.itemView.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View view)
                {
                    mListener.onClick(i);
                    Toast.makeText(mContext, "click..." + i, Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(mContext, MessageChatActivity.class);
                    mContext.startActivity(intent);
                }
            });
        }
    }

    @Override
    public int getItemCount()
    {return 5; }
    //@我的
    class SettingsViewHolder extends RecyclerView.ViewHolder
    {

        private TextView textView;
        private ImageView image;
        private ImageView icon;

        public SettingsViewHolder(@NonNull View itemView)
        {
            super(itemView);
            textView = itemView.findViewById(R.id.message_item_text);
            image= itemView.findViewById(R.id.message_item_image);
            icon= itemView.findViewById(R.id.message_item_icon);
        }
    }
    public interface OnItemClickListener
    {
        void onClick(int pos);
    }
}
