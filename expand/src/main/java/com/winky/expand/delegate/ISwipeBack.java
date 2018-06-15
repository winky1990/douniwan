package com.winky.expand.delegate;


import com.winky.expand.view.layout.swipe.SwipeBackLayout;

public interface ISwipeBack {

    /**
     * @return the SwipeBackLayout associated with this activity.
     */
    public abstract SwipeBackLayout getSwipeBackLayout();

    public abstract void setSwipeBackEnable(boolean enable);

    /**
     * Scroll out contentView and finish the activity
     */
    public abstract void scrollToFinishActivity();
}
