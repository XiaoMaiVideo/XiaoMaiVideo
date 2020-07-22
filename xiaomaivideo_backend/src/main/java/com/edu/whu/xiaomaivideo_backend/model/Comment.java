/**
 * Author: 张俊杰
 * Create Time: 2020/7/16
 * Update Time: 2020/7/22
 */

package com.edu.whu.xiaomaivideo_backend.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name="comment_tabel")
public class Comment {
    @Id
    @GeneratedValue
    private Long commentId;

    @ManyToOne
    @JsonIgnoreProperties(value = {"comments","likeMovies","movies","sendmsgs","following","followers","receivemsgs","shares"})
    @JoinColumn(name = "commenterId")
    private User commenter;

    @ManyToOne
    @JsonIgnoreProperties(value = {"publisher","likers","comments"})
    @JoinColumn(name = "movieId")
    private Movie movie;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date time;

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    private String msg;

    public Long getCommentId() {
        return commentId;
    }

    public void setCommentId(Long commentId) {
        this.commentId = commentId;
    }

    public User getCommenter() {
        return commenter;
    }

    public void setCommenter(User commenter) {
        this.commenter = commenter;
    }

    public Movie getMovie() {
        return movie;
    }

    public void setMovie(Movie movie) {
        this.movie = movie;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
