/**
 * Author: 叶俊豪
 * Create Time: 2020/7/14
 * Update Time: 2020/7/24
 */
package com.edu.whu.xiaomaivideo.ui.dialog;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.bumptech.glide.Glide;
import com.edu.whu.xiaomaivideo.R;
import com.lxj.xpopup.XPopup;
import com.lxj.xpopup.core.BottomPopupView;
import com.lxj.xpopup.util.XPopupUtils;

import de.mustafagercek.library.LoadingButton;

public class SimpleBottomDialog extends BottomPopupView {

    private Context mContext;
    private int imageId;
    private String text;
    private Long newMovieId;

    public SimpleBottomDialog(@NonNull Context context, int imageId, String text, Long movieId) {
        super(context);
        this.imageId = imageId;
        this.text = text;
        this.mContext = context;
        this.newMovieId = movieId;
    }

    @Override
    protected void onCreate() {
        super.onCreate();
        ImageView imageView = findViewById(R.id.bottomDialogImage);
        imageView.setImageResource(imageId);
        TextView textView = findViewById(R.id.bottomDialogText);
        textView.setText(text);
        LoadingButton atButton = findViewById(R.id.atButton);
        atButton.setButtonOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                SimpleBottomDialog.this.dismissWith(new Runnable() {
                    @Override
                    public void run() {
                        new XPopup.Builder(mContext).asCustom(new AtDialog(mContext, newMovieId)).show();
                    }
                });
            }
        });
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
