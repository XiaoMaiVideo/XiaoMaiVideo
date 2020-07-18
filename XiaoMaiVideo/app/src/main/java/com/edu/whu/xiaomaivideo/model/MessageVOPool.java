package com.edu.whu.xiaomaivideo.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MessageVOPool {

    private static HashMap<String, List<MessageVO>> messageVos = new HashMap<>();

    public static void addMessageVO(String type, MessageVO messageVO) {
        if (!(type.equals("like")||type.equals("comment")||type.equals("follow"))) {
            return;
        }
        List<MessageVO> messageVOList = messageVos.get(type);
        if (messageVOList == null) {
            messageVOList = new ArrayList<>();
        }
        messageVOList.add(messageVO);
    }

    public static void clear(String type) {
        if (messageVos.get(type) == null) {
            return;
        }
        messageVos.get(type).clear();
    }

    public static void hasNew(String type) {

    }

    public static List<MessageVO> getMessageVOs(String type) {
        if (!(type.equals("like")||type.equals("comment")||type.equals("follow"))) {
            return null;
        }
        List<MessageVO> messageVOList = messageVos.get(type);
        if (messageVOList == null) {
            messageVOList = new ArrayList<>();
        }
        return messageVOList;
    }
}
