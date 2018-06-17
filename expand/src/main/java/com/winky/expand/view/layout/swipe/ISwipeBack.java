package com.winky.expand.view.layout.swipe;

public interface ISwipeBack extends SwipeBackLayout.OnDetachListener {


    void setSwipeBackEnable(boolean enable);

    /**
     * Scroll out contentView and finish the activity
     */
    void scrollToFinishActivity();
}
