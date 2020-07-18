package com.edu.whu.xiaomaivideo.ui.dialog;

import android.content.Context;
import android.view.View;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;

import com.edu.whu.xiaomaivideo.R;
import com.lxj.xpopup.core.CenterPopupView;
import com.lxj.xpopup.util.XPopupUtils;

import de.mustafagercek.library.LoadingButton;

public class ProgressDialog extends CenterPopupView {
    ProgressBar mProgressBar;
    public ProgressDialog(@NonNull Context context) {
        super(context);
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
    }

    public void setProgress(int progress) {
        mProgressBar.setProgress(progress);
    }
}
