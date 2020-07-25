/**
 * Author: 叶俊豪、张俊杰、何慷
 * Create Time: 2020/7/8
 * Update Time: 2020/7/25
 */

package com.edu.whu.xiaomaivideo.util;

import androidx.annotation.IntDef;
import androidx.lifecycle.MutableLiveData;

import com.edu.whu.xiaomaivideo.model.User;

import org.greenrobot.eventbus.EventBus;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public class Constant {
    // 云服务器URL
    public static final String BASEURL = "http://139.224.133.166:8088/rest/";
    // WebSocket URL
    public static final String WS_URL = "ws://139.224.133.166:8088/ws/";
    public static final String RESPONSE_ERROR = "Response Error!";

    public static User currentUser = User.Visitor(); // 尚未登录

    public static void setCurrentUser(User user) {
        currentUser = user;
        EventBus.getDefault().post(new EventBusMessage(Constant.UPDATE_USER, ""));
    }

    public static Long currentChattingId = (long) -1; // 当前正在聊天的用户ID，-1表示不在聊天
    public static String currentChattingName = ""; // 当前正在聊天的用户昵称，""表示不在聊天

    public static final int RESULT_SUCCESS = 200;
    public static final int RESULT_FAILURE = 400;

    public static final int USER_NOT_EXISTS = 1;
    public static final int PASSWORD_WRONG = 2;
    public static final int LOGIN_SUCCESS_RESULT = 3;
    public static final int USER_ALREADY_EXISTS = 4;

    public static final int SET_USER_INFO = 5;
    public static final int TAKE_VIDEO = 6;
    public static final int SHARE_OUTSIDE = 7;

    public static final String PAGE_LIMIT = "10";
    public static final String[] LABELS = {"音乐","影视","社会","游戏","美食","儿童","生活","体育","文化","时尚","科技"};
    public static final String SET_WEBSOCKET = "Set WebSocket";
    public static final String SEND_MESSAGE = "Send WebSocket Message";
    public static final String RECEIVE_MESSAGE = "Receive WebSocket Message";
    public static final String UPDATE_MESSAGE_LIST = "Update Message List";
    public static final String UPDATE_USER = "Update User";
    public static final String RECOMMEND = "Recommend";
    public static final String LOCAL_HOT = "Local Hot";

    public static boolean isLocationPermissionGiven = true;
    public static boolean isCameraPermissionGiven = true;
    public static boolean isStoragePermissionGiven = true;
    public static MutableLiveData<String> currentLocation = new MutableLiveData<>("");
    public static MutableLiveData<Integer> currentLikeMessage = new MutableLiveData<>(0);
    public static MutableLiveData<Integer> currentCommentMessage = new MutableLiveData<>(0);
    public static MutableLiveData<Integer> currentFollowMessage = new MutableLiveData<>(0);

    public static final int MSEC = 1;
    public static final int SEC  = 1000;
    public static final int MIN  = 60000;
    public static final int HOUR = 3600000;
    public static final int DAY  = 86400000;
    public static final long MONTH  = 2592000000L;
    public static final long YEAR  = 31104000000L;

    @IntDef({MSEC, SEC, MIN, HOUR, DAY})
    @Retention(RetentionPolicy.SOURCE)
    public @interface Unit {
    }

}
