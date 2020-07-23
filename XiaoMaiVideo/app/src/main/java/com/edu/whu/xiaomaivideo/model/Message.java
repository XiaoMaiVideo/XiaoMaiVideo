/**
 * Author: 叶俊豪
 * Create Time: 2020/7/15
 * Update Time: 2020/7/15
 */

package com.edu.whu.xiaomaivideo.model;

import org.litepal.annotation.Column;
import org.litepal.crud.LitePalSupport;
import org.parceler.Parcel;

import java.util.Date;

@Parcel
public class Message {
    private Long msgId;
    private User sender;
    private User receiver;
    private Date time;
    private String text;
    private String msgType;
    private Movie atMovie;

    public Long getMsgId() {
        return msgId;
    }

    public void setMsgId(Long msgId) {
        this.msgId = msgId;
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

    public User getSender() {
        return sender;
    }

    public void setSender(User sender) {
        this.sender = sender;
    }

    public User getReceiver() {
        return receiver;
    }

    public void setReceiver(User receiver) {
        this.receiver = receiver;
    }

    public Movie getAtMovie() {
        return atMovie;
    }

    public void setAtMovie(Movie atMovie) {
        this.atMovie = atMovie;
    }
}
