/**
 * Author: 张俊杰、叶俊豪、李季东、方胜强
 * Create Time: 2020/7/8
 * Update Time: 2020/7/18
 */

package com.edu.whu.xiaomaivideo.model;

import com.alibaba.fastjson.annotation.JSONField;
import com.edu.whu.xiaomaivideo.util.Constant;

import org.parceler.Parcel;

import java.util.ArrayList;
import java.util.List;

@Parcel
public class User {
    private long userId;
    private String username;
    private String password;

    @JSONField(serialize = false)
    private String enter_uesr_center;
    private String gender;
    private String nickname;
    private String avatar;
    private String description;
    private String birthday;
    private String area;
    private String workplace;
    private boolean canAcceptLikeMessage; // 是否接收点赞消息
    private boolean canAcceptCommentMessage; // 是否接收评论消息
    private boolean canAcceptFollowMessage; // 是否接收新粉丝消息
    private boolean isPrivateUser; // 是否为私密账户，若是则只能看到头像与昵称，不能看到其他个人信息
    private boolean isFollowListAccessible; // 是否允许别人看关注/粉丝列表

    @JSONField(serialize = false)
    private boolean isFollow;

    private List<Comment> comments;
    private List<Message> sendmsgs;
    private List<Movie> movies;
    private List<Message> receivemsgs;
    private List<Movie> likeMovies;
    private List<User> followers;
    private List<User> following;
    private List<Share> shares;

    public User() {

    }

    // 未登录的游客
    public static User Visitor() {
        User user = new User();
        user.setUserId(0);
        user.setUsername("尚未登录...");
        user.setEnter_uesr_center("点击进行登录");
        user.setAvatar("https://ss0.bdstatic.com/70cFvHSh_Q1YnxGkpoWK1HF6hhy/it/u=1093847288,3038136586&fm=26&gp=0.jpg");
        return user;
    }

    public List<User> getFollowers() {
        return followers;
    }

    public void setFollowers(List<User> followers) {
        this.followers = followers;
    }

    public List<User> getFollowing() {
        return following;
    }

    public void setFollowing(List<User> following) {
        this.following = following;
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

    public String getEnter_uesr_center(){
        return enter_uesr_center;
    }

    public void setEnter_uesr_center(String enter_uesr_center) {
        this.enter_uesr_center= enter_uesr_center;
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

    public List<Comment> getComments() { return comments; }


    public void setComments(List<Comment> comments) { this.comments = comments; }
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

    public List<Share> getShares() {
        return shares;
    }

    public void setShares(List<Share> shares) {
        this.shares = shares;
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

    public void addShareMovies(Movie movie, String msg) {
        if (this.shares == null) {
            shares = new ArrayList<>();
        }
        Share share = new Share();
        share.setMsg(msg);
        share.setMovie(movie);
        shares.add(share);
    }

    // 判断某个用户是否点赞
    public boolean isLikeMovie(long movieId) {
        if (this.movies == null) {
            return false;
        }
        else if (this.getUserId() == 0) {
            return false;
        }
        for (Movie movie : this.likeMovies) {
            if (movie.getMovieId() == movieId) {
                return true;
            }
        }
        return false;
    }

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

    public void addSendmsgs(Message message) {
        if (sendmsgs == null) {
            sendmsgs = new ArrayList<>();
        }
        sendmsgs.add(message);
    }

    public void addReceivemsgs(Message message) {
        if (receivemsgs == null) {
            receivemsgs = new ArrayList<>();
        }
        receivemsgs.add(message);
    }

    public boolean isFollow() {
        return isFollow;
    }

    public void setFollow(boolean follow) {
        isFollow = follow;
    }
}
