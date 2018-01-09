package com.example.chinlee.scrolltest;


import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.ViewConfiguration;

public class ScrollLayout3 extends ScrollLayout2 {
    private static final String TAG = "ScrollLayout3";

    private static final int STATE_IDLE = 0;
    private static final int STATE_DRAGGING = 1;
    private static final int MIN_VELOCITY = 600;

    private VelocityTracker velocityTracker;
    private int touchSlop = ViewConfiguration.get(getContext()).getScaledTouchSlop();
    private int touchState = STATE_IDLE;
    private float lastMotionX;

    public ScrollLayout3(Context context) {
        this(context, null);
    }

    public ScrollLayout3(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ScrollLayout3(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        final int action = ev.getAction();
        final float motionX = ev.getX();

        switch (action) {
            case MotionEvent.ACTION_DOWN:
                Log.d(TAG, "onInterceptTouchEvent: ACTION_DOWN");
                lastMotionX = motionX;
                touchState = scroller.isFinished() ? STATE_IDLE : STATE_DRAGGING;
                if (velocityTracker != null) {
                    velocityTracker.recycle();
                    velocityTracker = null;
                }
                velocityTracker = VelocityTracker.obtain();
                break;
            case MotionEvent.ACTION_MOVE:
                Log.d(TAG, "onInterceptTouchEvent: ACTION_MOVE");
                final int deltaX = (int)Math.abs(ev.getX() - lastMotionX);
                if (deltaX > touchSlop) {
                    touchState = STATE_DRAGGING;
                }
                break;
            case MotionEvent.ACTION_CANCEL:
            case MotionEvent.ACTION_UP:
                Log.d(TAG, "onInterceptTouchEvent: ACTION_UP");
                touchState = STATE_IDLE;
                if (velocityTracker != null) {
                    velocityTracker.recycle();
                    velocityTracker = null;
                }
                break;
        }

        return (touchState != STATE_IDLE);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        final int action = event.getAction();
        final float motionX = event.getX();

        velocityTracker.addMovement(event);

        switch (action) {
            case MotionEvent.ACTION_DOWN:
                Log.d(TAG, "onTouchEvent: ACTION_DOWN");
                if (!scroller.isFinished()) {
                    scroller.abortAnimation();
                }
                lastMotionX = motionX;
                break;
            case MotionEvent.ACTION_MOVE:
                Log.d(TAG, "onTouchEvent: ACTION_MOVE");
                int deltaX = (int)(motionX - lastMotionX);
                int scrollX = getScrollX() - deltaX;
                if (scrollX >= 0 && scrollX <= getWidth() * (CHILD_VIEW_COLORS.length - 1)) {
                    scrollTo(scrollX, 0);
                }
                lastMotionX = motionX;
                break;
            case MotionEvent.ACTION_UP:
                Log.d(TAG, "onTouchEvent: ACTION_UP");
                velocityTracker.computeCurrentVelocity(1000);
                float velocityX = velocityTracker.getXVelocity();
                if (velocityX > MIN_VELOCITY && isItemValid(getCurrentItem() - 1)) {
                    setCurrentItem(getCurrentItem() - 1);
                } else if (velocityX < -MIN_VELOCITY && isItemValid(getCurrentItem() + 1)) {
                    setCurrentItem(getCurrentItem() + 1);
                } else {
                    int index = (getScrollX() + getWidth() / 2) / getWidth();
                    setCurrentItem(index);
                }
                velocityTracker.recycle();
                velocityTracker = null;
                touchState = STATE_IDLE;
                break;
            case MotionEvent.ACTION_CANCEL:
                Log.d(TAG, "onTouchEvent: ACTION_CANCEL");
                touchState = STATE_IDLE;
                break;
        }
        return true;
    }
}
