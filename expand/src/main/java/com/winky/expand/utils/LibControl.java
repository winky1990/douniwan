package com.winky.expand.utils;

import android.app.Application;
import android.content.Context;

import com.winky.expand.db.RealmUtils;
import com.winky.expand.skin.SkinManager;

public class LibControl {

    private static final Singleton<LibControl> SINGLETON = new Singleton<LibControl>() {
        @Override
        protected LibControl create() {
            return new LibControl();
        }
    };

    public static LibControl getInstance() {
        return SINGLETON.get();
    }

    private volatile static Context context;

    /**
     * 初始化三方库及辅助工具类
     *
     * @param application
     */
    public void init(Application application) {
        context = application.getApplicationContext();
        RealmUtils.getInstance().init(context);
        SkinManager.getInstance().init(context);
        CrashCollectUtils.getInstance();
    }

    public Context getContext() {
        if (context == null) {
            //注销app
        }
        return context;
    }

    /**
     * 释放三方库及辅助工具类
     */
    public void release() {
        VoiceUtils.getInstance().release();//释放声音播放
        RealmUtils.getInstance().release();
        SkinManager.getInstance().release();
    }
}
