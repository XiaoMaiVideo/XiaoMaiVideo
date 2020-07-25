/**
 * Author: 张俊杰、李季东、叶俊豪
 * Create Time: 2020/7/17
 * Update Time: 2020/7/23
 */

package com.edu.whu.xiaomaivideo.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.RequestOptions;
import com.edu.whu.xiaomaivideo.R;
import com.edu.whu.xiaomaivideo.model.User;
import com.edu.whu.xiaomaivideo.ui.activity.UserInfoActivity;
import com.edu.whu.xiaomaivideo.util.Constant;

import org.parceler.Parcels;

import java.util.List;

public class UserAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context mContext;
    private boolean isFollow;
    private List<User> users;

    public void setUsers(List<User> users) {
        this.users = users;
    }

    public UserAdapter(Context context, boolean isFollow)
    {
        this.mContext = context;
        this.isFollow=isFollow;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new UserViewHolder(LayoutInflater.from(mContext).inflate(R.layout.follow_following_item, parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        UserViewHolder viewHolder = ((UserViewHolder)holder);
        Glide.with(mContext).load(users.get(position).getAvatar()).apply(RequestOptions.bitmapTransform(new CircleCrop())).into(viewHolder.avatar);
        viewHolder.username.setText(users.get(position).getNickname());
        if (users.get(position).getDescription() != null && !users.get(position).getDescription().equals("")) {
            viewHolder.description.setText(users.get(position).getDescription());
        }
        else {
            viewHolder.description.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return users.size();
    }

    class UserViewHolder extends RecyclerView.ViewHolder {
        private ImageView avatar;
        private TextView username;
        private TextView description;
        private Button button;

        public UserViewHolder(@NonNull View itemView)
        {
            super(itemView);
            avatar = itemView.findViewById(R.id.imageView5);
            username= itemView.findViewById(R.id.textView10);
            description= itemView.findViewById(R.id.textView11);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(mContext, UserInfoActivity.class);
                    intent.putExtra("user", Parcels.wrap(User.class, users.get(getAdapterPosition())));
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
