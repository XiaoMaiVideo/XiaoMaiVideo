/**
 * Author: 叶俊豪
 * Create Time: 2020/7/20
 * Update Time: 2020/7/20
 */

package com.edu.whu.xiaomaivideo.model;

import org.parceler.Parcel;

import java.util.Date;

@Parcel
public class Share {
    private Long shareId;
    private Movie movie;
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
