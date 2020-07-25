/**
 * Author: 叶俊豪、付浩
 * Create Time: 2020/7/10
 * Update Time: 2020/7/22
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

public class UserVideoWorksAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context mContext;
    private SettingsAdapter.OnItemClickListener mListener;

    public UserVideoWorksAdapter(Context context, SettingsAdapter.OnItemClickListener listener) {
        this.mContext = context;
        this.mListener = listener;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i)
    {
        return new UserVideoWorksAdapter.UserVideoWorksViewHolder(LayoutInflater.from(mContext).inflate(R.layout.setting_item, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, final int i)
    {
        ((UserVideoWorksAdapter.UserVideoWorksViewHolder)viewHolder).imageView.setImageResource(R.drawable.ic_action_lock);
        ((UserVideoWorksAdapter.UserVideoWorksViewHolder)viewHolder).imageView2.setImageResource(R.drawable.ic_action_go);
        ((UserVideoWorksAdapter.UserVideoWorksViewHolder)viewHolder).textView.setText("黑夜给了我黑色的眼睛...");
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

    class UserVideoWorksViewHolder extends RecyclerView.ViewHolder
    {

        private TextView textView;
        private ImageView imageView;
        private ImageView imageView2;

        public UserVideoWorksViewHolder(@NonNull View itemView)
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
