/**
 * Author: 何慷、叶俊豪
 * Create Time: 2020/7/12
 * Update Time: 2020/7/24
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
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CommonUtil {

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

    public static List<Map<String,String>> addressResolution(String address) {
        String regex="(?<province>[^省]+自治区|.*?省|.*?行政区|.*?市)(?<city>[^市]+自治州|.*?地区|.*?行政单位|.+盟|市辖区|.*?市|.*?县)(?<county>[^县]+县|.+区|.+市|.+旗|.+海域|.+岛)?(?<town>[^区]+区|.+镇)?(?<village>.*)";
        Matcher m=Pattern.compile(regex).matcher(address);
        String province=null,city=null,county=null,town=null,village=null;
        List<Map<String,String>> table=new ArrayList<>();
        Map<String,String> row=null;
        while(m.find()) {
            row=new LinkedHashMap<String,String>();
            province=m.group("province");
            row.put("province", province==null?"":province.trim());
            city=m.group("city");
            row.put("city", city==null?"":city.trim());
            county=m.group("county");
            row.put("county", county==null?"":county.trim());
            town=m.group("town");
            row.put("town", town==null?"":town.trim());
            village=m.group("village");
            row.put("village", village==null?"":village.trim());
            table.add(row);
        }
        return table;
    }
}