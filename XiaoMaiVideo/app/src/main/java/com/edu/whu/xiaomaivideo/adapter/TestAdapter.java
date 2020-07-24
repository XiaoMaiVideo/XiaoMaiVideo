package com.edu.whu.xiaomaivideo.adapter;

import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.RequestOptions;
import com.donkingliang.groupedadapter.adapter.GroupedRecyclerViewAdapter;
import com.donkingliang.groupedadapter.holder.BaseViewHolder;
import com.edu.whu.xiaomaivideo.R;
import com.edu.whu.xiaomaivideo.ui.activity.ChatActivity;
import com.edu.whu.xiaomaivideo.util.Constant;
import com.edu.whu.xiaomaivideo.util.TimeUtil;
import com.edu.whu.xiaomaivideo.viewModel.MessageViewModel;

import org.w3c.dom.Text;

import java.util.List;

public class TestAdapter extends GroupedRecyclerViewAdapter {

    List<MessageViewModel.ShowMsg> showMsgs;
    Context mContext;

    public TestAdapter(Context context, List<MessageViewModel.ShowMsg> showmsgs) {
        super(context);
        mContext = context;
        showMsgs = showmsgs;
    }

    @Override
    public int getGroupCount() {
        return 1;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return showMsgs.size();
    }

    @Override
    public boolean hasHeader(int groupPosition) {
        return false;
    }

    @Override
    public boolean hasFooter(int groupPosition) {
        return false;
    }

    @Override
    public int getHeaderLayout(int viewType) {
        return 0;
    }

    @Override
    public int getFooterLayout(int viewType) {
        return 0;
    }

    @Override
    public int getChildLayout(int viewType) {
        return R.layout.msg_item;
    }

    @Override
    public void onBindHeaderViewHolder(BaseViewHolder holder, int groupPosition) {

    }

    @Override
    public void onBindFooterViewHolder(BaseViewHolder holder, int groupPosition) {

    }

    @Override
    public void onBindChildViewHolder(BaseViewHolder holder, int groupPosition, int childPosition) {
        ((TextView) holder.get(R.id.username)).setText(showMsgs.get(childPosition).getUsername());
        ((TextView) holder.get(R.id.msg)).setText(showMsgs.get(childPosition).getMsg());
        ((TextView) holder.get(R.id.date)).setText(TimeUtil.getRecentTimeSpanByNow(showMsgs.get(childPosition).getDate().getTime()));
        Glide.with(mContext)
                .load(showMsgs.get(childPosition).getAvatar())
                .apply(RequestOptions.bitmapTransform(new CircleCrop()))
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .into((ImageView)holder.get(R.id.avatar));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MessageViewModel.ShowMsg currentMsg = showMsgs.get(childPosition);
                // 进入聊天页面
                Constant.currentChattingId = currentMsg.getUserId();
                Constant.currentChattingName = currentMsg.getUsername();
                Intent intent = new Intent(mContext, ChatActivity.class);
                mContext.startActivity(intent);
            }
        });
    }
}
