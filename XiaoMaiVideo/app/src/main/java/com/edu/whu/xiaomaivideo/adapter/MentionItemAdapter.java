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
 * Author: 李季东
 * Create Time: 2020/7/15
 * Update Time: 2020/7/15
 * 被@到的具体消息item的adapter
 */
public class MentionItemAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    private Context mContext;
    private OnItemClickListener mListener;
    public MentionItemAdapter(Context context, OnItemClickListener listener)
    {
        this.mContext = context;
        this.mListener = listener;
    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        return new SettingsViewHolder(LayoutInflater.from(mContext).inflate(R.layout.message_item, viewGroup,false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int position) {
        ((SettingsViewHolder)viewHolder).image.setImageResource(R.drawable.child1);
        ((SettingsViewHolder)viewHolder).name.setText("王五");
        ((SettingsViewHolder)viewHolder).content.setText("个个都是人才，说话又好听");
        ((SettingsViewHolder)viewHolder).date.setText("7-15");
    }

    @Override
    public int getItemCount() {
        return 3;
    }
    class SettingsViewHolder extends RecyclerView.ViewHolder
    {

        private ImageView image;
        private TextView name;
        private TextView content;
        private TextView date;

        public SettingsViewHolder(@NonNull View itemView)
        {
            super(itemView);
            image= itemView.findViewById(R.id.mentioned_image);
            name= itemView.findViewById(R.id.mentioned_name);
            content= itemView.findViewById(R.id.mentioned_content);
            date= itemView.findViewById(R.id.mentioned_date);
        }
    }
    public interface OnItemClickListener
    {
        void onClick(int pos);
    }
}
