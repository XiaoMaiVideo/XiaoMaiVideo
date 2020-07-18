/**
 * Author: 何慷、叶俊豪
 * Create Time: 2020/7/12
 * Update Time: 2020/7/17
 */

package com.edu.whu.xiaomaivideo.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.text.Layout;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.edu.whu.xiaomaivideo.R;
import com.edu.whu.xiaomaivideo.model.User;
import com.edu.whu.xiaomaivideo.others.BaseApplication;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CommonUtils {

    /**
     * 判断是否为电话号码的正则表达式
     *
     * @param phoneNumber
     * @return
     */
    public static boolean isPhoneNumberValid(String phoneNumber) {
        boolean isValid = false;
        /**
         * 可接受的电话格式：
         */
        String expression = "^((13[0-9])|(15[^4,\\D])|(18[0-9]))\\d{8}$";

        CharSequence inputStr = phoneNumber;
        Pattern pattern = Pattern.compile(expression);
        Matcher matcher = pattern.matcher(inputStr);

        if (matcher.matches()) {
            isValid = true;
        }
        return isValid;
    }

    /**
     * 将文本转换成Bitmap
     */
    public static Bitmap textAsBitmap(String text, float textSize) {

        TextPaint textPaint = new TextPaint();
        // textPaint.setARGB(0x31, 0x31, 0x31, 0);
        textPaint.setColor(Color.WHITE);

        textPaint.setTextSize(textSize);

        StaticLayout layout = new StaticLayout(text, textPaint, getTextWidth(textPaint,text),
                Layout.Alignment.ALIGN_NORMAL, 1.3f, 0.0f, true);
        Bitmap bitmap = Bitmap.createBitmap(layout.getWidth() + 20,
                layout.getHeight() + 20, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        canvas.translate(10, 10);
//        canvas.drawColor(0xFF31306B);
        canvas.drawColor(0x00000000);

        layout.draw(canvas);
        return bitmap;
    }

    public static int getTextWidth(Paint paint, String str) {
        int iRet = 0;
        if (str != null && str.length() > 0) {
            int len = str.length();
            float[] widths = new float[len];
            paint.getTextWidths(str, widths);
            for (int j = 0; j < len; j++) {
                iRet += (int) Math.ceil(widths[j]);
            }
        }
        return iRet;
    }


    /**
     * 计算分享内容的字数，一个汉字=两个英文字母，一个中文标点=两个英文标点
     * 注意：该函数的不适用于对单个字符进行计算，因为单个字符四舍五入后都是1
     *
     * @param c
     * @return
     */
    public static long calculateLength(CharSequence c) {
        double len = 0;
        for (int i = 0; i < c.length(); i++) {
            int tmp = (int) c.charAt(i);
            if (tmp > 0 && tmp < 127) {
                len += 0.5;
            } else {
                len++;
            }
        }
        return Math.round(len);
    }

    public static String convertTimeToDateString(long milliSeconds) {
        DateFormat format = SimpleDateFormat.getDateTimeInstance();
        return format.format(new Date(milliSeconds));
    }

    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f) - 15;
    }

    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    public static void loadToImageView(String url, ImageView imageView) {
        if (url.endsWith("gif")) {
            loadToImageViewStaticGif(url, imageView);
        } else {
            Glide.with(BaseApplication.getContext())
                    .load(url)
                    .placeholder(R.drawable.loading)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .centerCrop()
                    .into(imageView);
        }
    }

    public static void loadToImageViewFitCenter(String url, ImageView imageView) {
        if (url.endsWith("gif")) {
            loadToImageViewStaticGif(url, imageView);
        } else {
            Glide.with(BaseApplication.getContext())
                    .load(url)
                    .into(imageView);
        }
    }

    public static void loadToImageViewZoomIn(String url, ImageView imageView) {
        if (url.endsWith("gif")) {
            loadToImageViewZoomInGif(url, imageView);
        } else {
            Glide.with(BaseApplication.getContext())
                    .load(url)
                    .fitCenter()
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(imageView);
        }
    }

    public static void loadToImageViewZoomInGif(String url, ImageView imageView) {
        Glide.with(BaseApplication.getContext())
                .load(url)
                //      .asGif()
                .fitCenter()
                .into(imageView);
    }

    public static void loadToImageViewStaticGif(String url, ImageView imageView) {
        Glide.with(BaseApplication.getContext())
                .load(url)
                //  .asBitmap()
                .centerCrop()
                .into(imageView);
    }


    public static void saveBitmap(String bitName, Bitmap mBitmap) {
        File f = new File("/sdcard/Pictures/" + bitName+".png");
        try {
            f.createNewFile();
        } catch (IOException e) {

        }
        FileOutputStream fOut = null;
        try {
            fOut = new FileOutputStream(f);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        mBitmap.compress(Bitmap.CompressFormat.PNG, 100, fOut);
        try {
            fOut.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            fOut.close();
            Toast.makeText(BaseApplication.getContext(),"保存完成",Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(BaseApplication.getContext(),"保存失败",Toast.LENGTH_SHORT).show();
        }
    }

    // 判断用户user是否被当前登录用户（CurrentUser）关注
    public static boolean isUserFollowedByCurrentUser(User user) {
        if (user.getUserId() == Constant.CurrentUser.getUserId()) {
            return false;
        }
        // 用户粉丝为0，不被当前用户关注
        if (user.getFollowers() == null) {
            return false;
        }
        // 看关注列表里面有没有这个user
        for (User following: Constant.CurrentUser.getFollowing()) {
            if (following.getUserId() == user.getUserId()) {
                return true;
            }
        }
        return false;
    }
}