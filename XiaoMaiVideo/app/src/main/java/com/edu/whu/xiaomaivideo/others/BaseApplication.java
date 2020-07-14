/**
 * Author: 何慷
 * Create Time: 2020/7/12
 * Update Time: 2020/7/12
 */

package com.edu.whu.xiaomaivideo.others;

import android.app.Application;
import android.content.Context;

public class BaseApplication extends Application {
    private static BaseApplication mApplication;

    @Override
    public void onCreate() {
        super.onCreate();
        this.mApplication = this;
    }

    public static Context getContext(){
        return mApplication.getApplicationContext();
    }
}
