package com.edu.whu.xiaomaivideo.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.edu.whu.xiaomaivideo.R;
import com.edu.whu.xiaomaivideo.model.MessageVO;
import com.edu.whu.xiaomaivideo.model.User;

import java.util.List;

/**
 * Author:李季东,张俊杰
 * Create Time: 2020/7/15
 * Update Time: 2020/7/15
 * @我的adpter 内部的Item包含一个text和一个recyclerView。
 * 一共两个item，分别用来展示“最新@我的”和“以往@我的”
 */
public class LCFMessageAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context mContext;
    private LCFMessageItemAdapter mLCFMessageItemAdapter_old, mLCFMessageItemAdapter_new;
    private List<MessageVO> oldMessage;
    private List<MessageVO> newMessage;
    private String mType;

    public LCFMessageAdapter(Context context, String type, List<User> oldUsers, List<User> newUsers, List<MessageVO> oldMessage, List<MessageVO> newMessage)
    {
        this.mContext = context;
        this.oldMessage = oldMessage;
        this.newMessage = newMessage;
        this.mType = type;
        mLCFMessageItemAdapter_new = new LCFMessageItemAdapter(mContext, newMessage, newUsers, mType);
        mLCFMessageItemAdapter_old = new LCFMessageItemAdapter(mContext, oldMessage, oldUsers, mType);
        Log.e("LCFMessageAdapter", mLCFMessageItemAdapter_new.getItemCount()+"_");
    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        return new LCFMessageBlockViewHolder(LayoutInflater.from(mContext).inflate(R.layout.activity_lcf_message_block, viewGroup,false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int position) {
        //这里应该要设计两个adapter，或者使用数组来区分数据

        // LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext);
        if (position==0) {
            //最新@我的消息
            ((LCFMessageBlockViewHolder)viewHolder).newPast.setText("最新");
            ((LCFMessageBlockViewHolder)viewHolder).recyclerViewMentioned.setLayoutManager(new LinearLayoutManager(mContext));
            ((LCFMessageBlockViewHolder)viewHolder).recyclerViewMentioned.setAdapter(mLCFMessageItemAdapter_new);
        } else {
            //以往@我的消息
            ((LCFMessageBlockViewHolder)viewHolder).newPast.setText("以往");
            ((LCFMessageBlockViewHolder)viewHolder).recyclerViewMentioned.setLayoutManager(new LinearLayoutManager(mContext));
            ((LCFMessageBlockViewHolder)viewHolder).recyclerViewMentioned.setAdapter(mLCFMessageItemAdapter_old);
        }
    }

    @Override
    public int getItemCount() {
        return 2;
    }
    class LCFMessageBlockViewHolder extends RecyclerView.ViewHolder {
        private TextView newPast;
        private RecyclerView recyclerViewMentioned;

        public LCFMessageBlockViewHolder(@NonNull View itemView) {
            super(itemView);
            newPast = itemView.findViewById(R.id.mentioned_new_past);
            recyclerViewMentioned= itemView.findViewById(R.id.recyclerViewMentioned);
        }
    }
}

