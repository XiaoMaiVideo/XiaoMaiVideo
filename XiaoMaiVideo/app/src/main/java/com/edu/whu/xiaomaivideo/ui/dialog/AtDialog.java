/**
 * Author: 叶俊豪
 * Create Time: 2020/7/23
 * Update Time: 2020/7/24
 */
package com.edu.whu.xiaomaivideo.ui.dialog;

import android.content.Context;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.fastjson.JSON;
import com.donkingliang.groupedadapter.adapter.GroupedRecyclerViewAdapter;
import com.donkingliang.groupedadapter.holder.BaseViewHolder;
import com.donkingliang.groupedadapter.widget.StickyHeaderLayout;
import com.edu.whu.xiaomaivideo.R;
import com.edu.whu.xiaomaivideo.adapter.AtDialogAdapter;
import com.edu.whu.xiaomaivideo.adapter.LikersDialogAdapter;
import com.edu.whu.xiaomaivideo.model.Message;
import com.edu.whu.xiaomaivideo.model.MessageVO;
import com.edu.whu.xiaomaivideo.model.Movie;
import com.edu.whu.xiaomaivideo.model.User;
import com.edu.whu.xiaomaivideo.restcallback.UserRestCallback;
import com.edu.whu.xiaomaivideo.restservice.UserRestService;
import com.edu.whu.xiaomaivideo.util.Constant;
import com.edu.whu.xiaomaivideo.util.EventBusMessage;
import com.lxj.xpopup.XPopup;
import com.lxj.xpopup.core.BasePopupView;
import com.lxj.xpopup.core.BottomPopupView;
import com.lxj.xpopup.util.XPopupUtils;

import org.greenrobot.eventbus.EventBus;

import java.util.Date;
import java.util.Map;

import de.mustafagercek.library.LoadingButton;

public class AtDialog extends BottomPopupView {

    Context mContext;
    RecyclerView mRecyclerView;
    AtDialogAdapter mAdapter;
    LoadingButton submitButton;
    Long newMovieId;

    public AtDialog(@NonNull Context context, Long movieId) {
        super(context);
        mContext = context;
        newMovieId = movieId;
    }

    @Override
    protected int getImplLayoutId() {
        return R.layout.at_dialog;
    }

    @Override
    protected void onCreate() {
        super.onCreate();
        BasePopupView popupView = new XPopup.Builder(mContext).asLoading().setTitle("加载中").show();
        UserRestService.getUserByID(Constant.currentUser.getUserId(), new UserRestCallback() {
            @Override
            public void onSuccess(int resultCode, User user) {
                super.onSuccess(resultCode, user);
                Constant.currentUser = user;
                setAdapter();
                setSubmitButton();
                popupView.dismiss();
            }
        });
    }

    private void setAdapter() {
        mAdapter = new AtDialogAdapter(mContext);
        mAdapter.setOnHeaderClickListener(new GroupedRecyclerViewAdapter.OnHeaderClickListener() {
            @Override
            public void onHeaderClick(GroupedRecyclerViewAdapter adapter, BaseViewHolder holder, int groupPosition) {
                AtDialogAdapter atDialogAdapter = (AtDialogAdapter) adapter;
                if (atDialogAdapter.isExpand(groupPosition)) {
                    atDialogAdapter.collapseGroup(groupPosition, true);
                }
                else {
                    atDialogAdapter.expandGroup(groupPosition, true);
                }
            }
        });
        mRecyclerView = findViewById(R.id.atRecyclerView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        mRecyclerView.setAdapter(mAdapter);
        StickyHeaderLayout layout = findViewById(R.id.sticky_layout);
        layout.setSticky(true);
    }

    private void setSubmitButton() {
        submitButton = findViewById(R.id.submitBtn);
        submitButton.setButtonOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                submitButton.onStartLoading();
                for (Map.Entry<Long, User> user: mAdapter.checkedUsers.entrySet()) {
                    // 逐个发消息
                    MessageVO message = new MessageVO();
                    message.setMovieId(newMovieId);
                    message.setSenderId(Constant.currentUser.getUserId());
                    message.setReceiverId(user.getKey());
                    message.setMsgType("at");
                    message.setText("我发布了新的视频，快来看看吧~");
                    EventBus.getDefault().post(new EventBusMessage(Constant.SEND_MESSAGE, JSON.toJSONString(message)));
                    Message message1 = new Message();
                    message1.setTime(new Date());
                    message1.setReceiver(user.getValue());
                    message1.setSender(Constant.currentUser);
                    message1.setAtMovie(new Movie(newMovieId));
                    message1.setMsgType("at");
                    message1.setText("我发布了新的视频，快来看看吧~");
                    Constant.currentUser.addSendmsgs(message1);
                    EventBus.getDefault().post(new EventBusMessage(Constant.UPDATE_MESSAGE_LIST, ""));
                }
                submitButton.onStopLoading();
                dismiss();
            }
        });
    }
}
