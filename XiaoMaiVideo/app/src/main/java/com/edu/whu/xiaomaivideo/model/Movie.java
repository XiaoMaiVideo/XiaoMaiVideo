/**
 * Author: 叶俊豪、李季东
 * Create Time: 2020/7/15
 * Update Time: 2020/7/18
 */

package com.edu.whu.xiaomaivideo.model;

import com.alibaba.fastjson.annotation.JSONField;

import org.parceler.Parcel;

import java.util.ArrayList;
import java.util.List;

@Parcel
public class Movie {
    private Long movieId;
    private String publishTime;
    private String url;
    private String description;
    private String location;

    @JSONField(serialize = false)
    private List<String> categoryList;

    @JSONField(serialize = false)
    private boolean isLike;

    private User publisher;
    private String categories;
    private List<User> likers;
    private List<Comment> comments;

    private int sharenum;
    private int commentnum;
    private int likednum;

    public Movie() {

    }

    public Movie(long id) {
        this.movieId = id;
    }

    public List<User> getLikers() {
        return likers;
    }

    public void setLikers(List<User> likers) {
        this.likers = likers;
    }

    public Long getMovieId() {
        return movieId;
    }

    public void setMovieId(Long movieId) {
        this.movieId = movieId;
    }

    public String getPublishTime() {
        return publishTime;
    }

    public void setPublishTime(String publishTime) {
        this.publishTime = publishTime;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public User getPublisher() {
        return publisher;
    }

    public void setPublisher(User publisher) {
        this.publisher = publisher;
    }

    public List<Comment> getComments(){ return  comments; }

    public  void  setComments(List<Comment> comments){ this.comments = comments; }

    public String getCategories() {
        return categories;
    }

    public void setCategories(String categories) {
        this.categories = categories;
    }

    public void addLiker(User user) {
        if (this.likers == null) {
            likers = new ArrayList<>();
        }
        likers.add(user);
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public int getSharenum() {
        return sharenum;
    }

    public void setSharenum(int sharenum) {
        this.sharenum = sharenum;
    }

    public int getCommentnum() {
        return commentnum;
    }

    public void setCommentnum(int commentnum) {
        this.commentnum = commentnum;
    }

    public int getLikednum() {
        return likednum;
    }

    public void setLikednum(int likednum) {
        this.likednum = likednum;
    }

    public List<String> getCategoryList() {
        return categoryList;
    }

    public void setCategoryList(List<String> categoryList) {
        this.categoryList = categoryList;
    }

    public boolean isLike() {
        return isLike;
    }

    public void setLike(boolean like) {
        isLike = like;
    }
}
