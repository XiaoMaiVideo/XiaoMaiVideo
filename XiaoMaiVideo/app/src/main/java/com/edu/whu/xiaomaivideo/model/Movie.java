/**
 * Author: 叶俊豪
 * Create Time: 2020/7/15
 * Update Time: 2020/7/16
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

    private User publisher;
    private String categories;
    private List<User> likers;

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
}
