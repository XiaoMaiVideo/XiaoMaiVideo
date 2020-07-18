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
import com.edu.whu.xiaomaivideo.ui.activity.MessageChatActivity;
import com.edu.whu.xiaomaivideo.ui.activity.LSFMessageActivity;

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
            return new MessageViewHolder(LayoutInflater.from(mContext).inflate(R.layout.message_item_1, viewGroup,false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {

        if (i==0) {
            ((MessageViewHolder)viewHolder).textView.setText("赞");
            ((MessageViewHolder)viewHolder).image.setImageResource(R.drawable.like);
            ((MessageViewHolder)viewHolder).icon.setImageResource(R.drawable.ic_action_go);
            viewHolder.itemView.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View view)
                {
                    Intent intent = new Intent(mContext, LSFMessageActivity.class);
                    intent.putExtra("type", "like");
                    mContext.startActivity(intent);
                }
            });
        } else if (i==1) {
            ((MessageViewHolder) viewHolder).textView.setText("评论");
            ((MessageViewHolder) viewHolder).image.setImageResource(R.drawable.message);
            ((MessageViewHolder) viewHolder).icon.setImageResource(R.drawable.ic_action_go);
            viewHolder.itemView.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View view)
                {
                    Intent intent = new Intent(mContext, LSFMessageActivity.class);
                    intent.putExtra("type", "comment");
                    mContext.startActivity(intent);
                }
            });
        } else if (i==2){
            ((MessageViewHolder) viewHolder).textView.setText("粉丝");
            ((MessageViewHolder) viewHolder).image.setImageResource(R.drawable.like);
            ((MessageViewHolder) viewHolder).icon.setImageResource(R.drawable.ic_action_go);
            viewHolder.itemView.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View view)
                {
                    Intent intent = new Intent(mContext, LSFMessageActivity.class);
                    intent.putExtra("type", "follow");
                    mContext.startActivity(intent);
                }
            });
        }
        else {
            ((MessageViewHolder) viewHolder).textView.setText("雷军");
            ((MessageViewHolder) viewHolder).image.setImageResource(R.drawable.ic_launcher_background);
            ((MessageViewHolder) viewHolder).icon.setImageResource(R.drawable.ic_action_go);
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
    {return 4; }
    //@我的
    class MessageViewHolder extends RecyclerView.ViewHolder
    {

        private TextView textView;
        private ImageView image;
        private ImageView icon;

        public MessageViewHolder(@NonNull View itemView)
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
