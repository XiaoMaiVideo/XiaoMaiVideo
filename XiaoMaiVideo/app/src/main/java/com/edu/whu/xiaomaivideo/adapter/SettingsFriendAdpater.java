/**
 * Author: 李季东、张俊杰
 * Create Time: 2020/7/10
 * Update Time: 2020/7/14
 */

package com.edu.whu.xiaomaivideo.adapter;

import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.edu.whu.xiaomaivideo.R;
import com.edu.whu.xiaomaivideo.ui.activity.VideoDetialActivity;

/**
 * Author: 李季东
 * Create Time: 2020/7/14
 * Update Time: 2020/7/15
 * 视频详情页面
 */
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
        MediaController mediaController = new MediaController(mContext);
        ((SettingsViewHolder)viewHolder).videoView.setMediaController(mediaController);
        ((SettingsViewHolder)viewHolder).videoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                Toast.makeText(mContext, "视频播放完毕", Toast.LENGTH_SHORT).show();
            }
        });

        viewHolder.itemView.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                mListener.onClick(i);
                Toast.makeText(mContext, "click..." + i, Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(mContext, VideoDetialActivity.class);
                //数据绑定
                Bundle bundle = new Bundle();
                bundle.putString("videoUrl","https://v-cdn.zjol.com.cn/280443.mp4");
                bundle.putString("username","张三");
                bundle.putInt("userImg",R.drawable.ic_launcher_background);
                intent.putExtras(bundle);
                mContext.startActivity(intent);
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
            imageView = itemView.findViewById(R.id.friendImage);
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
