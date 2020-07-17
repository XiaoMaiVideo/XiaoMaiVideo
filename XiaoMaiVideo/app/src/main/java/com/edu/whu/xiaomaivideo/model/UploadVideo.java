package com.edu.whu.xiaomaivideo.model;

import java.util.Date;
import java.util.List;

public class UploadVideo {
    /**
     * 用户上传视频
     */
    private String UserId;
    private String nickname;  //显示用户的昵称(可有可无)
    private String url;  //服务器端的数据库中的url 应该是用json格式存储的
    private Date time;  //上传时间
    private String content;  //对视频的描述
    private Integer likeNumber;  //获赞数
    private Integer message;  //评论数
    private Integer shareNumber; //分享数

    private List<String> messageList; //评论的具体内容
 //  private String location; //上传视频的地点

    public UploadVideo() {

    }

    public UploadVideo(String userId) {
        UserId = userId;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getLikeNumber() {
        return likeNumber;
    }

    public void setLikeNumber(Integer likeNumber) {
        this.likeNumber = likeNumber;
    }

    public Integer getMessage() {
        return message;
    }

    public void setMessage(Integer message) {
        this.message = message;
    }

    public Integer getShareNumber() {
        return shareNumber;
    }

    public void setShareNumber(Integer shareNumber) {
        this.shareNumber = shareNumber;
    }
    public List<String> getMessageList() {
        return messageList;
    }

    public void setMessageList(List<String> messageList) {
        this.messageList = messageList;
    }

}
