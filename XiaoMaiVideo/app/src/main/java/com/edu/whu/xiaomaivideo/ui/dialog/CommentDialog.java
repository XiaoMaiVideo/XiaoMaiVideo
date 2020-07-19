/**
 * Author: 张俊杰
 * Create Time: 2020/7/16
 * Update Time: 2020/7/16
 */

package com.edu.whu.xiaomaivideo.ui.dialog;

import android.content.Context;
import android.view.View;

import androidx.annotation.NonNull;

import com.alibaba.fastjson.JSON;
import com.edu.whu.xiaomaivideo.R;
import com.edu.whu.xiaomaivideo.model.MessageVO;
import com.edu.whu.xiaomaivideo.model.Movie;
import com.edu.whu.xiaomaivideo.util.Constant;
import com.edu.whu.xiaomaivideo.util.EventBusMessage;
import com.google.android.material.textfield.TextInputEditText;
import com.lxj.xpopup.core.BottomPopupView;
import org.greenrobot.eventbus.EventBus;

import de.mustafagercek.library.LoadingButton;

public class CommentDialog extends BottomPopupView {
    LoadingButton loadingButton;
    TextInputEditText textInputEditText;
    Movie movie;

    public CommentDialog(@NonNull Context context, Movie movie) {
        super(context);
        this.movie=movie;
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
                MessageVO message = new MessageVO();
                message.setMsgType("comment");
                message.setSenderId(Constant.currentUser.getUserId());
                message.setReceiverId(movie.getPublisher().getUserId());
                message.setMovieId(movie.getMovieId());
                message.setText(textInputEditText.getText().toString());
                EventBus.getDefault().post(new EventBusMessage(Constant.SEND_MESSAGE, JSON.toJSONString(message)));
                dismiss();
            }
        });
    }
}
