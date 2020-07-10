package com.edu.whu.xiaomaivideo.adapter;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.edu.whu.xiaomaivideo.R;

public class SettingsFriendAdpater extends RecyclerView.Adapter<RecyclerView.ViewHolder>
{
    private Context mContext;
    private OnItemClickListener mListener;

    public  SettingsFriendAdpater(Context context, OnItemClickListener listener)
    {
        this.mContext = context;
        this.mListener = listener;
    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new SettingsViewHolder(LayoutInflater.from(mContext).inflate(R.layout.friendvideo_item, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, final int i) {

        //        if (getItemViewType(i) == 0)
        ((SettingsViewHolder)viewHolder).imageView.setImageResource(R.drawable.ic_launcher_background);
        ((SettingsViewHolder)viewHolder).textView.setText("张三");
        ((SettingsViewHolder)viewHolder).videoView.setVideoURI(Uri.parse("https://v-cdn.zjol.com.cn/280443.mp4"));

        ((SettingsViewHolder)viewHolder).videoView.start();

        viewHolder.itemView.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                mListener.onClick(i);
//                Toast.makeText(mContext, "click..." + i, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return 10;
    }
    class SettingsViewHolder extends RecyclerView.ViewHolder
    {

        private TextView textView;
        private ImageView imageView;
        private VideoView videoView;
        private Button like;
        private Button comment;
        private Button forward;

        public SettingsViewHolder(@NonNull View itemView)
        {
            super(itemView);
            textView = itemView.findViewById(R.id.friendText);
            imageView = itemView.findViewById(R.id.firendImage);
            videoView=itemView.findViewById(R.id.friendVideo);
            like = itemView.findViewById(R.id.friendLike);
            comment = itemView.findViewById(R.id.friendComment);
            forward = itemView.findViewById((R.id.friendForward));
        }
    }
    public interface OnItemClickListener
    {
        void onClick(int pos);
    }
}
