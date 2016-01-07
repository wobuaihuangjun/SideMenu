package com.huangzj.side.example;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.huangzj.side.R;

/**
 * Created by huangzj on 2016/1/7.
 */
public class MenuRightFragment extends Fragment {
    private View mView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (mView == null) {
            mView = inflater.inflate(R.layout.right_menu, container, false);
        }
        return mView;
    }
}