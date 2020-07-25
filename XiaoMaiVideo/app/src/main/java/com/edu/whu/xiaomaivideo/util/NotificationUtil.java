/**
 * Author: 叶俊豪
 * Create Time: 2020/7/18
 * Update Time: 2020/7/18
 */
package com.edu.whu.xiaomaivideo.util;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Build;
import android.util.Log;

import com.edu.whu.xiaomaivideo.R;

import static android.content.Context.NOTIFICATION_SERVICE;

public class NotificationUtil {
    public static void pushNotification(Context context, String title, String text) {
        String id = "channel_001";
        String name = "name";
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(NOTIFICATION_SERVICE);
        Notification notification = null;
        NotificationChannel mChannel = new NotificationChannel(id, name, NotificationManager.IMPORTANCE_HIGH);
        notificationManager.createNotificationChannel(mChannel);
        notification = new Notification.Builder(context)
                .setChannelId(id)
                .setContentTitle(title)
                .setContentText(text)
                .setSmallIcon(R.drawable.app)
                .setAutoCancel(true)
                .build();
        notificationManager.notify(1, notification);
        Log.e("JWebSocketService", "推送完成");
    }
}
