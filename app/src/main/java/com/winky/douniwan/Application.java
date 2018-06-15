package com.winky.douniwan;

import android.os.Build;
import android.os.Looper;

import com.winky.expand.utils.LibControl;

public class Application extends android.app.Application {

    @Override
    public void onCreate() {
        super.onCreate();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (Looper.getMainLooper().isCurrentThread())
                LibControl.getInstance().init(this);
        } else {
            if (Looper.getMainLooper().getThread().getId() == Thread.currentThread().getId()) {
                LibControl.getInstance().init(this);
            }
        }
    }

    /**
     * 内存不足时
     */
    @Override
    public void onLowMemory() {
        super.onLowMemory();

    }
}
