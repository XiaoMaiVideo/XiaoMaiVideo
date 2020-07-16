/**
 * Author: 张俊杰
 * Create Time: 2020/7/15
 * Update Time: 2020/7/16
 */


package com.edu.whu.xiaomaivideo_backend.controller;

import com.edu.whu.xiaomaivideo_backend.model.*;
import com.edu.whu.xiaomaivideo_backend.service.CommitRestService;
import com.edu.whu.xiaomaivideo_backend.service.MessageRestService;
import com.edu.whu.xiaomaivideo_backend.service.MovieRestService;
import com.edu.whu.xiaomaivideo_backend.service.UserRestService;
import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@ServerEndpoint("/ws/{userId}")
@Component
@Slf4j
public class OneToOneWebsocketServer {
    public static UserRestService userRestService;


    public static MessageRestService messageRestService;


    public static CommitRestService commitRestService;


    public static MovieRestService movieRestService;

    /**
     * 用于存放所有在线客户端
     */
    private static Map<Long, OneToOneWebsocketServer> clients = new ConcurrentHashMap<>();

    private Gson gson = new Gson();

    private Session session;
    /**接收userId*/
    private Long userId;


    @OnOpen
    public void onOpen(Session session,@PathParam("userId") Long userId) {
        log.info("有新的客户端上线: {}", userId);
        this.session = session;
        this.userId = userId;
        clients.put(userId, this);
        try {
            session.getBasicRemote().sendText("连接成功"+userId);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @OnClose
    public void onClose() {

        log.info("有客户端离线: {}", userId);
        clients.remove(userId);
    }

    @OnError
    public void onError(Session session, Throwable throwable) {
        if (clients.get(userId) != null) {
            log.info("发生了错误,移除客户端: {}", userId);
            clients.remove(userId);
        }
        throwable.printStackTrace();
    }

    @OnMessage
    public void onMessage(String message) {
        log.info("收到客户端发来的消息: {}", message);
        MessageVO messageVO= gson.fromJson(message, MessageVO.class);
        User sender = userRestService.getUserById(messageVO.getSenderId());
        User receiver = userRestService.getUserById(messageVO.getReceiverId());
        switch (messageVO.getMsgType()) {
            case "msg":
                //发送私信消息以websocket形式
                Message message1=new Message();
                message1.setSender(sender);
                message1.setReceiver(receiver);
                message1.setText(messageVO.getText());
                message1.setTime(new Date());
                message1.setMsgType(messageVO.getMsgType());
                try{
                    messageRestService.saveMessage(message1);
                    log.info("成功保存信息{}",message1.getText());
                }catch (Exception e){

                }
                break;
            case "like":
                //点赞使用post+websocket
                break;
            case "commit":
                //commit时，需提交movieId
                Comment comment =new Comment();
                comment.setMsg(messageVO.getText());
                comment.setCommiter(sender);
                comment.setMovie(movieRestService.getMovieById(messageVO.getMovieId()));
                comment.setTime(new Date());
                try{
                    commitRestService.saveCommit(comment);
                    log.info("成功保存评论{}", comment.getMsg());
                }catch (Exception e){

                }
                break;

        }
        this.sendTo(messageVO);
    }

    private void sendTo(MessageVO message) {
        Session s;
        try{
            s = clients.get(message.getReceiverId()).session;
        }catch (Exception e){
            log.info("当前用户不在线，不做推送:{}",message.getText());
            return;
        }
        if (s != null) {
            try {
                s.getBasicRemote().sendText(gson.toJson(message));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
