/**
 * Author: 张俊杰
 * Create Time: 2020/7/11
 * Update Time: 2020/7/23
 */

package com.edu.whu.xiaomaivideo.viewModel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.edu.whu.xiaomaivideo.model.Message;
import com.edu.whu.xiaomaivideo.util.Constant;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MessageViewModel extends ViewModel {
    MutableLiveData<List<ShowMsg>> showmsgs;

    public MessageViewModel() {
        showmsgs=new MutableLiveData<>();
    }

    public MutableLiveData<List<ShowMsg>> getShowmsgs() {
        boolean flag=false;
        List<ShowMsg>showMsgs=new ArrayList<>();

        ShowMsg showMsg=new ShowMsg();
        showMsg.setMsg("sssssssss");
        showMsg.setDate(new Date());
        showMsg.setAvatar("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1595501808078&di=21f013e2efd77653d83bd006eb3b68e5&imgtype=0&src=http%3A%2F%2Fa1.att.hudong.com%2F62%2F02%2F01300542526392139955025309984.jpg");
        showMsg.setUsername("sssssssss");
        showMsgs.add(showMsg);
        showmsgs.setValue(showMsgs);
        return showmsgs;

//        if (Constant.currentUser.getSendmsgs()==null&&Constant.currentUser.getReceivemsgs()==null){
//            showmsgs.setValue(showMsgs);
//            return showmsgs;
//        }
//        for (Message messages1:Constant.currentUser.getSendmsgs()) {
//            for (ShowMsg showMsg:showMsgs){
//                if (showMsg.username.equals(messages1.getReceiver().getUsername())){
//                    if (showMsg.getDate().compareTo(messages1.getTime()) < 0){
//                        showMsg.setAvatar(messages1.getReceiver().getAvatar());
//                        showMsg.setDate(messages1.getTime());
//                        showMsg.setMsg(messages1.getText());
//                    }
//                    flag=true;
//                    break;
//                }
//            }
//            if (!flag){
//                ShowMsg showMsg=new ShowMsg();
//                showMsg.setAvatar(messages1.getReceiver().getAvatar());
//                showMsg.setDate(messages1.getTime());
//                showMsg.setMsg(messages1.getText());
//                showMsg.setUsername(messages1.getReceiver().getUsername());
//                showMsgs.add(showMsg);
//            }
//            flag=false;
//        }
//        for (Message messages2:Constant.currentUser.getReceivemsgs()) {
//            for (ShowMsg showMsg:showMsgs){
//                if (showMsg.username.equals(messages2.getSender().getUsername())){
//                    if (showMsg.getDate().compareTo(messages2.getTime()) < 0){
//                        showMsg.setAvatar(messages2.getSender().getAvatar());
//                        showMsg.setDate(messages2.getTime());
//                        showMsg.setMsg(messages2.getText());
//                    }
//                    flag=true;
//                    break;
//                }
//            }
//            if (!flag){
//                ShowMsg showMsg=new ShowMsg();
//                showMsg.setAvatar(messages2.getSender().getAvatar());
//                showMsg.setDate(messages2.getTime());
//                showMsg.setMsg(messages2.getText());
//                showMsg.setUsername(messages2.getSender().getUsername());
//                showMsgs.add(showMsg);
//            }
//            flag=false;
//        }
//        showmsgs.setValue(showMsgs);
//        return showmsgs;
    }

    public void setShowmsgs(MutableLiveData<List<ShowMsg>> showmsgs) {
        this.showmsgs = showmsgs;
    }

    public class ShowMsg{
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
    }

}