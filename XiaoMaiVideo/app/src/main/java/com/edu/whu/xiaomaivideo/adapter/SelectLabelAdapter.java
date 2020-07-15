/**
 * Author: 张俊杰
 * Create Time: 2020/7/14
 * Update Time: 2020/7/14
 */

package com.edu.whu.xiaomaivideo.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.edu.whu.xiaomaivideo.R;
import com.edu.whu.xiaomaivideo.util.Constant;

import java.util.ArrayList;
import java.util.List;

public class SelectLabelAdapter extends RecyclerView.Adapter<SelectLabelAdapter.SelectLabelViewHolder>
{

    private Context mContext;

    public List<String> selectLabels;
    public SelectLabelAdapter(Context context)
    {
        this.mContext = context;
        selectLabels=new ArrayList<>();
    }

    @NonNull
    @Override
    public SelectLabelAdapter.SelectLabelViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i)
    {
        return new SelectLabelViewHolder(LayoutInflater.from(mContext).inflate(R.layout.label_item, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull final SelectLabelViewHolder holder, final int position) {
        holder.textView.setText(Constant.LABELS[position]);
        holder.itemView.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                if (selectLabels.contains(Constant.LABELS[position])){
                    holder.itemView.setBackgroundColor(Color.parseColor("#FFFFFF"));
                    selectLabels.remove(Constant.LABELS[position]);
                }else {
                    holder.itemView.setBackgroundColor(Color.parseColor("#87CEFA"));
                    selectLabels.add(Constant.LABELS[position]);
                }
            }
        });
    }
    @Override
    public int getItemCount()
    {
        return Constant.LABELS.length;
    }

    class SelectLabelViewHolder extends RecyclerView.ViewHolder
    {

        private TextView textView;

        public SelectLabelViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.label_item_text);
        }
    }
}


