/**
 * Author: 张俊杰
 * Create Time: 2020/7/16
 * Update Time: 2020/7/16
 */

package com.edu.whu.xiaomaivideo.ui.dialog;

import android.app.Notification;
import android.content.Context;
import android.view.View;

import androidx.annotation.NonNull;

import com.alibaba.fastjson.JSON;
import com.edu.whu.xiaomaivideo.R;
import com.edu.whu.xiaomaivideo.model.MessageVO;
import com.edu.whu.xiaomaivideo.util.Constant;
import com.google.android.material.textfield.TextInputEditText;
import com.lxj.xpopup.core.BottomPopupView;

import de.mustafagercek.library.LoadingButton;

public class CommentDialog extends BottomPopupView {
    LoadingButton loadingButton;
    TextInputEditText textInputEditText;
    Long movieId;
    Long receiverId;

    public CommentDialog(@NonNull Context context,Long movieId, Long receiverId) {
        super(context);
        this.movieId=movieId;
        this.receiverId=receiverId;
    }

    @Override
    protected int getImplLayoutId() {
        return R.layout.comment_dialog;
    }

    @Override
    protected void onCreate() {
        super.onCreate();
        loadingButton=findViewById(R.id.button);
        textInputEditText=findViewById(R.id.text);
        loadingButton.setButtonOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                MessageVO messageVO=new MessageVO(Constant.CurrentUser.getUserId(),receiverId,textInputEditText.getText().toString(),"comment",movieId);
                String json = JSON.toJSONString(messageVO);

            }
        });
    }
}
