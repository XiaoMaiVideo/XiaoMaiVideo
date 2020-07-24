/**
 * Author: 张俊杰
 * Create Time: 2020/7/16
 * Update Time: 2020/7/16
 * 点赞/评论/@的消息
 */

package com.edu.whu.xiaomaivideo.model;

import org.litepal.crud.LitePalSupport;

import java.util.Date;

public class MessageVO extends LitePalSupport {
    private Long senderId;
    private Long receiverId;
    private String text;
    private String msgType;
    private Long movieId;
    private Date time;

    public MessageVO() {
    }

    public MessageVO(Long senderId, Long receiverId, String text, String msgType, Long movieId) {
        this.senderId = senderId;
        this.receiverId = receiverId;
        this.text = text;
        this.msgType = msgType;
        this.movieId = movieId;
    }

    public Long getSenderId() {
        return senderId;
    }

    public void setSenderId(Long senderId) {
        this.senderId = senderId;
    }

    public Long getReceiverId() {
        return receiverId;
    }

    public void setReceiverId(Long receiverId) {
        this.receiverId = receiverId;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getMsgType() {
        return msgType;
    }

    public void setMsgType(String msgType) {
        this.msgType = msgType;
    }

    public Long getMovieId() {
        return movieId;
    }

    public void setMovieId(Long movieId) {
        this.movieId = movieId;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }
}
