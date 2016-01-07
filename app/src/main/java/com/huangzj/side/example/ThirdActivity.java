package com.huangzj.side.example;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import com.huangzj.side.R;
import com.huangzj.slidingmenu.SlidingMenu;

/**
 * Created by huangzj on 2016/1/7.
 */
public class ThirdActivity extends Activity {

    private SlidingMenu mLeftMenu;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_third);

        mLeftMenu = (SlidingMenu) findViewById(R.id.slidingmenulayout);
        // configure the SlidingMenu
        // SlidingMenu menu = new SlidingMenu(this);
        mLeftMenu.setMode(SlidingMenu.LEFT);
        // 设置触摸屏幕的模式
        mLeftMenu.setShadowWidthRes(R.dimen.shadow_width);
        mLeftMenu.setShadowDrawable(R.drawable.shadow);

        mLeftMenu.setMenu(R.layout.leftmenu);

        mLeftMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mLeftMenu.isMenuShowing())
                    mLeftMenu.toggle();
            }
        });
        // 设置滑动菜单视图的宽度
        // 设置渐入渐出效果的值
        /**
         * SLIDING_WINDOW will include the Title/ActionBar in the content
         * section of the SlidingMenu, while SLIDING_CONTENT does not.
         */
    }
}
