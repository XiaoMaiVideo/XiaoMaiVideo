/**
 * Author: 张俊杰
 * Create Time: 2020/7/11
 * Update Time: 2020/7/11
 */

package com.edu.whu.xiaomaivideo.viewModel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.edu.whu.xiaomaivideo.model.Message;
import com.edu.whu.xiaomaivideo.util.Constant;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MessageViewModel extends ViewModel {

    public MutableLiveData<String> mText;
    Map<Long, List<Message>> map;

    public MessageViewModel() {
        map=new HashMap<>();
    }

    public LiveData<String> getmText() {
        return mText;
    }
    public void setMsg(){
        mText.setValue("测试");
    }

    public Map<Long, List<Message>> getMap() {
        Constant.currentUser.getSendmsgs();
        for (Message messages1:Constant.currentUser.getSendmsgs()) {
            if (!map.containsKey(messages1.getReceiverId())){
                List<Message> msgs=new ArrayList<>();
                msgs.add(messages1);
                map.put(messages1.getReceiverId(),msgs);
            }else {
                List<Message> msgs=map.get(messages1.getReceiverId());
                msgs.add(messages1);
                map.replace(messages1.getReceiverId(),msgs);
            }
        }
        for (Message messages2:Constant.currentUser.getReceivemsgs()) {
            if (!map.containsKey(messages2.getSenderId())){
                List<Message> msgs=new ArrayList<>();
                msgs.add(messages2);
                map.put(messages2.getSenderId(),msgs);
            }else {
                List<Message> msgs=map.get(messages2.getSenderId());
                msgs.add(messages2);
                map.replace(messages2.getSenderId(),msgs);
            }
        }
        return map;
    }

    public void setMap(Map<Long, List<Message>> map) {
        this.map = map;
    }
}