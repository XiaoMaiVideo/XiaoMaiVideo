/**
 * Author: 张俊杰，叶俊豪
 * Create Time: 2020/7/10
 * Update Time: 2020/7/18
 */

package com.edu.whu.xiaomaivideo.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.edu.whu.xiaomaivideo.R;

import java.util.ArrayList;
import java.util.List;

public class UserInfoLabelAdapter extends RecyclerView.Adapter<UserInfoLabelAdapter.UserInfoLabelViewHolder>
{
    private List<String> mLabels;
    private Context mContext;

    public UserInfoLabelAdapter(Context context, List<String> labels) {
        this.mContext = context;
        this.mLabels = labels;
    }

    @NonNull
    @Override
    public UserInfoLabelAdapter.UserInfoLabelViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new UserInfoLabelViewHolder(LayoutInflater.from(mContext).inflate(R.layout.label_item, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull UserInfoLabelViewHolder holder, final int position) {
        holder.textView.setText(mLabels.get(position));
    }

    @Override
    public int getItemCount()
    {
        return mLabels.size();
    }

    class UserInfoLabelViewHolder extends RecyclerView.ViewHolder {
        private TextView textView;

        public UserInfoLabelViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.label_item_text);
        }
    }
}

