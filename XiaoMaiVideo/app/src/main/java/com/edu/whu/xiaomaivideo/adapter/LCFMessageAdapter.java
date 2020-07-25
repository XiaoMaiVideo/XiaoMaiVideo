/**
 * Author:李季东，张俊杰，叶俊豪
 * Create Time: 2020/7/15
 * Update Time: 2020/7/24
 */

package com.edu.whu.xiaomaivideo.adapter;

import android.content.Context;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.RequestOptions;
import com.donkingliang.groupedadapter.adapter.GroupedRecyclerViewAdapter;
import com.donkingliang.groupedadapter.holder.BaseViewHolder;
import com.edu.whu.xiaomaivideo.R;
import com.edu.whu.xiaomaivideo.model.MessageVO;
import com.edu.whu.xiaomaivideo.model.User;
import com.edu.whu.xiaomaivideo.util.TimeUtil;

import java.util.List;

public class LCFMessageAdapter extends GroupedRecyclerViewAdapter {

    private Context mContext;
    private List<MessageVO> oldMessages, newMessages;
    List<User> oldUsers, newUsers;
    String mType;
    public LCFMessageAdapter(Context context, List<MessageVO> oldMessage, List<MessageVO> newMessage, List<User> oldUsers, List<User> newUsers, String type) {
        super(context);
        mContext = context;
        this.oldMessages = oldMessage;
        this.newMessages = newMessage;
        this.oldUsers = oldUsers;
        this.newUsers = newUsers;
        mType = type;
    }

    @Override
    public int getGroupCount() {
        return 2;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        if (groupPosition == 0) {
            return newMessages.size();
        }
        return oldMessages.size();
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
        return R.layout.activity_lcf_message_item;
    }

    @Override
    public void onBindHeaderViewHolder(BaseViewHolder holder, int groupPosition) {
        TextView textView = holder.get(R.id.mentioned_new_past);
        if (groupPosition == 0) {
            textView.setText("最新");
        }
        else {
            textView.setText("以往");
        }
    }

    @Override
    public void onBindFooterViewHolder(BaseViewHolder holder, int groupPosition) {

    }

    @Override
    public void onBindChildViewHolder(BaseViewHolder holder, int groupPosition, int childPosition) {
        ImageView avatar = holder.get(R.id.mentioned_image);
        TextView name = holder.get(R.id.mentioned_name);
        TextView content = holder.get(R.id.mentioned_content);
        TextView date = holder.get(R.id.mentioned_date);

        if (groupPosition == 0) {
            Glide.with(mContext)
                    .load(newUsers.get(childPosition).getAvatar())
                    .apply(RequestOptions.bitmapTransform(new CircleCrop()))
                    .into(avatar);
            name.setText(newUsers.get(childPosition).getNickname());
            if (mType.equals("like")) {
                content.setText("赞了你的视频，快去看看");
            } else if (mType.equals("comment")) {
                content.setText("评论了你的视频，快去看看");
            } else if (mType.equals("follow")) {
                content.setText("新关注了你，快去看看");
            }

            if (newMessages.get(childPosition).getTime() != null) {
                date.setText(TimeUtil.getRecentTimeSpanByNow(newMessages.get(childPosition).getTime().getTime()));
            } else {
                date.setText("5天前");
            }
        }
        else {
            Glide.with(mContext)
                    .load(oldUsers.get(childPosition).getAvatar())
                    .apply(RequestOptions.bitmapTransform(new CircleCrop()))
                    .into(avatar);
            name.setText(oldUsers.get(childPosition).getNickname());
            if (mType.equals("like")) {
                content.setText("赞了你的视频，快去看看");
            } else if (mType.equals("comment")) {
                content.setText("评论了你的视频，快去看看");
            } else if (mType.equals("follow")) {
                content.setText("新关注了你，快去看看");
            }

            if (oldMessages.get(childPosition).getTime() != null) {
                date.setText(TimeUtil.getRecentTimeSpanByNow(oldMessages.get(childPosition).getTime().getTime()));
            } else {
                date.setText("5天前");
            }
        }
    }
}
