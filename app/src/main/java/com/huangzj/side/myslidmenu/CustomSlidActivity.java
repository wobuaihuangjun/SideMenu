package com.huangzj.side.myslidmenu;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Toast;

import com.huangzj.side.R;

public class CustomSlidActivity extends Activity {

    SideMenu sideMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_slid);

        sideMenu = (SideMenu) findViewById(R.id.home_side_menu);
    }

    @Override
    public void onBackPressed() {
        if (sideMenu.isLeftMenuOpen()) {
            Toast.makeText(this, "左侧菜单已经打开", Toast.LENGTH_LONG).show();
        } else if (sideMenu.isRightMenuOpen()) {
            Toast.makeText(this, "右侧菜单已经打开", Toast.LENGTH_LONG).show();
        } else {
            super.onBackPressed();
        }
        sideMenu.closeMenu();
    }
}
