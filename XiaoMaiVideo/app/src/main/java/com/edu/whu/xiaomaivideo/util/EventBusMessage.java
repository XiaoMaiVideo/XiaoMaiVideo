/**
 * Author: 叶俊豪
 * Create Time: 2020/7/16
 * Update Time: 2020/7/16
 */

package com.edu.whu.xiaomaivideo.util;

import com.alibaba.fastjson.JSON;

// 只用于EventBus在Service和Activity/Fragment之间传输Websocket信息
public class EventBusMessage {
    private String type; // 接收消息（Service传给Activity/Fragment），还是发送消息（反过来）
    private String message; // 表示消息的json串，在Activity/Fragment中进行处理

    public EventBusMessage(String type, String message) {
        this.type = type;
        this.message = message;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
