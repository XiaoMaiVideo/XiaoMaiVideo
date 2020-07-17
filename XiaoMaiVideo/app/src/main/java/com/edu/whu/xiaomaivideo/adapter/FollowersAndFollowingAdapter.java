/**
 * Author: 张俊杰
 * Create Time: 2020/7/17
 * Update Time: 2020/7/17
 */

package com.edu.whu.xiaomaivideo.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.fastjson.JSON;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.RequestOptions;
import com.edu.whu.xiaomaivideo.R;
import com.edu.whu.xiaomaivideo.model.MessageVO;
import com.edu.whu.xiaomaivideo.model.User;
import com.edu.whu.xiaomaivideo.util.Constant;
import com.edu.whu.xiaomaivideo.util.EventBusMessage;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

public class FollowersAndFollowingAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context mContext;
    private boolean isFollow;
    private FollowersAndFollowingAdapter.OnItemClickListener mListener;
    private List<User> users;

    public void setUsers(List<User> users) {
        this.users = users;
    }

    public FollowersAndFollowingAdapter(Context context, FollowersAndFollowingAdapter.OnItemClickListener listener, boolean isFollow)
    {
        this.mContext = context;
        this.mListener = listener;
        this.isFollow=isFollow;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new FollowersAndFollowingViewHolder(LayoutInflater.from(mContext).inflate(R.layout.follow_following_item, parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ImageView imageView= ((FollowersAndFollowingViewHolder)holder).avatar;
        Glide.with(mContext).load(Constant.CurrentUser.getAvatar()).apply(RequestOptions.bitmapTransform(new CircleCrop())).into(imageView);
        //((FollowersAndFollowingViewHolder)holder).username.setText(users.get(position).getUsername());
        ((FollowersAndFollowingViewHolder)holder).username.setText("测试");
        ((FollowersAndFollowingViewHolder)holder).description.setText("ceshi");
        if (isFollow){
            ((FollowersAndFollowingViewHolder)holder).button.setText("回关");
            ((FollowersAndFollowingViewHolder)holder).button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    MessageVO message = new MessageVO();
//                    message.setMsgType("follow");
//                    message.setSenderId(Constant.CurrentUser.getUserId());
//                    message.setReceiverId(users.get(position).getUserId());
//                    message.setText("用户"+Constant.CurrentUser.getUsername()+"关注了你");
//                    EventBus.getDefault().post(new EventBusMessage(Constant.SEND_MESSAGE, JSON.toJSONString(message)));
                }
            });
        }else {
            ((FollowersAndFollowingViewHolder)holder).button.setText("取关");
            ((FollowersAndFollowingViewHolder)holder).button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    MessageVO message = new MessageVO();
//                    message.setMsgType("unfollow");
//                    message.setSenderId(Constant.CurrentUser.getUserId());
//                    message.setReceiverId(users.get(position).getUserId());
//                    message.setText("用户"+Constant.CurrentUser.getUsername()+"取消关注了你");
//                    EventBus.getDefault().post(new EventBusMessage(Constant.SEND_MESSAGE, JSON.toJSONString(message)));
//                    List<User> following=Constant.CurrentUser.getFollowing();
//                    following.remove(position);
//                    Constant.CurrentUser.setFollowing(following);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        //return users.size();
        return 20;
    }

    class FollowersAndFollowingViewHolder extends RecyclerView.ViewHolder
    {

        private ImageView avatar;
        private TextView username;
        private TextView description;
        private Button button;

        public FollowersAndFollowingViewHolder(@NonNull View itemView)
        {
            super(itemView);
            avatar = itemView.findViewById(R.id.imageView5);
            username= itemView.findViewById(R.id.textView10);
            description= itemView.findViewById(R.id.textView11);
            button=itemView.findViewById(R.id.button4);
        }
    }

    public interface OnItemClickListener
    {
        void onClick(int pos);
    }
}
