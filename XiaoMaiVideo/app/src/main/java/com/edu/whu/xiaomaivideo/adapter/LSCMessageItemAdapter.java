package com.edu.whu.xiaomaivideo.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.RequestOptions;
import com.edu.whu.xiaomaivideo.R;
import com.edu.whu.xiaomaivideo.model.MessageVO;
import com.edu.whu.xiaomaivideo.model.Movie;
import com.edu.whu.xiaomaivideo.model.User;
import com.edu.whu.xiaomaivideo.ui.activity.VideoDetailActivity;
import com.edu.whu.xiaomaivideo.util.TimeUtils;

import org.parceler.Parcels;

import java.util.List;

/**
 * Author: 李季东，张俊杰，叶俊豪
 * Create Time: 2020/7/15
 * Update Time: 2020/7/18
 * 被@到的具体消息item的adapter
 */
public class LSCMessageItemAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private Context mContext;
    private List<MessageVO> mMessages;
    List<User> mUsers;
    String mType;
    public LSCMessageItemAdapter(Context context, List<MessageVO> messages, List<User> users, String type)
    {
        this.mContext = context;
        this.mMessages = messages;
        this.mUsers = users;
        this.mType = type;
    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        return new LCFMessageViewHolder(LayoutInflater.from(mContext).inflate(R.layout.activity_lcf_message_item, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int position) {
        LCFMessageViewHolder messageViewHolder = (LCFMessageViewHolder)viewHolder;
        // 只需要知道用户昵称和头像就可以了
        Glide.with(mContext).load(mUsers.get(position).getAvatar()).apply(RequestOptions.bitmapTransform(new CircleCrop())).into(messageViewHolder.image);
        ((LCFMessageViewHolder)viewHolder).name.setText(mUsers.get(position).getNickname());
        if (mType.equals("like")) {
            ((LCFMessageViewHolder) viewHolder).content.setText("赞了你的视频，快去看看 >>");
        }
        else if (mType.equals("comment")) {
            ((LCFMessageViewHolder) viewHolder).content.setText("评论了你的视频，快去看看 >>");
        }
        else if (mType.equals("follow")) {
            ((LCFMessageViewHolder) viewHolder).content.setText("新关注了你，快去看看 >>");
        }

        if (!mType.equals("follow")) {
            ((LCFMessageViewHolder) viewHolder).date.setText(TimeUtils.getRecentTimeSpanByNow(System.currentTimeMillis()));
        }
        else {
            ((LCFMessageViewHolder) viewHolder).date.setText("");
        }
    }

    @Override
    public int getItemCount() {
        return mMessages.size();
    }
    class LCFMessageViewHolder extends RecyclerView.ViewHolder {
        private ImageView image;
        private TextView name;
        private TextView content;
        private TextView date;

        public LCFMessageViewHolder(@NonNull View itemView)
        {
            super(itemView);
            image = itemView.findViewById(R.id.mentioned_image);
            name = itemView.findViewById(R.id.mentioned_name);
            content = itemView.findViewById(R.id.mentioned_content);
            date = itemView.findViewById(R.id.mentioned_date);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (mType.equals("follow")) {
                        // 跳转关注列表
                        return;
                    }
                    // 跳转到视频详情页
                    Movie movie = new Movie();
                    movie.setMovieId(mMessages.get(getAdapterPosition()).getMovieId());
                    Intent intent = new Intent(mContext, VideoDetailActivity.class);
                    intent.putExtra("movie", Parcels.wrap(Movie.class, movie));
                    mContext.startActivity(intent);
                }
            });
        }
    }
    public interface OnItemClickListener
    {
        void onClick(int pos);
    }
}
