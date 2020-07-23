package com.edu.whu.xiaomaivideo.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.edu.whu.xiaomaivideo.R;

public class MsgAdapter  extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    class MsgViewHolder extends RecyclerView.ViewHolder
    {

        private TextView username;
        private TextView msg;
        private TextView date;
        private ImageView avatar;


        public MsgViewHolder(@NonNull View itemView) {
            super(itemView);
            username = itemView.findViewById(R.id.username);
            msg= itemView.findViewById(R.id.msg);
            date= itemView.findViewById(R.id.date);
            avatar = itemView.findViewById(R.id.avatar);
        }
    }
}
