/**
 * author 何慷
 * Create Time : 2020/7/17
 */
package com.edu.whu.xiaomaivideo.ui.activity;

import android.view.View;

import com.edu.whu.xiaomaivideo.R;

public class CommentActivity  extends BaseActivity {

    @Override
    int getLayoutId() {
        return R.layout.activity_comment;
    }

    @Override
    void initView() {
    }

    @Override
    void initData() {

    }


    public void Single(View view) {
        startActivity(CommentSingleActivity.class);
    }
}
