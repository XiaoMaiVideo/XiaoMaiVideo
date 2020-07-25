/**
 * Author: 叶俊豪
 * Create Time: 2020/7/24
 * Update Time: 2020/7/25
 */
package com.edu.whu.xiaomaivideo.ui.dialog;

import android.content.Context;
import android.view.View;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;

import com.edu.whu.xiaomaivideo.R;
import com.lxj.xpopup.core.AttachPopupView;

public class TakeVideoSelectionDialog extends AttachPopupView {

    OnItemClickListener mListener;

    public TakeVideoSelectionDialog(@NonNull Context context, OnItemClickListener listener) {
        super(context);
        mListener = listener;
    }

    @Override
    protected int getImplLayoutId() {
        return R.layout.take_video_selection_dialog;
    }

    @Override
    protected void onCreate() {
        super.onCreate();
        LinearLayout takeVideo = findViewById(R.id.takeVideo);
        LinearLayout selectVideo = findViewById(R.id.selectVideo);
        takeVideo.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.onLeftItemClick(TakeVideoSelectionDialog.this);
            }
        });
        selectVideo.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.onRightItemClick(TakeVideoSelectionDialog.this);
            }
        });
    }

    public interface OnItemClickListener {
        void onLeftItemClick(TakeVideoSelectionDialog dialog);
        void onRightItemClick(TakeVideoSelectionDialog dialog);
    }
}
