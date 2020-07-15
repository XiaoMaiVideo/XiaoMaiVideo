package com.edu.whu.xiaomaivideo.adapter;

import android.content.Context;
import android.graphics.Rect;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.edu.whu.xiaomaivideo.R;

/**
 * Author: 李季东
 * Create Time: 2020/7/14
 * Update Time: 2020/7/15
 * 视频详情评论的adapter
 */
public class CommentAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context mContext;
    private OnItemClickListener mListener;

    public CommentAdapter(Context context, OnItemClickListener listener)
    {
        this.mContext = context;
        this.mListener = listener;
    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new SettingsViewHolder(LayoutInflater.from(mContext).inflate(R.layout.comment_item, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, final int i) {
        ((SettingsViewHolder)viewHolder).imageView.setImageResource(R.drawable.ic_launcher_background);
        ((SettingsViewHolder)viewHolder).name.setText("李四");
        ((SettingsViewHolder)viewHolder).date.setText("07-10 16:37");
        ((SettingsViewHolder)viewHolder).content.setText("江南皮革厂倒闭了");
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onClick(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return 10;
    }
    class SettingsViewHolder extends RecyclerView.ViewHolder
    {

        private TextView name;
        private TextView date;
        private TextView content;
        private ImageView imageView;

        public SettingsViewHolder(@NonNull View itemView)
        {
            super(itemView);
            name = itemView.findViewById(R.id.comment_user_name);
            date =itemView.findViewById(R.id.comment_date);
            content =itemView.findViewById(R.id.comment_content);
            imageView = itemView.findViewById(R.id.comment_user_image);
        }
    }
    public interface OnItemClickListener
    {
        void onClick(int pos);
    }
}
