package com.winky.douniwan.tools;

import android.app.Application;
import android.content.Context;

import com.winky.expand.db.RealmUtils;
import com.winky.expand.skin.SkinManager;
import com.winky.expand.utils.CrashCollectUtils;
import com.winky.expand.utils.FileUtils;
import com.winky.expand.utils.Singleton;
import com.winky.expand.utils.SysUtils;
import com.winky.expand.utils.VoiceUtils;

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
        //注意相互依赖
        SysUtils.getInstance().setContext(context);
        FileUtils.getInstance().setContext(context);
        RealmUtils.getInstance().init(context);
        SkinManager.getInstance().init(context);
        CrashCollectUtils.getInstance();

//        MobSDK.init(context);
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
        context = null;
    }
}
