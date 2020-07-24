/**
 * Author: 张俊杰、叶俊豪
 * Create Time: 2020/7/8
 * Update Time: 2020/7/24
 */


package com.edu.whu.xiaomaivideo_backend.model;


import com.fasterxml.jackson.annotation.*;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name="user_table")
@JsonIgnoreProperties(value="password", allowSetters=true)
public class User {
    @Id
    @GeneratedValue
    private Long userId;        //ID
    private String username;    //用户名
    private String password;    //密码
    private String gender;      //性别
    private String nickname;    //昵称
    private String avatar;      //
    private String description; //个性签名
    private String birthday;    //生日
    private String area;        //地区
    private String workplace;   //公司
    private boolean canAcceptLikeMessage; // 是否接收点赞消息
    private boolean canAcceptCommentMessage; // 是否接收评论消息
    private boolean canAcceptFollowMessage; // 是否接收新粉丝消息
    private boolean isPrivateUser; // 是否为私密账户，若是则只能看到头像与昵称，不能看到其他个人信息
    private boolean isFollowListAccessible; // 是否允许别人看关注/粉丝列表

    @Transient
    private boolean isFollow;//用户是否关注


    @JsonIgnoreProperties(value = {"publisher","likers","comments"})
    @OneToMany(cascade = CascadeType.ALL,mappedBy = "publisher")
    private List<Movie> movies=new ArrayList<>();

    @JsonIgnoreProperties(value = {"sender"})
    @OneToMany(cascade = CascadeType.ALL,mappedBy = "sender")
    private List<Message> sendmsgs=new ArrayList<>();

    @JsonIgnoreProperties(value = {"commenter"})
    @OneToMany(cascade = CascadeType.ALL,mappedBy = "commenter")
    private List<Comment> comments =new ArrayList<>();

    @JsonIgnoreProperties(value = {"comments","likeMovies","movies","sendmsgs","following","followers","receivemsgs","shares"})
    @ManyToMany
    @LazyCollection(LazyCollectionOption.FALSE)
    @JoinTable(name = "follow_tabel",joinColumns = @JoinColumn(name = "followers_id"),
            inverseJoinColumns = @JoinColumn(name = "following_id"))
    private List<User> following;

    @JsonIgnoreProperties(value = {"comments","likeMovies","movies","sendmsgs","following","followers","receivemsgs","shares"})
    @ManyToMany(mappedBy = "following")
    private List<User> followers;

    @JsonIgnoreProperties(value = {"receiver"})
    @OneToMany(cascade = CascadeType.ALL,mappedBy = "receiver")
    private List<Message> receivemsgs=new ArrayList<>();


    @JsonIgnoreProperties(value = {"likers","comments"})
    @ManyToMany(cascade = CascadeType.REFRESH, fetch = FetchType.EAGER)
    @JoinTable(name = "like_tabel",joinColumns = @JoinColumn(name = "userId"),inverseJoinColumns = @JoinColumn(name="movieId"))
    private List<Movie> likeMovies=new ArrayList<>();

    @OneToMany(cascade=CascadeType.ALL)
    private List<Share> shares=new ArrayList<>();

    public List<Share> getShares() {
        return shares;
    }

    public boolean getIsFollow() {
        return isFollow;
    }

    public void setIsFollow(boolean follow) {
        isFollow = follow;
    }

    public void setShares(List<Share> shares) {
        this.shares = shares;
    }

    public List<User> getFollowing() {
        return following;
    }

    public void setFollowing(List<User> following) {
        this.following = following;
    }

    public List<User> getFollowers() {
        return followers;
    }

    public void setFollowers(List<User> followers) {
        this.followers = followers;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

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

    public void setArea(String area) { this.area = area; }

    public String getWorkplace() {return workplace; }

    public void setWorkplace(String workplace) { this.workplace = workplace; }

    public boolean isCanAcceptLikeMessage() {
        return canAcceptLikeMessage;
    }

    public void setCanAcceptLikeMessage(boolean canAcceptLikeMessage) {
        this.canAcceptLikeMessage = canAcceptLikeMessage;
    }

    public boolean isCanAcceptCommentMessage() {
        return canAcceptCommentMessage;
    }

    public void setCanAcceptCommentMessage(boolean canAcceptCommentMessage) {
        this.canAcceptCommentMessage = canAcceptCommentMessage;
    }

    public boolean isCanAcceptFollowMessage() {
        return canAcceptFollowMessage;
    }

    public void setCanAcceptFollowMessage(boolean canAcceptFollowMessage) {
        this.canAcceptFollowMessage = canAcceptFollowMessage;
    }

    public boolean isPrivateUser() {
        return isPrivateUser;
    }

    public void setPrivateUser(boolean privateUser) {
        isPrivateUser = privateUser;
    }

    public boolean isFollowListAccessible() {
        return isFollowListAccessible;
    }

    public void setFollowListAccessible(boolean followListAccessible) {
        isFollowListAccessible = followListAccessible;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return username.equals(user.username);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, username, password, gender, nickname, avatar, description, birthday, area, workplace, movies, sendmsgs, comments, following, followers, receivemsgs, likeMovies);
    }
}
