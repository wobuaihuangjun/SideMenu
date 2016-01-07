package com.huangzj.side;

import android.app.Activity;
import android.content.Context;
import android.graphics.Rect;
import android.util.DisplayMetrics;

/**
 * 手机屏幕工具类
 * <p/>
 * Created by lhd on 2015/9/21.
 */
public class ScreenUtil {

    /**
     * 获取屏幕宽度(像素px)
     *
     * @return
     */
    public static int getPxWidth(Context context) {
        DisplayMetrics metric = new DisplayMetrics();
        metric = context.getResources().getDisplayMetrics();
        return metric.widthPixels;
    }

    /**
     * 获取屏幕高度(像素px)
     *
     * @return
     */
    public static int getPxHeight(Context context) {
        DisplayMetrics metric = new DisplayMetrics();
        metric = context.getResources().getDisplayMetrics();
        return metric.heightPixels;
    }

    /**
     * 获取状态栏高度(像素px)
     *
     * @param activity
     * @return
     */
    public static int getStatusBarPxHeight(Activity activity) {
        Rect localRect = new Rect();
        activity.getWindow().getDecorView().getWindowVisibleDisplayFrame(localRect);

        int height = localRect.top;
        if (height != 0) {
            return height;
        }

        try {
            Class<?> localClass = Class.forName("com.android.internal.R$dimen");
            Object localObject = localClass.newInstance();
            int i5 = Integer.parseInt(localClass.getField("status_bar_height")
                    .get(localObject)
                    .toString());

            height = activity.getResources().getDimensionPixelSize(i5);

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        return height;
    }
}
