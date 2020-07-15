/**
 * Author: 张俊杰、叶俊豪
 * Create Time: 2020/7/15
 * Update Time: 2020/7/15
 */


package com.edu.whu.xiaomaivideo_backend.model;

import com.fasterxml.jackson.annotation.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name="movie_table")
public class Movie {

    @Id
    @GeneratedValue
    private Long movieId;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date publishTime;
    private String url;
    private String description;

    //当序列化publisher时，忽略掉{"likeMovies","movies"}两个属性
    @JsonIgnoreProperties(value = {"likeMovies","movies"})
    @ManyToOne
    @JoinColumn(name = "userId")
    private User publisher;

    //当序列化likers时，忽略掉{"likeMovies","movies"}两个属性
    @JsonIgnoreProperties(value = {"likeMovies","movies"})
    @ManyToMany(cascade = CascadeType.REFRESH,mappedBy = "likeMovies")
    private List<User> likers=new ArrayList<>();

    private String categories;

    public List<User> getLikers() {
        return likers;
    }

    public void setLikers(List<User> likers) {
        this.likers = likers;
    }

    public User getPublisher() {
        return publisher;
    }

    public void setPublisher(User publisher) {
        this.publisher = publisher;
    }

    public Long getMovieId() {
        return movieId;
    }

    public void setMovieId(Long movieId) {
        this.movieId = movieId;
    }

    public Date getPublishTime() {
        return publishTime;
    }

    public void setPublishTime(Date publishTime) {
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

    public String getCategories() {
        return categories;
    }

    public void setCategories(String categories) {
        this.categories = categories;
    }
}
