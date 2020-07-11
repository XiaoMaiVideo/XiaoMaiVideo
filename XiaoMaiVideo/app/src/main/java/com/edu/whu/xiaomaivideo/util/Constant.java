package com.edu.whu.xiaomaivideo.util;

import com.edu.whu.xiaomaivideo.model.User;

public class Constant {
    // 云服务器URL
    public static final String BASEURL = "http://139.224.133.166:8088/rest/";
    public static final String RESPONSE_ERROR = "Response Error!";

    public static User CurrentUser = User.Visitor(); // 尚未登录

    public static final int RESULT_SUCCESS = 200;
    public static final int RESULT_FAILURE = 400;

    public static final int USER_NOT_EXISTS = 1;
    public static final int PASSWORD_WRONG = 2;
    public static final int LOGIN_SUCCESS_RESULT = 3;
    public static final int USER_ALREADY_EXISTS = 4;

    public static final int SETTING_ITEM_COUNT = 1;
}
