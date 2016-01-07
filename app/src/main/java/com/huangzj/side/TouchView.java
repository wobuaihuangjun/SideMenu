package com.huangzj.side;

import android.content.Context;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.GestureDetector.OnGestureListener;
import android.view.MotionEvent;
import android.widget.RelativeLayout;

public class TouchView extends RelativeLayout implements OnGestureListener {

	private static final String TAG="TouchView";
	
	/**
	 * 该组件手势是否禁用
	 */
	private boolean touchDisable;
	
	private SideMenu sideMenu;
	
	private GestureDetector detector;
	
	public TouchView(Context context) {
		this(context, null);
	}
	
	public TouchView(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}
	
	public TouchView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		touchDisable=false;
		detector=new GestureDetector(context, this);
	}

	@Override
	public boolean dispatchTouchEvent(MotionEvent ev) {
		return super.dispatchTouchEvent(ev);
	}
	
	@Override
	public boolean onInterceptTouchEvent(MotionEvent ev) {
		return touchDisable;
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		return detector.onTouchEvent(event);
	}
	
	public boolean isTouchDisable() {
		return touchDisable;
	}

	public void setTouchDisable(boolean touchDisable) {
		this.touchDisable = touchDisable;
	}

	public SideMenu getSideMenu() {
		return sideMenu;
	}

	public void setSideMenu(SideMenu sideMenu) {
		this.sideMenu = sideMenu;
	}

	@Override
	public boolean onDown(MotionEvent e) {
		return false;
	}

	@Override
	public void onShowPress(MotionEvent e) {
		
	}

	@Override
	public boolean onSingleTapUp(MotionEvent e) {
		return false;
	}

	@Override
	public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX,
							float distanceY) {
		return false;
	}

	@Override
	public void onLongPress(MotionEvent e) {
		
	}

	@Override
	public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
						   float velocityY) {
		return false;
	}
}
