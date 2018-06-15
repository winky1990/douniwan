package com.winky.expand.basics;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.winky.expand.R;
import com.winky.expand.delegate.ISwipeBack;
import com.winky.expand.view.layout.swipe.SwipeBackLayout;

public abstract class BaseSwipeBackFragment extends BaseSkinFragment implements ISwipeBack {

    private SwipeBackLayout swipeBackLayout;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        swipeBackLayout = new SwipeBackLayout(getActivity());
        swipeBackLayout = (SwipeBackLayout) View.inflate(getActivity(), R.layout.view_swipeback, container);
        View view = inflater.inflate(bindLayout(), null, false);
        swipeBackLayout.addView(view);
        swipeBackLayout.attachToActivity(getActivity());
        return swipeBackLayout;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public SwipeBackLayout getSwipeBackLayout() {
        return swipeBackLayout;
    }

    @Override
    public void setSwipeBackEnable(boolean enable) {
        swipeBackLayout.setEnableGesture(enable);
    }

    @Override
    public void scrollToFinishActivity() {
        swipeBackLayout.scrollToFinishActivity();
    }
}
