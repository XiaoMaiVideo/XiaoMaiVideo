/**
 * Author: 张俊杰
 * Create Time: 2020/7/20
 * Update Time: 2020/7/22
 */

package com.edu.whu.xiaomaivideo_backend.controller;

import com.edu.whu.xiaomaivideo_backend.model.AjaxResponse;
import com.edu.whu.xiaomaivideo_backend.model.Message;
import com.edu.whu.xiaomaivideo_backend.model.MessageVO;
import com.edu.whu.xiaomaivideo_backend.model.User;
import com.edu.whu.xiaomaivideo_backend.service.MessageRestService;
import com.edu.whu.xiaomaivideo_backend.service.UserRestService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@Controller
@RequestMapping("/rest")
public class MessageController {

    @Resource(name="userRestJPAServiceImpl")
    UserRestService userRestService;

    @Resource(name="messageRestJPAServiceImpl")
    MessageRestService messageRestService;

    @GetMapping("/getMessages")
    public @ResponseBody
    AjaxResponse getMessages(@RequestParam Long receiverId, @RequestParam Long senderId) {
        User receiver=userRestService.getUserById(receiverId);
        User sender=userRestService.getUserById(senderId);

        List<Message> messages=new ArrayList<>();
        messages.addAll(messageRestService.getMessages(receiver,sender));
        messages.addAll(messageRestService.getMessages(sender,receiver));


        Collections.sort(messages, new Comparator<Message>() {

            @Override
            public int compare(Message m1, Message m2) {
                return m1.getTime().compareTo(m2.getTime());
            }
        });

        return AjaxResponse.success(messages);
    }
}
