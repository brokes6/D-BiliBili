package com.example.dildil.util;

import android.content.Context;
import android.graphics.Paint;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.ArcShape;
import android.graphics.drawable.shapes.RoundRectShape;
import android.view.View;
import android.view.WindowManager;

/**
 * Created by Jue on 2016/3/29.
 * 常用小工具类
 */
public class NavigationUtil {

    /**
     * 获取屏幕宽度
     */
    public static int getScreenWidth(Context context) {
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        int width = wm.getDefaultDisplay().getWidth();
        return width;
    }


    /**
     * 获取屏幕高度
     */
    public static int getScreenHeith(Context context) {
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        int height = wm.getDefaultDisplay().getHeight();
        return height;
    }

    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
     */
    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }


    /**
     * 将px值转换为sp值，保证文字大小不变
     *
     * @param pxValue （DisplayMetrics类中属性scaledDensity）
     * @return
     */
    public static int px2sp(Context context, float pxValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (pxValue / fontScale + 0.5f);
    }

    /**
     * 将sp值转换为px值，保证文字大小不变
     *
     * @param spValue （DisplayMetrics类中属性scaledDensity）
     * @return
     */
    public static int sp2px(Context context, float spValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }


    public static int px2ts(Context context, int isDp, float px) {
        if (isDp == 1) {
            return px2dip(context, px);
        } else {
            return px2sp(context, px);
        }
    }

    public static void setRoundRectBg(Context mContext, View view, int dipRadius, int badgeColor) {
        int radius = dip2px(mContext, dipRadius);
        float[] radiusArray = new float[]{radius, radius, radius, radius, radius, radius, radius, radius};
        RoundRectShape roundRect = new RoundRectShape(radiusArray, null, null);
        ShapeDrawable bgDrawable = new ShapeDrawable(roundRect);
        bgDrawable.getPaint().setColor(badgeColor);
        view.setBackgroundDrawable(bgDrawable);
    }

    public static void setOvalBg(View view, int bgColor) {
        ArcShape shape = new ArcShape(0, 360);
        ShapeDrawable drawable = new ShapeDrawable(shape);
        drawable.getPaint().setColor(bgColor);
        drawable.getPaint().setAntiAlias(true);
        drawable.getPaint().setStyle(Paint.Style.FILL);
        view.setBackgroundDrawable(drawable);
    }


    public static float compareTo(Context context,float dimension, float tabTextSize,int textSizeType) {
        if(dimension==0){
            return tabTextSize;
        }else{
            return px2ts(context,textSizeType,tabTextSize);
        }
    }
}