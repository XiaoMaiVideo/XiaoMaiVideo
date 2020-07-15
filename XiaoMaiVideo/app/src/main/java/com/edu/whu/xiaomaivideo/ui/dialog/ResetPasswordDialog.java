/**
 * Author: 叶俊豪
 * Create Time: 2020/7/14
 * Update Time: 2020/7/14
 */

package com.edu.whu.xiaomaivideo.ui.dialog;

import android.content.Context;
import android.view.View;

import androidx.annotation.NonNull;

import com.edu.whu.xiaomaivideo.R;
import com.lxj.xpopup.animator.PopupAnimator;
import com.lxj.xpopup.core.BottomPopupView;
import com.lxj.xpopup.core.CenterPopupView;
import com.lxj.xpopup.util.XPopupUtils;

import de.mustafagercek.library.LoadingButton;

public class ResetPasswordDialog extends BottomPopupView {

    OnConfirmButtonClickListener mListener;
    LoadingButton confirmButton;
    public ResetPasswordDialog(@NonNull Context context, OnConfirmButtonClickListener onClickListener) {
        super(context);
        this.mListener = onClickListener;
    }

    // 返回自定义弹窗的布局
    @Override
    protected int getImplLayoutId() {
        return R.layout.reset_password_dialog;
    }

    // 执行初始化操作，比如：findView，设置点击，或者任何你弹窗内的业务逻辑
    @Override
    protected void onCreate() {
        super.onCreate();
        LoadingButton cancelButton = findViewById(R.id.tv_cancel);
        cancelButton.setButtonOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss(); // 关闭弹窗
            }
        });
        LoadingButton confirmButton = findViewById(R.id.tv_confirm);
        confirmButton.setButtonOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.onClick(ResetPasswordDialog.this);
            }
        });
    }

    @Override
    protected int getMaxHeight() {
        return (int) (XPopupUtils.getWindowHeight(getContext())*.6f);
    }

    public interface OnConfirmButtonClickListener {
        void onClick(ResetPasswordDialog dialog);
    }
}
