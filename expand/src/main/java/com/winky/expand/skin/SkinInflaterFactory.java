package com.winky.expand.skin;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDelegate;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.winky.expand.Config;
import com.winky.expand.utils.Logger;
import com.winky.expand.utils.TextUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 自定义的InflaterFactory，用来代替默认的LayoutInflaterFactory
 * Ref: <a href="http://willowtreeapps.com/blog/app-development-how-to-get-the-right-layoutinflater/"> How to Get the Right LayoutInflater</a>
 */
public class SkinInflaterFactory implements LayoutInflater.Factory2 {

    /**
     * 自定义命名控件
     */
    public static final String NAMESPACE = "http://schemas.android.com/android/custom";
    /**
     * 是否支持换肤
     */
    public static final String ATTR_SKIN_ENABLE = "enable";

    /**
     * 存储那些有皮肤更改需求的View及其对应的属性的集合
     */
    private Map<View, SkinItem> skinItemMapCache = new ConcurrentHashMap<>();
    private AppCompatActivity mAppCompatActivity;
    private final Logger logger = Logger.getInstance();

    public SkinInflaterFactory(AppCompatActivity appCompatActivity) {
        this.mAppCompatActivity = appCompatActivity;
    }

    @Override
    public View onCreateView(String s, Context context, AttributeSet attributeSet) {
        return null;
    }

    @Override
    public View onCreateView(View parent, String name, Context context, AttributeSet attrs) {
        boolean isSkinEnable = attrs.getAttributeBooleanValue(NAMESPACE, ATTR_SKIN_ENABLE, false);
        AppCompatDelegate delegate = mAppCompatActivity.getDelegate();
        View view = delegate.createView(parent, name, context, attrs);
        if (view instanceof TextView && Config.COLLECTION_TEXTVIEW) {
            TextViewCache.getInstance().add(mAppCompatActivity, (TextView) view);
        }
        if (isSkinEnable) {
            if (view == null) {
                view = ViewProducer.createViewFromTag(context, name, attrs);
            }
            if (view == null) {
                return null;
            }
            parseSkinAttr(context, attrs, view);
        }
        return view;
    }

    /**
     * collect skin view
     */
    private void parseSkinAttr(Context context, AttributeSet attrs, View view) {
        List<SkinAttr> viewAttrs = new ArrayList<>();
//        logger.i("viewName:" + view.getClass().getSimpleName());
        for (int i = 0; i < attrs.getAttributeCount(); i++) {
            String attrName = attrs.getAttributeName(i);
            String attrValue = attrs.getAttributeValue(i);
//            logger.i("    AttributeName:" + attrName + "|attrValue:" + attrValue);
            //region  style
            //style theme
            if ("style".equals(attrName)) {
                int[] skinAttrs = new int[]{android.R.attr.textColor, android.R.attr.background};
                TypedArray a = context.getTheme().obtainStyledAttributes(attrs, skinAttrs, 0, 0);
                int textColorId = a.getResourceId(0, -1);
                int backgroundId = a.getResourceId(1, -1);
                if (textColorId != -1) {
                    String entryName = context.getResources().getResourceEntryName(textColorId);
                    String typeName = context.getResources().getResourceTypeName(textColorId);
                    SkinAttr skinAttr = AttrFactory.get("textColor", textColorId, entryName, typeName);
//                    logger.w("    textColor in style is supported:" + "\n" +
//                            "    resource id:" + textColorId + "\n" +
//                            "    attrName:" + attrName + "\n" +
//                            "    attrValue:" + attrValue + "\n" +
//                            "    entryName:" + entryName + "\n" +
//                            "    typeName:" + typeName);
                    if (skinAttr != null) {
                        viewAttrs.add(skinAttr);
                    }
                }
                if (backgroundId != -1) {
                    String entryName = context.getResources().getResourceEntryName(backgroundId);
                    String typeName = context.getResources().getResourceTypeName(backgroundId);
                    SkinAttr skinAttr = AttrFactory.get("background", backgroundId, entryName, typeName);
//                    logger.w("    background in style is supported:" + "\n" +
//                            "    resource id:" + backgroundId + "\n" +
//                            "    attrName:" + attrName + "\n" +
//                            "    attrValue:" + attrValue + "\n" +
//                            "    entryName:" + entryName + "\n" +
//                            "    typeName:" + typeName);
                    if (skinAttr != null) {
                        viewAttrs.add(skinAttr);
                    }

                }
                a.recycle();
                continue;
            }
            //endregion
            //if attrValue is reference，eg:@color/red
            if (AttrFactory.isSupportedAttr(attrName) && attrValue.startsWith("@")) {
                try {
                    //resource id
                    int id = Integer.parseInt(attrValue.substring(1));
                    if (id == 0) {
                        continue;
                    }
                    //entryName，eg:text_color_selector
                    String entryName = context.getResources().getResourceEntryName(id);
                    //typeName，eg:color、drawable
                    String typeName = context.getResources().getResourceTypeName(id);
                    SkinAttr mSkinAttr = AttrFactory.get(attrName, id, entryName, typeName);
//                    logger.w("    " + attrName + " is supported:" + "\n" +
//                            "    resource id:" + id + "\n" +
//                            "    attrName:" + attrName + "\n" +
//                            "    attrValue:" + attrValue + "\n" +
//                            "    entryName:" + entryName + "\n" +
//                            "    typeName:" + typeName
//                    );
                    if (mSkinAttr != null) {
                        viewAttrs.add(mSkinAttr);
                    }
                } catch (NumberFormatException e) {
                    logger.e(e.toString());
                }
            }
        }
        if (!TextUtils.isEmpty(viewAttrs)) {
            SkinItem skinItem = new SkinItem();
            skinItem.view = view;
            skinItem.attrs = viewAttrs;
            skinItemMapCache.put(skinItem.view, skinItem);
            if (SkinManager.getInstance().isExternalSkin()) {//如果当前皮肤来自于外部或者是处于夜间模式
                skinItem.apply();
            }
        }
    }

    public void applySkin() {
        if (skinItemMapCache.isEmpty()) {
            return;
        }
        for (View view : skinItemMapCache.keySet()) {
            if (view == null) {
                continue;
            }
            skinItemMapCache.get(view).apply();
        }
    }

    /**
     * clear skin view
     */
    public void clean() {
        for (View view : skinItemMapCache.keySet()) {
            if (view == null) {
                continue;
            }
            skinItemMapCache.get(view).clean();
        }
        TextViewCache.getInstance().remove(mAppCompatActivity);
        skinItemMapCache.clear();
        skinItemMapCache = null;
    }

    public void removeSkinView(View view) {
//        logger.i("removeSkinView:" + view);
        SkinItem skinItem = skinItemMapCache.remove(view);
        if (skinItem != null) {
//            logger.w("removeSkinView from mSkinItemMap:" + skinItem.view);
        }
        if (Config.COLLECTION_TEXTVIEW && view instanceof TextView) {
//            logger.e("removeSkinView from TextViewRepository:" + view);
            TextViewCache.getInstance().remove(mAppCompatActivity, (TextView) view);
        }
    }


}
