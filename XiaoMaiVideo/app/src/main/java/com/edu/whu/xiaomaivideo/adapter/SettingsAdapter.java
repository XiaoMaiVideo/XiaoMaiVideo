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

public class SettingsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>
{

    private Context mContext;
    private OnItemClickListener mListener;

    public SettingsAdapter(Context context, OnItemClickListener listener)
    {
        this.mContext = context;
        this.mListener = listener;
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
        ((SettingsViewHolder)viewHolder).imageView.setImageResource(R.drawable.ic_action_lock);
        ((SettingsViewHolder)viewHolder).imageView2.setImageResource(R.drawable.ic_action_go);
        ((SettingsViewHolder)viewHolder).textView.setText("黑夜给了我黑色的眼睛。。。");
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
        return 20;
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

