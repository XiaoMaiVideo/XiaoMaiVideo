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

import java.util.List;

public class EditUserInfoAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>
{

    private Context mContext;
    private OnItemClickListener mListener;
    private String[] infos= new String[]{"昵称", "性别","头像","个人简介"};

    public EditUserInfoAdapter(Context context, OnItemClickListener listener)
    {
        this.mContext = context;
        this.mListener = listener;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i)
    {
        return new EditUserInfoViewHolder(LayoutInflater.from(mContext).inflate(R.layout.edit_user_info_item, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, final int i)
    {
        ((EditUserInfoViewHolder)viewHolder).editInfo.setText(infos[i]);
        ((EditUserInfoViewHolder)viewHolder).num.setText(String.valueOf(i+1));

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
        return infos.length;
    }

    class EditUserInfoViewHolder extends RecyclerView.ViewHolder
    {

        private TextView editInfo;
        private TextView num;

        public EditUserInfoViewHolder(@NonNull View itemView)
        {
            super(itemView);
            editInfo = itemView.findViewById(R.id.edit_info_textview);
            num = itemView.findViewById(R.id.edit_info_num);
        }
    }

    public interface OnItemClickListener
    {
        void onClick(int pos);
    }
}