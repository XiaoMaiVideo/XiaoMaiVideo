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
        if (!messageVos.containsKey(type)) {
            messageVos.put(type, new ArrayList<>());
        }
        messageVos.get(type).add(messageVO);
    }

    public static void clear(String type) {
        if (!(type.equals("like")||type.equals("comment")||type.equals("follow"))) {
            return;
        }
        if (!messageVos.containsKey(type)) {
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
        if (!messageVos.containsKey(type)) {
            return new ArrayList<>();
        }
        return messageVos.get(type);
    }
}
