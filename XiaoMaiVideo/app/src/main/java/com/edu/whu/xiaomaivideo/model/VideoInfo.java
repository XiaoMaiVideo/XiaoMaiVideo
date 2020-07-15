/**
 * author:何慷
 * createTime：2020/7/13
 */
package com.edu.whu.xiaomaivideo.model;

import java.util.List;

public class VideoInfo {

    private List<Video> videoList;

    public VideoInfo(List<Video> videoList) {
        this.videoList = videoList;
    }

    public List<Video> getVideoList() {
        return videoList;
    }

    public void setVideoList(List<Video> videoList) {
        this.videoList = videoList;
    }
}
