/**
 * Author: 张俊杰
 * Create Time: 2020/7/20
 * Update Time: 2020/7/22
 */

package com.edu.whu.xiaomaivideo.viewModel;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.edu.whu.xiaomaivideo.model.Message;

import java.util.List;

public class ChatViewModel extends ViewModel{

    MutableLiveData<List<Message>> msgs;

    public ChatViewModel() {
        msgs=new MutableLiveData<>();
    }

    public MutableLiveData<List<Message>> getMsgs() {
        return msgs;
    }

    public void setMsgs(List<Message> msgs) {
        this.msgs.setValue(msgs);
    }
}
