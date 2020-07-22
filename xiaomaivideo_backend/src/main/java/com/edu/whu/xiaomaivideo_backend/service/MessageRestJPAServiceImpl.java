/**
 * Author: 张俊杰
 * Create Time: 2020/7/15
 * Update Time: 2020/7/22
 */

package com.edu.whu.xiaomaivideo_backend.service;

import com.edu.whu.xiaomaivideo_backend.dao.MessageRepository;
import com.edu.whu.xiaomaivideo_backend.model.Message;
import com.edu.whu.xiaomaivideo_backend.model.User;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class MessageRestJPAServiceImpl implements MessageRestService{
    @Resource
    private MessageRepository messageRepository;

    @Override
    public Message saveMessage(Message message) {
        return messageRepository.save(message);
    }

    @Override
    public List<Message> getMessages(User receiver, User sender) {
        return messageRepository.findByReceiverAndSenderOrderByTime(receiver,sender);
    }

//    @Override
//    public List<Message> getMessageBySenderId(Long id) {
//        return messageRepository.findBySenderId(id);
//    }
//
//    @Override
//    public List<Message> getMessageByReceiverId(Long id) {
//        return messageRepository.findByReceiverId(id);
//    }
}
