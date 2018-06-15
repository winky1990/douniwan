package com.winky.expand.view.layout;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.FrameLayout;

import com.winky.expand.view.layout.auto.AutoUtils;

/**
 * 自适应framelayout
 *
 * @author winky
 * @date 2018/5/26
 */
public class AutoFrameLayout extends FrameLayout {

    public AutoFrameLayout(Context context) {
        super(context);
    }

    public AutoFrameLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public AutoFrameLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public LayoutParams generateLayoutParams(AttributeSet attrs) {
        AutoUtils.getInstance().analysisParams(this, getContext(), attrs);
        return new LayoutParams(getContext(), attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        if (!isInEditMode()) {
            AutoUtils.getInstance().setLayoutAttrs(this);
        }
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
    }
}
