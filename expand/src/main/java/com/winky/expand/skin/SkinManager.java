package com.winky.expand.skin;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.support.annotation.RestrictTo;
import android.support.v4.content.ContextCompat;

import com.winky.expand.Config;
import com.winky.expand.db.SharedUtils;
import com.winky.expand.skin.listener.ISkinLoader;
import com.winky.expand.skin.listener.ISkinUpdate;
import com.winky.expand.skin.listener.SkinLoaderListener;
import com.winky.expand.utils.FileUtils;
import com.winky.expand.utils.Singleton;
import com.winky.expand.utils.TextUtils;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * https://github.com/burgessjp/ThemeSkinning
 */
public class SkinManager implements ISkinLoader {

    private static final Singleton<SkinManager> SINGLETON = new Singleton<SkinManager>() {
        @Override
        protected SkinManager create() {
            return new SkinManager();
        }
    };

    public static SkinManager getInstance() {
        return SINGLETON.get();
    }

    /**
     * 自定义字体包地址
     */
    private final String PREF_FONT_PATH = "skin_font_path";
    /**
     * 自定义皮肤地址
     */
    private final String PREF_CUSTOM_SKIN_PATH = "skin_custom_path";
    /**
     * assets 下皮肤的文件夹名
     */
    private final String SKIN_DIR_NAME = "skin";
    /**
     * assets 下字体包的文件夹名
     */
    private final String FONT_DIR_NAME = "fonts";

    private Typeface typeface;
    private List<ISkinUpdate> mSkinObservers;
    private Context context;
    private Resources mResources;
    private boolean isDefaultSkin = false;
    /**
     * skin packageName
     */
    private String skinPackageName;

    private SharedUtils sharedUtils;

    private FileUtils fileUtils;
    private String skinDirPath;

    private SkinManager() {
        fileUtils = FileUtils.getInstance();
        skinDirPath = fileUtils.ifDirNotExist(fileUtils.getCacheDir() + File.separator + SKIN_DIR_NAME + File.separator);
    }

    public void init(Context context) {
        this.context = context;
        sharedUtils = SharedUtils.get(context, Config.SHARE_CUSTOM);
        typeface = getCustomTypeface();
        setSkinFile(context);

        String skin = (String) sharedUtils.get(PREF_CUSTOM_SKIN_PATH, "");
        if (TextUtils.isEmpty(skin)) {
            return;
        }
        loadSkin(skin, null);
    }

    public Typeface getCustomTypeface() {
        String fontPath = (String) sharedUtils.get(PREF_FONT_PATH, "");
        Typeface tf;
        if (!TextUtils.isEmpty(fontPath)) {
            tf = Typeface.createFromAsset(context.getAssets(), fontPath);
        } else {
            tf = Typeface.DEFAULT;
            sharedUtils.put(PREF_FONT_PATH, "");
        }
        return tf;
    }

    /**
     * 将assets/skin下的皮肤复制到内置文件夹
     *
     * @param context
     */
    private void setSkinFile(Context context) {
        try {
            String[] skinFiles = context.getAssets().list(SKIN_DIR_NAME);
            String skinDir = fileUtils.ifDirNotExist(fileUtils.getCacheDir());
            for (String fileName : skinFiles) {
                File file = new File(skinDirPath + fileName);
                if (!file.exists()) {
                    String assetPath = SKIN_DIR_NAME + File.separator + fileName;
                    String toPath = skinDirPath + fileName;
                    FileUtils.getInstance().copyAssetsToDir(context, assetPath, toPath);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public int getColorPrimaryDark() {
        if (mResources != null) {
            int identify = mResources.getIdentifier("colorPrimaryDark", "color", skinPackageName);
            if (identify > 0) {
                return mResources.getColor(identify);
            }
        }
        return -1;
    }

    /**
     * 是否自定义皮肤
     *
     * @return
     */
    public boolean isExternalSkin() {
        return !isDefaultSkin && mResources != null;
    }

    public String getCurSkinPackageName() {
        return skinPackageName;
    }

    public Resources getResources() {
        return mResources;
    }

    public void restoreDefaultTheme() {
        //清除缓存的皮肤
        sharedUtils.put(PREF_CUSTOM_SKIN_PATH, "");
        isDefaultSkin = true;
        mResources = context.getResources();
        skinPackageName = context.getPackageName();
        notifySkinUpdate();
    }

    @Override
    public void attach(ISkinUpdate observer) {
        if (mSkinObservers == null) {
            mSkinObservers = new ArrayList<>();
        }
        if (!mSkinObservers.contains(observer)) {
            mSkinObservers.add(observer);
        }
    }

    @Override
    public void detach(ISkinUpdate observer) {
        if (mSkinObservers != null && mSkinObservers.contains(observer)) {
            mSkinObservers.remove(observer);
        }
    }

    @Override
    public void notifySkinUpdate() {
        if (mSkinObservers != null) {
            for (ISkinUpdate observer : mSkinObservers) {
                observer.onThemeUpdate();
            }
        }
    }

    /**
     * load skin form local
     * <p>
     * eg:theme.skin
     * </p>
     *
     * @param skinName the name of skin(in assets/skin)
     * @param callback load Callback
     */
    @SuppressLint("StaticFieldLeak")
    public void loadSkin(String skinName, final SkinLoaderListener callback) {

        new AsyncTask<String, Void, Resources>() {

            @Override
            protected void onPreExecute() {
                if (callback != null) {
                    callback.onStart();
                }
            }

            @Override
            protected Resources doInBackground(String... params) {
                try {
                    if (params.length == 1) {
                        String skinPkgPath = fileUtils.ifFileNotExist(skinDirPath + params[0]);
                        File file = new File(skinPkgPath);
                        if (!file.exists()) {
                            return null;
                        }
                        PackageManager packageManager = context.getPackageManager();
                        PackageInfo packageInfo = packageManager.getPackageArchiveInfo(skinPkgPath, PackageManager.GET_ACTIVITIES);
                        if (packageInfo == null) {
                            return null;
                        }
                        skinPackageName = packageInfo.packageName;
                        AssetManager assetManager = AssetManager.class.newInstance();
                        Method addAssetPath = assetManager.getClass().getMethod("addAssetPath", String.class);
                        addAssetPath.invoke(assetManager, skinPkgPath);


                        Resources superRes = context.getResources();
                        Resources skinResource;
//                        if (Build.VERSION.SDK_INT >= 26) {
//                            skinResource = Resources.getSystem();
//                        } else {
                        skinResource = new Resources(assetManager, superRes.getDisplayMetrics(), superRes.getConfiguration());
//                        }
                        //保存用户设置的皮肤路径
                        sharedUtils.put(PREF_CUSTOM_SKIN_PATH, params[0]);

                        isDefaultSkin = false;
                        return skinResource;
                    }
                    return null;
                } catch (Exception e) {
                    e.printStackTrace();
                    return null;
                }
            }

            @Override
            protected void onPostExecute(Resources result) {
                mResources = result;

                if (mResources != null) {
                    if (callback != null) {
                        callback.onSuccess();
                    }
                    notifySkinUpdate();
                } else {
                    isDefaultSkin = true;
                    if (callback != null) {
                        callback.onFailed("没有获取到资源");
                    }
                }
            }

        }.execute(skinName);
    }


    public Typeface getCurTypeface() {
        return typeface;
    }

    /**
     * load font
     *
     * @param fontName font name in assets/fonts
     */
    public void loadFont(String fontName) {
        Typeface tf;
        if (!TextUtils.isEmpty(fontName)) {
            String fontPath = FONT_DIR_NAME + File.separator + fontName;
            tf = Typeface.createFromAsset(context.getAssets(), fontPath);
            //缓存字体地址
            sharedUtils.put(PREF_FONT_PATH, fontPath);
        } else {
            tf = Typeface.DEFAULT;
            sharedUtils.put(PREF_FONT_PATH, "");
        }
        TextViewCache.getInstance().applyFont(tf);
    }

    //region Resource obtain
    @RestrictTo(RestrictTo.Scope.LIBRARY)
    public int getColor(int resId) {
        int originColor = ContextCompat.getColor(context, resId);
        if (mResources == null || isDefaultSkin) {
            return originColor;
        }

        String resName = context.getResources().getResourceEntryName(resId);

        int trueResId = mResources.getIdentifier(resName, "color", skinPackageName);
        int trueColor;
        if (trueResId == 0) {
            trueColor = originColor;
        } else {
            trueColor = mResources.getColor(trueResId);
        }
        return trueColor;
    }

    /**
     * get drawable from specific directory
     *
     * @param resId res id
     * @param dir   res directory
     * @return drawable
     */
    @RestrictTo(RestrictTo.Scope.LIBRARY)
    public Drawable getDrawable(int resId, String dir) {
        Drawable originDrawable = ContextCompat.getDrawable(context, resId);
        if (mResources == null || isDefaultSkin) {
            return originDrawable;
        }
        String resName = context.getResources().getResourceEntryName(resId);
        int trueResId = mResources.getIdentifier(resName, dir, skinPackageName);
        Drawable trueDrawable;
        if (trueResId == 0) {
            trueDrawable = originDrawable;
        } else {
            if (android.os.Build.VERSION.SDK_INT < 22) {
                trueDrawable = mResources.getDrawable(trueResId);
            } else {
                trueDrawable = mResources.getDrawable(trueResId, context.getTheme());
            }
        }
        return trueDrawable;
    }

    @RestrictTo(RestrictTo.Scope.LIBRARY)
    public Drawable getDrawable(int resId) {
        Drawable originDrawable = ContextCompat.getDrawable(context, resId);
        if (mResources == null || isDefaultSkin) {
            return originDrawable;
        }
        String resName = context.getResources().getResourceEntryName(resId);
        int trueResId = mResources.getIdentifier(resName, "drawable", skinPackageName);
        Drawable trueDrawable;
        if (trueResId == 0) {
            trueResId = mResources.getIdentifier(resName, "mipmap", skinPackageName);
        }
        if (trueResId == 0) {
            trueDrawable = originDrawable;
        } else {
            if (android.os.Build.VERSION.SDK_INT < 22) {
                trueDrawable = mResources.getDrawable(trueResId);
            } else {
                trueDrawable = mResources.getDrawable(trueResId, context.getTheme());
            }
        }
        return trueDrawable;
    }

    /**
     * 加载指定资源颜色drawable,转化为ColorStateList，保证selector类型的Color也能被转换。
     * 无皮肤包资源返回默认主题颜色
     * author:pinotao
     *
     * @param resId resources id
     * @return ColorStateList
     */
    @RestrictTo(RestrictTo.Scope.LIBRARY)
    public ColorStateList getColorStateList(int resId) {
        boolean isExternalSkin = true;
        if (mResources == null || isDefaultSkin) {
            isExternalSkin = false;
        }

        String resName = context.getResources().getResourceEntryName(resId);
        if (isExternalSkin) {
            int trueResId = mResources.getIdentifier(resName, "color", skinPackageName);
            ColorStateList trueColorList;
            if (trueResId == 0) { // 如果皮肤包没有复写该资源，但是需要判断是否是ColorStateList

                return ContextCompat.getColorStateList(context, resId);
            } else {
                trueColorList = mResources.getColorStateList(trueResId);
                return trueColorList;
            }
        } else {
            return ContextCompat.getColorStateList(context, resId);
        }
    }

    /**
     * 释放资源
     */
    public void release() {
        TextViewCache.getInstance().clear();
    }
}
