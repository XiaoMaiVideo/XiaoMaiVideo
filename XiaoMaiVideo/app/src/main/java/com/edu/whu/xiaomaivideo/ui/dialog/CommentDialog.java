/**
 * Author: 张俊杰
 * Create Time: 2020/7/16
 * Update Time: 2020/7/16
 */

package com.edu.whu.xiaomaivideo.ui.dialog;

import android.content.Context;
import android.view.View;

import androidx.annotation.NonNull;

import com.edu.whu.xiaomaivideo.R;
import com.lxj.xpopup.core.BottomPopupView;

import de.mustafagercek.library.LoadingButton;

public class CommentDialog extends BottomPopupView {
    LoadingButton loadingButton;

    public CommentDialog(@NonNull Context context,Long movieId) {
        super(context);
    }

    @Override
    protected int getImplLayoutId() {
        return R.layout.comment_dialog;
    }

    @Override
    protected void onCreate() {
        super.onCreate();
        loadingButton=findViewById(R.id.button);
        loadingButton.setButtonOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }
}
