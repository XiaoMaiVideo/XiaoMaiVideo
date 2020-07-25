/**
 * Author: 叶俊豪
 * Create Time: 2020/7/23
 * Update Time: 2020/7/23
 */
package com.edu.whu.xiaomaivideo.ui.dialog;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.edu.whu.xiaomaivideo.R;
import com.edu.whu.xiaomaivideo.adapter.LikersDialogAdapter;
import com.edu.whu.xiaomaivideo.model.Movie;
import com.edu.whu.xiaomaivideo.model.User;
import com.edu.whu.xiaomaivideo.restcallback.MovieRestCallback;
import com.edu.whu.xiaomaivideo.restservice.MovieRestService;
import com.lxj.xpopup.XPopup;
import com.lxj.xpopup.core.BasePopupView;
import com.lxj.xpopup.core.BottomPopupView;
import com.lxj.xpopup.util.XPopupUtils;

import java.util.List;

public class LikersDialog extends BottomPopupView {
    Context mContext;
    Movie mMovie;
    RecyclerView mRecyclerView;
    LikersDialogAdapter mAdapter;
    public LikersDialog(@NonNull Context context, Movie movie) {
        super(context);
        mContext = context;
        mMovie = movie;

    }

    @Override
    protected int getImplLayoutId() {
        return R.layout.likers_dialog;
    }

    @Override
    protected void onCreate() {
        super.onCreate();
        ImageView imageView = findViewById(R.id.dialog_bottomsheet_iv_close);
        imageView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                LikersDialog.this.dismiss();
            }
        });
        setRecyclerView();
    }

    private void setRecyclerView() {
        mAdapter = new LikersDialogAdapter(mContext, mMovie.getLikers());
        mRecyclerView = findViewById(R.id.likersRecyclerView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    protected int getMaxHeight() {
        return (int) (XPopupUtils.getWindowHeight(getContext())*.6f);
    }
}
