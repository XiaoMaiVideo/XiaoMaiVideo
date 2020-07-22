/**
 * Author: 李季东
 * Create Time: 2020/7/18
 * Update Time: 2020/7/18
 */
package com.edu.whu.xiaomaivideo.model;

import org.parceler.Parcel;

import java.util.Date;
@Parcel
public class Comment {
    private  long commentId;    //评论id
    private User commenter;     //评论发表人
    private Movie movie;        //评论的视频
    private Date time;          //发表评论时间
    private String msg;         //评论内容

    private int isLike;         //是否有赞
    private long likeCount;     //点赞数
    public long getCommentId(){return commentId;}
    public void setCommentId(long commentId){this.commentId=commentId;}
    public User getCommenter(){return commenter;}
    public void setCommenter(User commenter){this.commenter=commenter;}
    public Movie getMovie(){return movie;}
    public void  setMovie(Movie movie){this.movie=movie;}
    public Date getTime(){return time;}
    public void setTime(Date date){this.time=date;}
    public String getMsg(){return msg;}
    public void setMsg(String msg){this.msg=msg;}
    public int getIsLike() {
        return isLike;
    }
    public void setIsLike(int isLike) {
        this.isLike = isLike;
    }
    public long getLikeCount() {
        return likeCount;
    }
    public void setLikeCount(long likeCount) {
        this.likeCount = likeCount;
    }
}
