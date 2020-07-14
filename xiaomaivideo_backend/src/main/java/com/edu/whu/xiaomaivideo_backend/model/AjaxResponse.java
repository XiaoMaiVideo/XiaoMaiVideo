/**
 * Author: 张俊杰、叶俊豪
 * Create Time: 2020/7/8
 * Update Time: 2020/7/10
 */


package com.edu.whu.xiaomaivideo_backend.model;


public class AjaxResponse {
    private boolean isok;
    private int code;   
    private String message;
    private Object data;

    public boolean isIsok() {
        return isok;
    }

    public void setIsok(boolean isok) {
        this.isok = isok;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    private AjaxResponse() {

    }

    public AjaxResponse(int code, String message, boolean isOk) {
        this.code = code;
        this.message = message;
        this.isok = isOk;
    }

    public static AjaxResponse success() {
        AjaxResponse resultBean = new AjaxResponse();
        resultBean.setIsok(true);
        resultBean.setCode(200);
        resultBean.setMessage("success");
        return resultBean;
    }

    public static AjaxResponse success(Object data) {
        AjaxResponse resultBean = new AjaxResponse();
        resultBean.setIsok(true);
        resultBean.setCode(200);
        resultBean.setMessage("success");
        resultBean.setData(data);
        return resultBean;
    }

    public static AjaxResponse failure() {
        AjaxResponse resultBean = new AjaxResponse();
        resultBean.setIsok(false);
        resultBean.setCode(400);
        resultBean.setMessage("failure");
        return resultBean;
    }

}