/**
 * Author: 叶俊豪 张俊杰
 * Create Time: 2020/7/8
 * Update Time: 2020/7/15
 */

package com.edu.whu.xiaomaivideo.util;

import com.edu.whu.xiaomaivideo.model.User;

public class Constant {
    // 云服务器URL
    public static final String BASEURL = "http://139.224.133.166:8088/rest/";
    // WebSocket URL
    public static final String WS_URL = "ws://139.224.133.166:8088/ws/";
    public static final String RESPONSE_ERROR = "Response Error!";

    public static User CurrentUser = User.Visitor(); // 尚未登录

    public static final int RESULT_SUCCESS = 200;
    public static final int RESULT_FAILURE = 400;

    public static final int USER_NOT_EXISTS = 1;
    public static final int PASSWORD_WRONG = 2;
    public static final int LOGIN_SUCCESS_RESULT = 3;
    public static final int USER_ALREADY_EXISTS = 4;

    public static final int SET_USER_INFO = 5;
    public static final int TAKE_VIDEO = 6;

    public static final String[] LABELS = {"音乐","影视","社会","游戏","美食","儿童","生活","体育","文化","时尚","科技"};


}
