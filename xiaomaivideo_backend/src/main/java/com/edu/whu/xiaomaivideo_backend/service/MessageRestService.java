/**
 * Author: 张俊杰
 * Create Time: 2020/7/15
 * Update Time: 2020/7/15
 */

package com.edu.whu.xiaomaivideo_backend.service;

import com.edu.whu.xiaomaivideo_backend.model.Message;

import java.util.List;

public interface MessageRestService {
    Message saveMessage(Message message);

    //void deleteMessage(Long id);

    //void updateMessage(Message message);

    //List<Message> getMessageBySenderId(Long id);

    //List<Message> getMessageByReceiverId(Long id);
}
