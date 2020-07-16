/**
 * Author: 叶俊豪
 * Create Time: 2020/7/15
 * Update Time: 2020/7/15
 */

package com.edu.whu.xiaomaivideo.model;

import org.parceler.Parcel;

import java.util.Date;

@Parcel
public class Message {
    private Long msgId;
    private Long senderId;
    private Long receiverId;
    private Date time;
    private String text;
    private String msgType;

    public Long getMsgId() {
        return msgId;
    }

    public void setMsgId(Long msgId) {
        this.msgId = msgId;
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

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
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
}
