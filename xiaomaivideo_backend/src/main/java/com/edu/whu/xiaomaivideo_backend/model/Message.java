/**
 * Author: 张俊杰
 * Create Time: 2020/7/15
 * Update Time: 2020/7/22
 */

package com.edu.whu.xiaomaivideo_backend.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name="msg_tabel")
public class Message {
    @Id
    @GeneratedValue
    private Long msgId;

    @ManyToOne
    @JsonIgnoreProperties(value = {"publisher","likers","comments"})
    private Movie atMovie;

    @ManyToOne
    @JsonIgnoreProperties(value = {"comments","likeMovies","movies","sendmsgs","following","followers","receivemsgs","shares"})
    @JoinColumn(name = "senderId")
    private User sender;

    @ManyToOne
    @JsonIgnoreProperties(value = {"comments","likeMovies","movies","sendmsgs","following","followers","receivemsgs","shares"})
    @JoinColumn(name = "receiverId")
    private User receiver;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date time;

    private String text;

    private String msgType;

    public Movie getAtMovie() {
        return atMovie;
    }

    public void setAtMovie(Movie atMovie) {
        this.atMovie = atMovie;
    }

    public String getMsgType() {
        return msgType;
    }

    public void setMsgType(String msgType) {
        this.msgType = msgType;
    }

    public Long getMsgId() {
        return msgId;
    }

    public void setMsgId(Long msgId) {
        this.msgId = msgId;
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
}
