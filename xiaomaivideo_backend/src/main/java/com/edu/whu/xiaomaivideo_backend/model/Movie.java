/**
 * Author: 张俊杰、叶俊豪
 * Create Time: 2020/7/15
 * Update Time: 2020/7/24
 */


package com.edu.whu.xiaomaivideo_backend.model;

import com.fasterxml.jackson.annotation.*;

import javax.persistence.*;
import javax.persistence.criteria.CriteriaBuilder;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name="movie_table")
public class Movie {

    @Id
    @GeneratedValue
    private Long movieId;

    // @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private String publishTime;
    private String url;
    private String description;
    private String categories;
    private String location;
    private int likednum;
    private int commentnum;
    private int sharenum;

    @Transient
    private boolean isLike;//用户是否点赞

    @JsonIgnoreProperties(value = {"comments","likeMovies","movies","sendmsgs","following","followers","receivemsgs","shares"})
    @ManyToOne
    @JoinColumn(name = "userId")
    private User publisher;

    //当序列化likers时，忽略掉{"likeMovies","movies"}两个属性
    @JsonIgnoreProperties(value = {"comments","likeMovies","movies","sendmsgs","following","followers","receivemsgs","shares"})
    @ManyToMany(cascade = CascadeType.REFRESH,mappedBy = "likeMovies")
    private List<User> likers=new ArrayList<>();

    @JsonIgnoreProperties(value = {"movie"})
    @OneToMany(cascade = CascadeType.ALL,mappedBy = "movie")
    private List<Comment> comments =new ArrayList<>();

    public int getLikednum() {
        return likers.size();
    }

    public void setLikednum(int likenum) {
        this.likednum = likednum;
    }

    public boolean getIsLike() {
        return isLike;
    }

    public void setIsLike(boolean like) {
        isLike = like;
    }

    public int getCommentnum() {
        return comments.size();
    }

    public void setCommentnum(int commentnum) {
        this.commentnum = commentnum;
    }

    public int getSharenum() {
        return sharenum;
    }

    public void setSharenum(int sharenum) {
        this.sharenum = sharenum;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

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

    public String getCategories() {
        return categories;
    }

    public void setCategories(String categories) {
        this.categories = categories;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Movie movie = (Movie) o;
        return movieId.equals(movie.movieId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(publishTime, url, description, categories);
    }
}
