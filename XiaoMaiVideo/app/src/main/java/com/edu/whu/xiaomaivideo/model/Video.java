/**
 * author:何慷
 * createTime:2020/7/13
 */

package com.edu.whu.xiaomaivideo.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;

/**
 * 写了一些相关的接口
 */
public class Video implements Parcelable {
    private String UserId;
    private String nickname;  //可选择显示用户昵称或者用户Id(一般显示用户昵称）
    private String url;  //服务器端的数据库中的url 应该是用json格式存储的
    private Date time;  //上传时间
    private String content;  //对视频的描述
    private Integer likeNumber;  //获赞数
    private Integer message;  //评论数
    private Integer shareNumber; //分享数

    public Video(String url) {
        this.url = url;
    }

    protected Video(Parcel in) {
        url = in.readString();
    }

    /**
     * CREATOR对象，必须提供实现
     * 接口对象，使用模板类Parcelable.Creator，套用到TaskInfo来得到的，Parcelable.Creator<TaskInfo>
     * 很大程度上是一个工厂（Factory）类，用于远程对象在接收端的创建
     * 从某种意义上来说，writeToParcel()与CREATOR是一一对应的，发送端进程通过writeToParcel()，
     * 使用一个Parcel对象将中间结果保存起来，而接收端进程则会使用CREATOR对象把作为Parcel对象的中间对象再恢复出来，
     * 通过类的初始化方法以这个Parcel对象为基础来创建新对象
     */
    public static final Creator<Video> CREATOR = new Creator<Video>() {

        /**
         *ParcelableCreator<T>模板类所必须实现的接口方法
         * 提供从Parcel转义出新的对象的能力
         * 接收端来接收传输过来的Parcel对象时，便会以这一个接口方法来取得对象
         * 直接调用基于Parcel的类的初始化方法，然后将创建的对象返回。
         * @param in
         * @return
         */
        @Override
        public Video createFromParcel(Parcel in) {
            return new Video(in);
        }

        @Override
        public Video[] newArray(int size) {
            return new Video[size];
        }
    };

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    /**
     * Parcelable所需要的接口方法之一，必须实现
     * 这一方法作用很简单，就是通过返回的整形来描述这一Parcel是起什么作用的，
     * 通过这一整形每个bit来描述其类型，一般会返回0。
     * @return
     */
    @Override
    public int describeContents() {
        return 0;
    }

    /**
     * Parcelable所需要的接口方法，必须实现。
     * 作用:发送，将类所需要传输的属性写到Parcel里，被用来提供发送功能的Parcel，会作为第一个参数传入
     * 于是在这个方法里都是使用writeInt()、writeLong()写入到Parcel里
     * @param dest
     * @param flags  可以用来指定这样的发送是单向还是双向的，可以与aidl的in、out、inout三种限定符匹配
     */
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(url);
    }


    public String getUserId() {
        return UserId;
    }

    public void setUserId(String userId) {
        UserId = userId;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getLikeNumber() {
        return likeNumber;
    }

    public void setLikeNumber(Integer likeNumber) {
        this.likeNumber = likeNumber;
    }

    public Integer getMessage() {
        return message;
    }

    public void setMessage(Integer message) {
        this.message = message;
    }

    public Integer getShareNumber() {
        return shareNumber;
    }

    public void setShareNumber(Integer shareNumber) {
        this.shareNumber = shareNumber;
    }

}



