/**
 * Author: 张俊杰、叶俊豪
 * Create Time: 2020/7/8
 * Update Time: 2020/7/15
 */


package com.edu.whu.xiaomaivideo_backend.model;


import com.fasterxml.jackson.annotation.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name="user_table")
@JsonIgnoreProperties(value="password", allowSetters=true)
public class User {
    @Id
    @GeneratedValue
    private Long userId;
    private String username;

    private String password;
    private String gender;
    private String nickname;
    private String avatar;
    private String description;
    private String birthday;
    private String area;
    private String workplace;

    @OneToMany(cascade = CascadeType.ALL,mappedBy = "publisher")
    private List<Movie> movies=new ArrayList<>();

    @JsonIgnoreProperties(value = {"sender","receiver"})
    @OneToMany(cascade = CascadeType.ALL,mappedBy = "sender")
    private List<Message> sendmsgs=new ArrayList<>();

    public List<Message> getSendmsgs() {
        return sendmsgs;
    }

    public void setSendmsgs(List<Message> sendmsgs) {
        this.sendmsgs = sendmsgs;
    }

    public List<Message> getReceivemsgs() {
        return receivemsgs;
    }

    public void setReceivemsgs(List<Message> receivemsgs) {
        this.receivemsgs = receivemsgs;
    }

    @JsonIgnoreProperties(value = {"sender","receiver"})
    @OneToMany(cascade = CascadeType.ALL,mappedBy = "receiver")
    private List<Message> receivemsgs=new ArrayList<>();

    @ManyToMany(cascade = CascadeType.REFRESH)
    @JoinTable(name = "like_tabel",joinColumns = @JoinColumn(name = "userId"),inverseJoinColumns = @JoinColumn(name="movieId"))
    private List<Movie> likeMovies=new ArrayList<>();

    public List<Movie> getLikeMovies() {
        return likeMovies;
    }

    public void setLikeMovies(List<Movie> likeMovies) {
        this.likeMovies = likeMovies;
    }

    public List<Movie> getMovies() {
        return movies;
    }

    public void setMovies(List<Movie> movies) {
        this.movies = movies;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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
}
