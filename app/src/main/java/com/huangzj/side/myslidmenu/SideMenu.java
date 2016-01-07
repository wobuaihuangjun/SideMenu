package com.huangzj.side.myslidmenu;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;

import com.huangzj.side.R;
import com.huangzj.side.util.ScreenUtil;


public class SideMenu extends HorizontalScrollView {

    /**
     * 屏幕宽度
     */
    private int mScreenWidth;

    /**
     * 屏幕高度
     */
    private int mScreenHeight;
    /**
     * dp
     */
    private int mMenuRightPadding;

    private int mMenuLeftPadding;

    /**
     * 菜单的宽度
     */
    private int mLeftMenuWidth;
    private int mRightMenuWidth;

    /**
     * 拖动基准线
     */
    private int mLeftX;
    private int mRightX;

    /**
     * 菜单是否打开
     */
    private boolean isLeftMenuOpen;
    private boolean isRightMenuOpen;

    /**
     * 初始化标志
     */
    private boolean init;

    private ViewGroup leftMenu;
    private ViewGroup middleContent;
    private ViewGroup rightMenu;

    /**
     * 中间布局阴影蒙版
     */
    private View contentAlphaLayout;

    /**
     * 保存滚动速度
     */
    private VelocityTracker mVelocityTracker;

    private int mMinimumFlingVelocity, mMaximumFlingVelocity;

    private int mTouchSlop;

    private MotionEvent mCurrentDownEvent;

    private CloseMenuListener closeMenuListener;

    private boolean scrollLeft = false, scrollRight = false;

    public SideMenu(Context context) {
        this(context, null, 0);
    }

    public SideMenu(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
        //this.setOverScrollMode(HorizontalScrollView.OVER_SCROLL_NEVER);
    }

    public SideMenu(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        final ViewConfiguration configuration = ViewConfiguration.get(context);
        mMinimumFlingVelocity = configuration.getScaledMinimumFlingVelocity();
        mMaximumFlingVelocity = configuration.getScaledMaximumFlingVelocity();
        mTouchSlop = configuration.getScaledTouchSlop();
        /*topBitmap=BitmapFactory.decodeResource(context.getResources(), R.drawable.land_night_shy1);
        topHeight=topBitmap.getHeight();
		topRectF=new RectF();*/
        mScreenWidth = ScreenUtil.getPxWidth(context);
        mScreenHeight = ScreenUtil.getPxHeight(context);
        TypedArray typedArray = context.getTheme().obtainStyledAttributes(attrs, R.styleable.SlidingMenu, defStyle, 0);
        int count = typedArray.getIndexCount();
        for (int i = 0; i < count; i++) {
            int attr = typedArray.getIndex(i);
            switch (attr) {
                case R.styleable.SlidingMenu_rightPadding:
                    // 默认50
                    mMenuRightPadding = typedArray.getDimensionPixelSize(attr,
                            (int) TypedValue.applyDimension(
                                    TypedValue.COMPLEX_UNIT_DIP, 50f,
                                    getResources().getDisplayMetrics()));// 默认为10dp
                    break;
                case R.styleable.SlidingMenu_leftPadding:
                    mMenuLeftPadding = typedArray.getDimensionPixelSize(attr,
                            (int) TypedValue.applyDimension(
                                    TypedValue.COMPLEX_UNIT_DIP, 50f,
                                    getResources().getDisplayMetrics()));
                    break;
            }
        }
        typedArray.recycle();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        /*topRectF.set(0, 0, mScreenWidth, topHeight);
        canvas.drawBitmap(topBitmap, null, topRectF, paint);
		topRectF.set(mScreenWidth, 0, 2*mScreenWidth, topHeight);
		canvas.drawBitmap(topBitmap, null, topRectF, paint);
		topRectF.set(2*mScreenWidth, 0, 3*mScreenWidth, topHeight);
		canvas.drawBitmap(topBitmap, null, topRectF, paint);*/
    }

    /**
     * 计算界面相关属性，绑定相关控件
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        if (!init) {
            mLeftMenuWidth = mScreenWidth - mMenuLeftPadding;
            mRightMenuWidth = mScreenWidth - mMenuRightPadding;
            mLeftX = mLeftMenuWidth / 2;
            mRightX = (int) (mRightMenuWidth * 0.4 + mLeftMenuWidth);

            LinearLayout wrapper = (LinearLayout) getChildAt(0);
            leftMenu = (ViewGroup) wrapper.getChildAt(0);
            middleContent = (ViewGroup) wrapper.getChildAt(1);
            rightMenu = (ViewGroup) wrapper.getChildAt(2);

            leftMenu.getLayoutParams().width = mLeftMenuWidth;
            middleContent.getLayoutParams().width = mScreenWidth;
            rightMenu.getLayoutParams().width = mRightMenuWidth;

            //用于在画出左侧菜单时，主界面内容页逐渐变暗
            contentAlphaLayout = middleContent.findViewById(R.id.content_alph_layout);
            contentAlphaLayout.getLayoutParams().width = mScreenWidth;

//            rightMenuBtn = (RelativeLayout) middleContent.findViewById(R.id.homepage_right_menu_btn);
//            rightMenuButton = (RelativeLayout) rightMenu.findViewById(R.id.right_menu_button);
//            backImage = (ImageView) rightMenu.findViewById(R.id.navigate_back_btn);
        }
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        if (changed) {
            // 将菜单隐藏
            this.smoothScrollTo(mLeftMenuWidth, 0);
            init = true;
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        if (mVelocityTracker == null)
            mVelocityTracker = VelocityTracker.obtain();
        mVelocityTracker.addMovement(ev);
        int action = ev.getAction();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                if (mCurrentDownEvent == null)
                    mCurrentDownEvent = MotionEvent.obtain(ev);
                break;
            case MotionEvent.ACTION_MOVE:
                if (mCurrentDownEvent == null)
                    mCurrentDownEvent = MotionEvent.obtain(ev);
                break;
            case MotionEvent.ACTION_UP:
                VelocityTracker velocityTracker = mVelocityTracker;
                int pointerId = ev.getPointerId(0);
                velocityTracker.computeCurrentVelocity(500, mMaximumFlingVelocity);
                float velocityY = velocityTracker.getYVelocity(pointerId);
                float velocityX = velocityTracker.getXVelocity(pointerId);
                if ((Math.abs(velocityY) > mMinimumFlingVelocity)
                        || (Math.abs(velocityX) > mMinimumFlingVelocity)) {
                    onFling(mCurrentDownEvent, ev, velocityX, velocityY);
                } else {
                    if ((Math.abs(velocityY) <= mTouchSlop)
                            && (Math.abs(velocityX) <= mTouchSlop)) {
                        judgeNowState();
                        onClick(ev);
                    } else {
                        onScroll();
                    }
                }
                // 释放
                if (mCurrentDownEvent != null) {
                    mCurrentDownEvent.recycle();
                    mCurrentDownEvent = null;
                }
                if (mVelocityTracker != null) {
                    mVelocityTracker.recycle();
                    mVelocityTracker = null;
                }
                return true;
            default:
                break;
        }
        return super.onTouchEvent(ev);
    }

    public void onClick(MotionEvent e) {
        Rect r = new Rect();
        middleContent.getGlobalVisibleRect(r);
        // 点击中间布局
        if (r.contains((int) e.getRawX(), (int) e.getRawY())) {
            if (isLeftMenuOpen || isRightMenuOpen) {
                closeMenu();
            }
            if (scrollLeft) {
                closeMenu();
            }
        }
    }

    public void onScroll() {
        int scrollX = getScrollX();
        if (scrollX >= mLeftX && scrollX < mRightX) {
            closeMenu();
        } else if (scrollX >= mRightX) {
            openRightMenu();
        } else if (scrollX <= mLeftX) {
            openLeftMenu();
        } else {
            closeMenu();
        }
    }


    public void onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        if (velocityX > 0) {
            if (!isLeftMenuOpen && !isRightMenuOpen) {
                if (scrollRight) {
                    closeMenu();
                    return;
                }
                openLeftMenu();
            } else if (isRightMenuOpen) {
                closeMenu();
            } else {
                onScroll();
            }
        } else if (velocityX < 0) {
            if (!isLeftMenuOpen && !isRightMenuOpen) {
                if (scrollLeft) {
                    closeMenu();
                } else {
                    openRightMenu();
                }
            } else if (isLeftMenuOpen) {
                closeMenu();
            } else {
                onScroll();
            }
        } else {
            if (!isLeftMenuOpen && !isRightMenuOpen) {
                closeMenu();
            } else if (isLeftMenuOpen) {
                openLeftMenu();
            } else if (isRightMenuOpen) {
                openRightMenu();
            } else {
                closeMenu();
            }
        }
    }

    public void setCloseMenuListener(CloseMenuListener listener) {
        this.closeMenuListener = listener;
    }

    public boolean isRightMenuOpen() {
        return isRightMenuOpen;
    }

    public boolean isLeftMenuOpen() {
        return isLeftMenuOpen;
    }

    /**
     * 打开左侧菜单
     */
    public void openLeftMenu() {
        this.smoothScrollTo(0, 0);
        isLeftMenuOpen = true;
        adjustTouchView(true);
    }

    /**
     * 打开右侧菜单
     */
    public void openRightMenu() {
        this.smoothScrollTo(mLeftMenuWidth + mRightMenuWidth, 0);
        isRightMenuOpen = true;
        adjustTouchView(false);
    }

    /**
     * 拦截touch焦点
     *
     * @param ev
     * @return boolean
     */
    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        if (isLeftMenuOpen) {
            return false;
        }
        return super.onInterceptTouchEvent(ev);
    }

    /**
     * 分发touch事件
     *
     * @param ev
     * @return boolean
     */
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        return super.dispatchTouchEvent(ev);
    }

    public interface CloseMenuListener {
        void onCloseMenu();
    }

    /**
     * 关闭菜单
     */
    public void closeMenu() {
        this.smoothScrollTo(mLeftMenuWidth, 0);
        isLeftMenuOpen = false;
        isRightMenuOpen = false;
        adjustTouchView(false);
        if (closeMenuListener != null) {
            closeMenuListener.onCloseMenu();
        }
    }

    /**
     * 判断页面现况
     */
    private void judgeNowState() {
        if (getScrollX() == mLeftMenuWidth) {
            return;
        } else if (getScrollX() <= mLeftX) {
            openLeftMenu();
        } else if (getScrollX() >= mRightX) {
            openRightMenu();
        } else {
            closeMenu();
        }
    }

    /**
     * 调整touchView
     *
     * @param touchDisable
     */
    private void adjustTouchView(boolean touchDisable) {
        ((TouchView) middleContent).setSideMenu(this);
        ((TouchView) middleContent).setTouchDisable(touchDisable);
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);

        if (l < mLeftMenuWidth) {
            scrollLeft = true;
            scrollRight = false;
        } else if (l > mLeftMenuWidth) {
            scrollLeft = false;
            scrollRight = true;
        } else if (l == mLeftMenuWidth) {
            scrollLeft = false;
            scrollRight = false;
        }
        if (!scrollLeft && !scrollRight) {
            this.smoothScrollTo(mLeftMenuWidth, 0);
        }
        //左侧菜单滑动时，内容页逐渐变暗
        contentAlphaLayout.setAlpha(1 - (l * 1.0f / mLeftMenuWidth) * 0.5f - 0.5f);
    }
}
