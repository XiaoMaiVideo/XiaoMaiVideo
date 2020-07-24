/**
 * Author: 张俊杰
 * Create Time: 2020/7/20
 * Update Time: 2020/7/22
 */

package com.edu.whu.xiaomaivideo.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.RequestOptions;
import com.edu.whu.xiaomaivideo.R;
import com.edu.whu.xiaomaivideo.model.Message;
import com.edu.whu.xiaomaivideo.model.MessageVO;
import com.edu.whu.xiaomaivideo.model.Movie;
import com.edu.whu.xiaomaivideo.model.User;
import com.edu.whu.xiaomaivideo.ui.activity.UserInfoActivity;
import com.edu.whu.xiaomaivideo.ui.activity.VideoDetailActivity;
import com.edu.whu.xiaomaivideo.util.CommonUtil;
import com.edu.whu.xiaomaivideo.util.Constant;
import com.edu.whu.xiaomaivideo.util.TimeUtil;

import org.litepal.crud.callback.UpdateOrDeleteCallback;
import org.parceler.Parcels;

import java.util.ArrayList;
import java.util.List;

public class ChatAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context mContext;
    private List<Message> msgs;

    public ChatAdapter(Context context, List<Message> msgs) {
        this.mContext = context;
        this.msgs = msgs;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == 0) {
            Log.e("viewType", String.valueOf(viewType));
            return new ChatAdapterViewHolder(LayoutInflater.from(mContext).inflate(R.layout.my_msg, parent, false));
        } else {
            return new ChatAdapterViewHolder(LayoutInflater.from(mContext).inflate(R.layout.others_msg, parent, false));
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ((ChatAdapterViewHolder)holder).msgText.setText(msgs.get(position).getText());
        ((ChatAdapterViewHolder)holder).dateText.setText(CommonUtil.convertTimeToDateString(msgs.get(position).getTime().getTime()));
        //自己发的
        if (msgs.get(position).getSender().getUserId() == Constant.currentUser.getUserId()) {
            Glide.with(mContext)
                    .load(Constant.currentUser.getAvatar())
                    .apply(RequestOptions.bitmapTransform(new CircleCrop()))
                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                    .into(((ChatAdapterViewHolder) holder).avatar);

        } else {
            //别人发的
            Glide.with(mContext)
                    .load(msgs.get(position).getSender().getAvatar())
                    .apply(RequestOptions.bitmapTransform(new CircleCrop()))
                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                    .into(((ChatAdapterViewHolder) holder).avatar);
            ((ChatAdapterViewHolder) holder).avatar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(mContext, UserInfoActivity.class);
                    intent.putExtra("user", Parcels.wrap(User.class, msgs.get(position).getSender()));
                    mContext.startActivity(intent);
                }
            });
        }

        if (msgs.get(position).getMsgType().equals("msg")) {
            ((ChatAdapterViewHolder)holder).movieCard.setVisibility(View.GONE);
        } else if (msgs.get(position).getMsgType().equals("at")) {
            // 放个图标好了，缩略图太费资源了
            ((ChatAdapterViewHolder)holder).movieImg.setImageResource(R.drawable.movie);
            // 描述
            if (msgs.get(position).getAtMovie().getDescription() == null || msgs.get(position).getAtMovie().getDescription().equals("")) {
                ((ChatAdapterViewHolder) holder).movieDescription.setText(msgs.get(position).getSender().getNickname()+" 发布的视频");
            }
            else {
                ((ChatAdapterViewHolder) holder).movieDescription.setText(msgs.get(position).getAtMovie().getDescription());
            }
            ((ChatAdapterViewHolder)holder).movieCard.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // 跳转到详情页面
                    Intent intent = new Intent(mContext, VideoDetailActivity.class);
                    intent.putExtra("movie", Parcels.wrap(Movie.class, msgs.get(position).getAtMovie()));
                    mContext.startActivity(intent);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return msgs.size();
    }

    @Override
    public int getItemViewType(int position) {
        // 自己发的是0，别人发的是1
        return msgs.get(position).getSender().getUserId() == Constant.currentUser.getUserId()? 0: 1;
    }

    class ChatAdapterViewHolder extends RecyclerView.ViewHolder
    {
        private TextView msgText;
        private CardView movieCard;
        private ImageView avatar;
        private ImageView movieImg;
        private TextView movieDescription;
        private TextView dateText;

        public ChatAdapterViewHolder(@NonNull View itemView) {
            super(itemView);
            msgText = itemView.findViewById(R.id.msg_text);
            movieCard =itemView.findViewById(R.id.movie_card);
            avatar =itemView.findViewById(R.id.avatar);
            movieImg = itemView.findViewById(R.id.movie_img);
            movieDescription = itemView.findViewById(R.id.movie_description);
            dateText=itemView.findViewById(R.id.date_text);
        }
    }
}
