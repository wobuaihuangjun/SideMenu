package com.huangzj.side.util;

import android.content.Context;
import android.util.DisplayMetrics;

/**
 * 手机屏幕工具类
 * <p/>
 * Created by huangzj on 2016/1/7.
 */
public class ScreenUtil {

    /**
     * 获取屏幕宽度(像素px)
     *
     * @return
     */
    public static int getPxWidth(Context context) {
        DisplayMetrics metric = context.getResources().getDisplayMetrics();
        return metric.widthPixels;
    }

    /**
     * 获取屏幕高度(像素px)
     *
     * @return
     */
    public static int getPxHeight(Context context) {
        DisplayMetrics metric = context.getResources().getDisplayMetrics();
        return metric.heightPixels;
    }

}
