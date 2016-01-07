package com.huangzj.side.example;

import android.os.Bundle;

import com.huangzj.side.R;
import com.huangzj.slidingmenu.SlidingMenu;
import com.huangzj.slidingmenu.app.SlidingActivity;

/**
 * Created by huangzj on 2016/1/7.
 */
public class SecondActivity extends SlidingActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_center);

        setBehindContentView(R.layout.leftmenu);
        // configure the SlidingMenu
        SlidingMenu menu = getSlidingMenu();
        menu.setMode(SlidingMenu.LEFT);
        // 设置触摸屏幕的模式
        menu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
        menu.setShadowWidthRes(R.dimen.shadow_width);
        menu.setShadowDrawable(R.drawable.shadow);

        // 设置滑动菜单视图的宽度
        menu.setBehindOffsetRes(R.dimen.slidingmenu_offset);
        // 设置渐入渐出效果的值
        menu.setFadeDegree(0.35f);
        /**
         * SLIDING_WINDOW will include the Title/ActionBar in the content
         * section of the SlidingMenu, while SLIDING_CONTENT does not.
         */
        // menu.attachToActivity(this, SlidingMenu.SLIDING_CONTENT);
        // menu.setMenu(R.layout.leftmenu);
    }
}
