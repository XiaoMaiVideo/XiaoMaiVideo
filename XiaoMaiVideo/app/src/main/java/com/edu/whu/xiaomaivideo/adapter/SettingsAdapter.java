/**
 * Author: 张俊杰、叶俊豪
 * Create Time: 2020/7/10
 * Update Time: 2020/7/11
 */

package com.edu.whu.xiaomaivideo.adapter;

import android.content.Context;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.edu.whu.xiaomaivideo.R;

import java.util.List;

public class SettingsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>
{
    private List<Pair<String, Integer>> mItems;
    private Context mContext;
    private OnItemClickListener mListener;

    public SettingsAdapter(Context context, OnItemClickListener listener, List<Pair<String, Integer>> items) {
        this.mContext = context;
        this.mListener = listener;
        this.mItems = items;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i)
    {
        return new SettingsViewHolder(LayoutInflater.from(mContext).inflate(R.layout.setting_item, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, final int i)
    {
        ((SettingsViewHolder)viewHolder).imageView.setImageResource(mItems.get(i).second);
        ((SettingsViewHolder)viewHolder).imageView2.setImageResource(R.drawable.ic_action_go);
        ((SettingsViewHolder)viewHolder).textView.setText(mItems.get(i).first);
        viewHolder.itemView.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                mListener.onClick(i);
            }
        });
    }

    @Override
    public int getItemCount()
    {
        return mItems.size();
    }

    class SettingsViewHolder extends RecyclerView.ViewHolder
    {

        private TextView textView;
        private ImageView imageView;
        private ImageView imageView2;

        public SettingsViewHolder(@NonNull View itemView)
        {
            super(itemView);
            textView = itemView.findViewById(R.id.settingText);
            imageView = itemView.findViewById(R.id.settingImage);
            imageView2=itemView.findViewById(R.id.settingIcon);
        }
    }

    public interface OnItemClickListener
    {
        void onClick(int pos);
    }
}

