package com.edu.whu.xiaomaivideo.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.edu.whu.xiaomaivideo.R;

public class UserInfoLabelAdapter extends RecyclerView.Adapter<UserInfoLabelAdapter.UserInfoLabelViewHolder>
{

    private Context mContext;
    private OnItemClickListener mListener;

    public UserInfoLabelAdapter(Context context, OnItemClickListener listener)
    {
        this.mContext = context;
        this.mListener = listener;
    }

    @NonNull
    @Override
    public UserInfoLabelAdapter.UserInfoLabelViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i)
    {
        return new UserInfoLabelViewHolder(LayoutInflater.from(mContext).inflate(R.layout.user_info_label_item, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull UserInfoLabelViewHolder holder, final int position) {
        holder.textView.setText("Hello, world!");
        holder.itemView.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                mListener.onClick(position);
            }
        });
    }

    @Override
    public int getItemCount()
    {
        return 20;
    }

    class UserInfoLabelViewHolder extends RecyclerView.ViewHolder
    {

        private TextView textView;

        public UserInfoLabelViewHolder(@NonNull View itemView)
        {
            super(itemView);
            textView = itemView.findViewById(R.id.user_info_label_item_text);
        }
    }

    public interface OnItemClickListener
    {
        void onClick(int pos);
    }
}

