package com.edu.whu.xiaomaivideo.ui.dialog;

import android.content.Context;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.bumptech.glide.Glide;
import com.edu.whu.xiaomaivideo.R;
import com.lxj.xpopup.core.BottomPopupView;
import com.lxj.xpopup.util.XPopupUtils;

public class SimpleBottomDialog extends BottomPopupView {

    private int imageId;
    private String text;

    public SimpleBottomDialog(@NonNull Context context, int imageId, String text) {
        super(context);
        this.imageId = imageId;
        this.text = text;
    }

    @Override
    protected void onCreate() {
        super.onCreate();
        ImageView imageView = findViewById(R.id.bottomDialogImage);
        imageView.setImageResource(imageId);
        TextView textView = findViewById(R.id.bottomDialogText);
        textView.setText(text);
    }

    // 返回自定义弹窗的布局
    @Override
    protected int getImplLayoutId() {
        return R.layout.simple_bottom_dialog;
    }

    @Override
    protected int getMaxHeight() {
        return (int) (XPopupUtils.getWindowHeight(getContext())*.5f);
    }

}
