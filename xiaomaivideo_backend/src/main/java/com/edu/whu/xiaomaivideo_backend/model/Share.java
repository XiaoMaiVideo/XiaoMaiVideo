/**
 * Author: 张俊杰
 * Create Time: 2020/7/18
 * Update Time: 2020/7/18
 */

package com.edu.whu.xiaomaivideo_backend.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name="share_tabel")
public class Share {
    @Id
    @GeneratedValue
    private Long shareId;

    @ManyToOne
    @JsonIgnoreProperties(value = {"likers","comments"})
    private Movie movie;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date shareDate;

    private String msg;

    public Long getShareId() {
        return shareId;
    }

    public void setShareId(Long shareId) {
        this.shareId = shareId;
    }

    public Movie getMovie() {
        return movie;
    }

    public void setMovie(Movie movie) {
        movie.setSharenum(movie.getSharenum()+1);
        this.movie = movie;
    }

    public Date getShareDate() {
        return shareDate;
    }

    public void setShareDate(Date shareDate) {
        this.shareDate = shareDate;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
