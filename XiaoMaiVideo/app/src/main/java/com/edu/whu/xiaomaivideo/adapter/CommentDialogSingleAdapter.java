/**
 * author: 何慷、李季东
 * createTime：2020/7/17
 * Update Time: 2020/7/18
 */
package com.edu.whu.xiaomaivideo.adapter;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.edu.whu.xiaomaivideo.R;
import com.edu.whu.xiaomaivideo.model.Comment;
import com.edu.whu.xiaomaivideo.util.TimeUtil;
import com.makeramen.roundedimageview.RoundedImageView;


public class CommentDialogSingleAdapter extends BaseQuickAdapter<Comment, BaseViewHolder> {


    public CommentDialogSingleAdapter() {
        super(R.layout.item_comment_new);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, Comment content) {
        RoundedImageView iv_header = helper.getView(R.id.iv_header);
        ImageView iv_like = helper.getView(R.id.iv_like);
        TextView tv_like_count = helper.getView(R.id.tv_like_count);
        TextView tv_user_name = helper.getView(R.id.tv_user_name);
        TextView tv_content = helper.getView(R.id.tv_content);
        TextView tv_time = helper.getView(R.id.tv_time);

        helper.addOnClickListener(R.id.rl_group);
        helper.addOnClickListener(R.id.ll_like);

        iv_like.setImageResource(content.getIsLike() == 0 ? R.mipmap.icon_topic_post_item_like : R.mipmap.icon_topic_post_item_like_blue);

        tv_like_count.setText(content.getLikeCount() + "");
        tv_like_count.setVisibility(content.getLikeCount() <= 0 ? View.GONE : View.VISIBLE);
        tv_content.setText(content.getMsg());
        tv_user_name.setText(content.getCommenter().getNickname());
        tv_time.setText(TimeUtil.getRecentTimeSpanByNow(content.getTime().getTime()));

        Glide.with(mContext).load(content.getCommenter().getAvatar()).into(iv_header);
    }
}
