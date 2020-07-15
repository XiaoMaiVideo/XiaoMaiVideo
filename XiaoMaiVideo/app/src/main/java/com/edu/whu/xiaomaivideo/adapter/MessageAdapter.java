package com.edu.whu.xiaomaivideo.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.edu.whu.xiaomaivideo.R;

/**
 * Author:李季东
 * Create Time: 2020/7/15
 * Update Time: 2020/7/15
 * 消息adapter
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
        }else if (i==1){
            ((SettingsViewHolder) viewHolder).textView.setText("评论");
            ((SettingsViewHolder) viewHolder).image.setImageResource(R.drawable.message);
            ((SettingsViewHolder) viewHolder).icon.setImageResource(R.drawable.ic_action_go);
        }else if (i==2){
            ((SettingsViewHolder) viewHolder).textView.setText("赞");
            ((SettingsViewHolder) viewHolder).image.setImageResource(R.drawable.like);
            ((SettingsViewHolder) viewHolder).icon.setImageResource(R.drawable.ic_action_go);
        }else{
            ((SettingsViewHolder) viewHolder).textView.setText("订阅消息");
            ((SettingsViewHolder) viewHolder).image.setImageResource(R.drawable.star);
            ((SettingsViewHolder) viewHolder).icon.setImageResource(R.drawable.ic_action_go);
        }

    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public int getItemCount()
    {return 4; }
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
