/**
 * Author: 叶俊豪
 * Create Time: 2020/7/24
 * Update Time: 2020/7/25
 */
package com.edu.whu.xiaomaivideo.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.RequestOptions;
import com.donkingliang.groupedadapter.adapter.GroupedRecyclerViewAdapter;
import com.donkingliang.groupedadapter.holder.BaseViewHolder;
import com.edu.whu.xiaomaivideo.R;
import com.edu.whu.xiaomaivideo.model.User;
import com.edu.whu.xiaomaivideo.util.Constant;

import java.util.HashMap;
import java.util.Map;

public class AtDialogAdapter extends GroupedRecyclerViewAdapter {
    Context mContext;
    public Map<Long, User> checkedUsers;
    public boolean isFollowingExpand = false, isFollowersExpand = false;
    public AtDialogAdapter(Context context) {
        super(context);
        mContext = context;
        checkedUsers = new HashMap<>();
    }

    @Override
    public int getGroupCount() {
        return 2;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        if (groupPosition == 0) {
            // 关注
            return isFollowingExpand? Constant.currentUser.getFollowing().size(): 0;
        }
        else {
            // 粉丝
            return isFollowersExpand? Constant.currentUser.getFollowers().size(): 0;
        }
    }

    @Override
    public boolean hasHeader(int groupPosition) {
        return true;
    }

    @Override
    public boolean hasFooter(int groupPosition) {
        return false;
    }

    @Override
    public int getHeaderLayout(int viewType) {
        return R.layout.activity_lcf_message_header;
    }

    @Override
    public int getFooterLayout(int viewType) {
        return 0;
    }

    @Override
    public int getChildLayout(int viewType) {
        return R.layout.at_item;
    }

    @Override
    public void onBindHeaderViewHolder(BaseViewHolder holder, int groupPosition) {
        TextView textView = holder.get(R.id.mentioned_new_past);
        if (groupPosition == 0) {
            textView.setText("关注");
        }
        else {
            textView.setText("粉丝");
        }
    }

    @Override
    public void onBindFooterViewHolder(BaseViewHolder holder, int groupPosition) {

    }

    public void expandGroup(int groupPosition, boolean animate) {
        if (groupPosition == 0) {
            isFollowingExpand = true;
        }
        else {
            isFollowersExpand = true;
        }
        if (animate) {
            notifyChildrenInserted(groupPosition);
        } else {
            notifyDataChanged();
        }
    }

    public void collapseGroup(int groupPosition, boolean animate) {
        if (groupPosition == 0) {
            isFollowingExpand = false;
        }
        else {
            isFollowersExpand = false;
        }
        if (animate) {
            notifyChildrenRemoved(groupPosition);
        } else {
            notifyDataChanged();
        }
    }

    public boolean isExpand(int groupPosition) {
        return groupPosition == 0? isFollowingExpand: isFollowersExpand;
    }

    @Override
    public void onBindChildViewHolder(BaseViewHolder holder, int groupPosition, int childPosition) {
        ImageView avatar = holder.get(R.id.userAvatar);
        TextView userName = holder.get(R.id.userName);
        TextView userDescription = holder.get(R.id.userDescription);
        CheckBox checkbox = holder.get(R.id.atCheckBox);
        final User currentUser = groupPosition == 0? Constant.currentUser.getFollowing().get(childPosition): Constant.currentUser.getFollowers().get(childPosition);
        Glide.with(mContext)
                .load(currentUser.getAvatar())
                .apply(RequestOptions.bitmapTransform(new CircleCrop()))
                .into(avatar);
        userName.setText(currentUser.getNickname());
        if (currentUser.getDescription() == null || currentUser.getDescription().equals("")) {
            userDescription.setVisibility(View.GONE);
        }
        else {
            userDescription.setText(currentUser.getDescription());
        }
        checkbox.setChecked(false);
        checkbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                // 选中就加入，不选中就移除
                if (b) {
                    checkedUsers.put(currentUser.getUserId(), currentUser);
                }
                else {
                    checkedUsers.keySet().remove(currentUser.getUserId());
                }
            }
        });
    }
}
