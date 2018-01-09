package com.example.chinlee.scrolltest;


import android.content.Context;
import android.util.AttributeSet;
import android.widget.Scroller;

public class ScrollLayout2 extends ScrollLayout1 {

    protected Scroller scroller;

    public ScrollLayout2(Context context) {
        this(context, null);
    }

    public ScrollLayout2(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ScrollLayout2(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.scroller = new Scroller(getContext());
    }

    @Override
    public void setCurrentItem(int index) {
        if (index >= 0 && index < CHILD_VIEW_COLORS.length) {
            scroller.startScroll(getScrollX(), getScrollY(),
                    getWidth() * index - getScrollX(), getScrollY());
            currentItem = index;
            invalidate();
        }
    }

    public void stopScroll() {
        if (!scroller.isFinished()) {
            int currentX = scroller.getCurrX();
            int targetIndex = (currentX + getWidth() / 2) / getWidth();
            scroller.abortAnimation();
            scrollTo(getWidth() * targetIndex, 0);
            currentItem = targetIndex;
        }
    }

    @Override
    public void computeScroll() {
        if (scroller.computeScrollOffset()) {
            scrollTo(scroller.getCurrX(), scroller.getCurrY());
            postInvalidate();
        }
    }
}
