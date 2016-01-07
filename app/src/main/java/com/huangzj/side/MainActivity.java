package com.huangzj.side;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.huangzj.side.example.FirstActivity;
import com.huangzj.side.example.FourActivity;
import com.huangzj.side.example.SecondActivity;
import com.huangzj.side.example.ThirdActivity;
import com.huangzj.side.myslidmenu.CustomSlidActivity;

/**
 * Created by huangzj on 2016/1/7.
 */
public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
    }

    public void onClick(View v) {
        startActivity(new Intent(this, CustomSlidActivity.class));
    }

    public void way1(View view) {
        startActivity(new Intent(this, FirstActivity.class));
    }

    public void way2(View view) {
        startActivity(new Intent(this, SecondActivity.class));
    }

    public void way3(View view) {
        startActivity(new Intent(this, ThirdActivity.class));
    }

    public void way4(View view) {
        startActivity(new Intent(this, FourActivity.class));
    }

}
