/**
 * Author: 张俊杰、叶俊豪
 * Create Time: 2020/7/11
 * Update Time: 2020/7/24
 */

package com.edu.whu.xiaomaivideo.viewModel;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.edu.whu.xiaomaivideo.model.Message;
import com.edu.whu.xiaomaivideo.model.User;
import com.edu.whu.xiaomaivideo.restcallback.UserRestCallback;
import com.edu.whu.xiaomaivideo.restservice.UserRestService;
import com.edu.whu.xiaomaivideo.ui.activity.LoginActivity;
import com.edu.whu.xiaomaivideo.util.CommonUtil;
import com.edu.whu.xiaomaivideo.util.Constant;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MessageViewModel extends ViewModel {
    MutableLiveData<List<ShowMsg>> showmsgs;

    public MessageViewModel() {
        showmsgs = new MutableLiveData<>(new ArrayList<>());
    }

    public MutableLiveData<List<ShowMsg>> getShowmsgs() {
        return showmsgs;
    }

    public void updateShowmsgs() {
        boolean flag = false;
        List<ShowMsg> showMsgs = new ArrayList<>();
        if (Constant.currentUser.getSendmsgs() == null && Constant.currentUser.getReceivemsgs() == null) {
            showmsgs.setValue(showMsgs);
            return;
        }
        for (Message messages1:Constant.currentUser.getSendmsgs()) {
            for (ShowMsg showMsg: showMsgs) {
                if (showMsg.username.equals(messages1.getReceiver().getNickname())){
                    if (showMsg.getDate().compareTo(messages1.getTime()) < 0){
                        showMsg.setUserId(messages1.getReceiver().getUserId());
                        showMsg.setAvatar(messages1.getReceiver().getAvatar());
                        showMsg.setDate(messages1.getTime());
                        showMsg.setMsg(messages1.getText());
                    }
                    flag = true;
                    break;
                }
            }
            if (!flag) {
                ShowMsg showMsg=new ShowMsg();
                showMsg.setUserId(messages1.getReceiver().getUserId());
                showMsg.setAvatar(messages1.getReceiver().getAvatar());
                showMsg.setDate(messages1.getTime());
                showMsg.setMsg(messages1.getText());
                showMsg.setUsername(messages1.getReceiver().getNickname());
                showMsgs.add(showMsg);
            }
            flag = false;
        }
        for (Message messages2:Constant.currentUser.getReceivemsgs()) {
            for (ShowMsg showMsg:showMsgs) {
                if (showMsg.username.equals(messages2.getSender().getNickname())){
                    if (showMsg.getDate().compareTo(messages2.getTime()) < 0){
                        showMsg.setUserId(messages2.getSender().getUserId());
                        showMsg.setAvatar(messages2.getSender().getAvatar());
                        showMsg.setDate(messages2.getTime());
                        showMsg.setMsg(messages2.getText());
                    }
                    flag = true;
                    break;
                }
            }
            if (!flag){
                ShowMsg showMsg=new ShowMsg();
                showMsg.setUserId(messages2.getSender().getUserId());
                showMsg.setAvatar(messages2.getSender().getAvatar());
                showMsg.setDate(messages2.getTime());
                showMsg.setMsg(messages2.getText());
                showMsg.setUsername(messages2.getSender().getNickname());
                showMsgs.add(showMsg);
            }
            flag = false;
        }
        showmsgs.setValue(showMsgs);
    }

    public void setShowmsgs(MutableLiveData<List<ShowMsg>> showmsgs) {
        this.showmsgs = showmsgs;
    }

    public void refreshUser(){
        if (Constant.currentUser.getUserId() == 0) {
            updateShowmsgs();
            return;
        }
        UserRestService.getUserByID(Constant.currentUser.getUserId(), new UserRestCallback() {
            @Override
            public void onSuccess(int resultCode, User user) {
                super.onSuccess(resultCode, user);
                Constant.setCurrentUser(user);
            }
        });
    }

    public class ShowMsg {
        Long userId;
        String avatar;
        Date date;
        String username;
        String msg;

        public String getAvatar() {
            return avatar;
        }

        public void setAvatar(String avatar) {
            this.avatar = avatar;
        }

        public Date getDate() {
            return date;
        }

        public void setDate(Date date) {
            this.date = date;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getMsg() {
            return msg;
        }

        public void setMsg(String msg) {
            this.msg = msg;
        }

        public Long getUserId() {
            return userId;
        }

        public void setUserId(Long userId) {
            this.userId = userId;
        }
    }

}