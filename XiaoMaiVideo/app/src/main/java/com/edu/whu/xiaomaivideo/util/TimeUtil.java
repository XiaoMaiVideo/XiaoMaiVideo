/**
 * author: 何慷
 * createTime：2020/7/17
 */

/**
 * 评论区时间获取
 */
package com.edu.whu.xiaomaivideo.util;

import java.util.Locale;

public class TimeUtil {

    public static String getRecentTimeSpanByNow(final long millis) {
        long now = System.currentTimeMillis();
        long span = now - millis;
        if (span < 1000) {
            return "刚刚";
        } else if (span < Constant.MIN) {
            return String.format(Locale.getDefault(), "%d秒前", span / Constant.SEC);
        } else if (span < Constant.HOUR) {
            return String.format(Locale.getDefault(), "%d分钟前", span / Constant.MIN);
        } else if (span < Constant.DAY) {
            return String.format(Locale.getDefault(), "%d小时前", span / Constant.HOUR);
        } else if (span < Constant.MONTH) {
            return String.format(Locale.getDefault(), "%d天前", span / Constant.DAY);
        } else if (span < Constant.YEAR) {
            return String.format(Locale.getDefault(), "%d月前", span / Constant.MONTH);
        } else if (span > Constant.YEAR) {
            return String.format(Locale.getDefault(), "%d年前", span / Constant.YEAR);
        } else {
            return String.format("%tF", millis);
        }
    }


}
