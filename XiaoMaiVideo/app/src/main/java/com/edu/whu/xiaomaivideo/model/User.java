/**
 * Author: 张俊杰、叶俊豪
 * Create Time: 2020/7/8
 * Update Time: 2020/7/16
 */

package com.edu.whu.xiaomaivideo.model;

import com.alibaba.fastjson.annotation.JSONField;

import org.parceler.Parcel;

import java.util.ArrayList;
import java.util.List;

@Parcel
public class User {
    private long userId;
    private String username;
    private String password;
    private String gender;
    private String nickname;
    private String avatar;
    private String description;
    private String birthday;
    private String area;
    private String workplace;

    private List<Message> sendmsgs;
    private List<Movie> movies;
    private List<Message> receivemsgs;
    private List<Movie> likeMovies;

    public User() {

    }

    // 未登录的游客
    public static User Visitor() {
        User user = new User();
        user.setUserId(0);
        user.setUsername("尚未登录...");
        user.setAvatar("https://ss0.bdstatic.com/70cFvHSh_Q1YnxGkpoWK1HF6hhy/it/u=1093847288,3038136586&fm=26&gp=0.jpg");
        return user;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getWorkplace() {
        return workplace;
    }

    public void setWorkplace(String workplace) {
        this.workplace = workplace;
    }

    public List<Message> getSendmsgs() {
        return sendmsgs;
    }

    public void setSendmsgs(List<Message> sendmsgs) {
        this.sendmsgs = sendmsgs;
    }

    public List<Movie> getMovies() {
        return movies;
    }

    public void setMovies(List<Movie> movies) {
        this.movies = movies;
    }

    public List<Message> getReceivemsgs() {
        return receivemsgs;
    }

    public void setReceivemsgs(List<Message> receivemsgs) {
        this.receivemsgs = receivemsgs;
    }

    public List<Movie> getLikeMovies() {
        return likeMovies;
    }

    public void setLikeMovies(List<Movie> likeMovies) {
        this.likeMovies = likeMovies;
    }

    public void addLikeMovies(Movie movie) {
        if (this.likeMovies == null) {
            likeMovies = new ArrayList<>();
        }
        likeMovies.add(movie);
    }

    public void addMovies(Movie movie) {
        if (this.movies == null) {
            movies = new ArrayList<>();
        }
        movies.add(movie);
    }

    // 判断用户是否点赞
    public boolean isLikeMovie(long movieId) {
        if (this.movies == null) {
            return false;
        }
        for (Movie movie : this.likeMovies) {
            if (movie.getMovieId() == movieId) {
                return true;
            }
        }
        return false;
    }
}
