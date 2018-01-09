package com.example.chinlee.scrolltest;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


public class ScrollLayout1 extends ViewGroup {

    protected static final int CHILD_VIEW_COLORS[] = {
            Color.parseColor("#FFCC6666"),
            Color.parseColor("#FFCCCC66"),
            Color.parseColor("#FF6666CC")
    };

    protected int currentItem;

    public ScrollLayout1(Context context) {
        this(context, null);
    }

    public ScrollLayout1(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ScrollLayout1(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        for (int i = 0; i < CHILD_VIEW_COLORS.length; i++) {
            TextView textView = new TextView(getContext());
            textView.setBackgroundColor(CHILD_VIEW_COLORS[i]);
            textView.setGravity(Gravity.CENTER);
            textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 46);
            textView.setTextColor(Color.parseColor("#80FFFFFF"));
            textView.setText(String.valueOf(i));
            textView.setClickable(true);
            addView(textView);
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec);

        for (int i = 0, childCount = getChildCount(); i < childCount; i++) {
            View childView = getChildAt(i);
            childView.measure(
                    MeasureSpec.makeMeasureSpec(width, MeasureSpec.EXACTLY),
                    MeasureSpec.makeMeasureSpec(height, MeasureSpec.EXACTLY));
        }

        setMeasuredDimension(width, height);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        for (int i = 0, childCount = getChildCount(); i < childCount; i++) {
            View childView = getChildAt(i);
            childView.layout(getWidth() * i, 0, getWidth() * (i + 1), b - t);
        }
    }

    public void setCurrentItem(int index) {
        if (isItemValid(index)) {
            scrollTo(index * getWidth(), getScrollY());
            currentItem = index;
            invalidate();
        }
    }

    public int getCurrentItem() {
        return currentItem;
    }

    public boolean isItemValid(int index) {
        return (index >= 0 && index < CHILD_VIEW_COLORS.length);
    }
}
