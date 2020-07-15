package com.edu.whu.xiaomaivideo.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.edu.whu.xiaomaivideo.R;
/**
 * Author:李季东
 * Create Time: 2020/7/15
 * Update Time: 2020/7/15
 * @我的adpter 内部的Item包含一个text和一个recyclerView。
 * 一共两个item，分别用来展示“最新@我的”和“以往@我的”
 */
public class MentionedAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


    private Context mContext;
    private OnItemClickListener mListener;
    private MentionItemAdapter mentionItemAdapter;

    public MentionedAdapter(Context context, OnItemClickListener listener)
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
        if(position==1){
            //最新@我的消息
            ((SettingsViewHolder)viewHolder).newPast.setText("最新");
            ((SettingsViewHolder)viewHolder).recyclerViewMentioned.setAdapter(mentionItemAdapter);
        }else {
            //以往@我的消息
            ((SettingsViewHolder)viewHolder).newPast.setText("以往");
            ((SettingsViewHolder)viewHolder).recyclerViewMentioned.setAdapter(mentionItemAdapter);
        }

    }

    @Override
    public int getItemCount() {
        return 2;
    }
    class SettingsViewHolder extends RecyclerView.ViewHolder
    {

        private TextView newPast;
        private RecyclerView recyclerViewMentioned;

        public SettingsViewHolder(@NonNull View itemView)
        {
            super(itemView);
            newPast = itemView.findViewById(R.id.mentioned_new_past);
            recyclerViewMentioned= itemView.findViewById(R.id.recyclerViewMentioned);
        }
    }
    public interface OnItemClickListener
    {
        void onClick(int pos);
    }
}

