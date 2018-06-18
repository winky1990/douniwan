package com.winky.expand.basics;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.winky.douniwan.tools.NavigationUtils;
import com.winky.expand.R;
import com.winky.expand.view.layout.swipe.ISwipeBack;
import com.winky.expand.view.layout.swipe.SwipeBackLayout;

public abstract class BaseSwipeBackFragment extends BaseSkinFragment implements ISwipeBack {

    private SwipeBackLayout swipeBackLayout;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        swipeBackLayout = (SwipeBackLayout) inflater.inflate(R.layout.view_swipeback, container, false);
        View view = inflater.inflate(bindLayout(), null, false);
        swipeBackLayout.attachToSwipe(view);
        swipeBackLayout.addSwipeListener(this);
        return swipeBackLayout;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void setSwipeBackEnable(boolean enable) {
        swipeBackLayout.setEnableGesture(enable);
    }

    @Override
    public void scrollToFinishActivity() {
        swipeBackLayout.scrollToFinishActivity();
    }

    @Override
    public void onScrollStateChange(int state, float scrollPercent) {

    }

    @Override
    public void onEdgeTouch(int edgeFlag) {

    }

    @Override
    public void onScrollOverThreshold() {
        NavigationUtils.getInstance().navigateUp(getView());
    }
}
