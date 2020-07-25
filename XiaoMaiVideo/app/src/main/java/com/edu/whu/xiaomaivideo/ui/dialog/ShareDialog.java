/**
 * Author: 张俊杰、叶俊豪
 * Create Time: 2020/7/16
 * Update Time: 2020/7/22
 */

package com.edu.whu.xiaomaivideo.ui.dialog;

import android.content.Context;
import android.view.View;

import androidx.annotation.NonNull;

import com.alibaba.fastjson.JSON;
import com.edu.whu.xiaomaivideo.R;
import com.edu.whu.xiaomaivideo.model.MessageVO;
import com.edu.whu.xiaomaivideo.model.Movie;
import com.edu.whu.xiaomaivideo.restcallback.RestCallback;
import com.edu.whu.xiaomaivideo.restservice.UserRestService;
import com.edu.whu.xiaomaivideo.util.Constant;
import com.edu.whu.xiaomaivideo.util.EventBusMessage;
import com.google.android.material.textfield.TextInputEditText;
import com.lxj.xpopup.core.BottomPopupView;
import org.greenrobot.eventbus.EventBus;

import de.mustafagercek.library.LoadingButton;

public class ShareDialog extends BottomPopupView {
    LoadingButton loadingButton;
    TextInputEditText textInputEditText;
    Movie movie;
    OnShareMsgSendListener mListener;

    public ShareDialog(@NonNull Context context, Movie movie, OnShareMsgSendListener listener) {
        super(context);
        this.movie = movie;
        this.mListener = listener;
    }

    @Override
    protected int getImplLayoutId() {
        return R.layout.share_dialog;
    }

    @Override
    protected void onCreate() {
        super.onCreate();
        loadingButton = findViewById(R.id.button);
        textInputEditText = findViewById(R.id.text);
        loadingButton.setButtonOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                loadingButton.onStartLoading();
                UserRestService.addUserShare(movie.getMovieId(), textInputEditText.getText().toString(), new RestCallback() {
                    @Override
                    public void onSuccess(int resultCode) {
                        if (resultCode == Constant.RESULT_SUCCESS) {
                            // 成功
                            loadingButton.onStopLoading();
                            mListener.onShareMsgSend();
                            dismiss();
                        }
                    }
                });
            }
        });
    }

    public interface OnShareMsgSendListener {
        void onShareMsgSend();
    }
}
