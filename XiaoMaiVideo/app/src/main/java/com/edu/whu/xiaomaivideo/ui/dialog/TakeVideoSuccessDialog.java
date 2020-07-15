package com.edu.whu.xiaomaivideo.ui.dialog;

import android.content.Context;

import androidx.annotation.NonNull;

import com.edu.whu.xiaomaivideo.R;
import com.lxj.xpopup.core.BottomPopupView;
import com.lxj.xpopup.util.XPopupUtils;

public class TakeVideoSuccessDialog extends BottomPopupView {

    public TakeVideoSuccessDialog(@NonNull Context context) {
        super(context);
    }
    // 返回自定义弹窗的布局
    @Override
    protected int getImplLayoutId() {
        return R.layout.take_video_success_dialog;
    }

    @Override
    protected int getMaxHeight() {
        return (int) (XPopupUtils.getWindowHeight(getContext())*.5f);
    }

}
