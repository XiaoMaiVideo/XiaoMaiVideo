/**
 * Author: 叶俊豪
 * Create Time: 2020/7/13
 * Update Time: 2020/7/23
 */
package com.edu.whu.xiaomaivideo.ui.dialog;

import android.content.Context;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.edu.whu.xiaomaivideo.R;
import com.lxj.xpopup.core.CenterPopupView;
import com.lxj.xpopup.util.XPopupUtils;

import de.mustafagercek.library.LoadingButton;

public class ProgressDialog extends CenterPopupView {
    ProgressBar mProgressBar;
    TextView mTextView;
    String mTitle;
    public ProgressDialog(@NonNull Context context, String title) {
        super(context);
        mTitle = title;
    }

    // 返回自定义弹窗的布局
    @Override
    protected int getImplLayoutId() {
        return R.layout.progress_dialog;
    }

    @Override
    protected void onCreate() {
        super.onCreate();
        mProgressBar = findViewById(R.id.dialogProgressBar);
        mTextView = findViewById(R.id.progressDialogTitle);
        mTextView.setText(mTitle);
    }

    public void setProgress(int progress) {
        mProgressBar.setProgress(progress);
    }
}
