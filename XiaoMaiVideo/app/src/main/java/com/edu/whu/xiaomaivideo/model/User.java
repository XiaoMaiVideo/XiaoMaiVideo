/**
 * Author: 张俊杰、叶俊豪
 * Create Time: 2020/7/8
 * Update Time: 2020/7/12
 */

package com.edu.whu.xiaomaivideo.model;

import org.parceler.Parcel;

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
}
