package com.winky.expand;

public final class Config {
    /**
     * 是否调试模式
     */
    public static final boolean isDebug = true;
    /**
     * 设计屏幕宽、高
     */
    public static final int[] DESIGN_SCREEN = {1080, 1920};

    /**
     * SharedPreferences 缓存通用数据，退出不清除
     */
    public static final String SHARE_CURRENCY = "currency";

    /**
     * SharedPreferences 缓存用户数据，退出登录需清除
     */
    public static final String SHARE_CUSTOM = "custom";
    /**
     * 即时生效换字体，是否采集textview
     */
    public static final boolean COLLECTION_TEXTVIEW = true;

    public static final String NAVIGATION_ID = "navigation_id";
}
