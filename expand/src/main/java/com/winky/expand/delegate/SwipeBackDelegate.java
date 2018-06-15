package com.winky.expand.delegate;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import com.winky.expand.R;
import com.winky.expand.view.layout.swipe.SwipeBackLayout;


public class SwipeBackDelegate {

    private ISwipeBack swipeBack;
    private FragmentActivity activity;
    private SwipeBackLayout swipeBackLayout;

    public SwipeBackDelegate(ISwipeBack swipeBack) {
        this.swipeBack = swipeBack;
        if (!(swipeBack instanceof FragmentActivity)) {
            throw new RuntimeException("must exends FragmentActivity");
        }
        this.activity = (FragmentActivity) swipeBack;
    }

    public void onCreate(Bundle savedInstanceState) {
        activity.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        activity.getWindow().getDecorView().setBackground(null);
        swipeBackLayout = (SwipeBackLayout) activity.getLayoutInflater().inflate(R.layout.view_swipeback, null);
    }

    public void onPostCreate() {
        swipeBackLayout.attachToActivity(activity);
    }

    public SwipeBackLayout getSwipeBackLayout() {
        return swipeBackLayout;
    }

    public void setSwipeBackEnable(boolean enable) {
        swipeBackLayout.setEnableGesture(enable);
    }

    public void scrollToFinishActivity() {
        swipeBackLayout.scrollToFinishActivity();
    }
}
