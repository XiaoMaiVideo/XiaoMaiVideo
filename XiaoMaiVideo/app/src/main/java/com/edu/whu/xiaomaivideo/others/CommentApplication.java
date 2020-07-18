/**
 * author: 何慷
 * createTime：2020/7/17
 */
package com.edu.whu.xiaomaivideo.others;

import android.app.Application;

public class CommentApplication extends Application {

    public static Application sApplication;

    public static Application getApplication() {
        return sApplication;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        sApplication = this;
    }
}
