package com.winky.expand.skin;

import android.app.Activity;
import android.graphics.Typeface;
import android.widget.TextView;

import com.winky.expand.utils.Singleton;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 采集所有的textview，处理动态换字体
 *
 * @author winky
 * @date 2018/5/27
 */
public class TextViewCache {

    public static final Singleton<TextViewCache> SINGLETON = new Singleton<TextViewCache>() {
        @Override
        protected TextViewCache create() {
            return new TextViewCache();
        }
    };

    public static TextViewCache getInstance() {
        return SINGLETON.get();
    }

    private final Map<String, List<TextView>> textViewCache = new HashMap<>();

    public void add(Activity activity, TextView textView) {

        String className = activity.getLocalClassName();
        if (textViewCache.containsKey(className)) {
            textViewCache.get(className).add(textView);
        } else {
            List<TextView> textViews = new ArrayList<>();
            textViews.add(textView);
            textViewCache.put(className, textViews);
        }
        textView.setTypeface(SkinManager.getInstance().getCurTypeface());
    }

    public void remove(Activity activity) {
        textViewCache.remove(activity.getLocalClassName());
    }

    public void remove(Activity activity, TextView textView) {
        if (textViewCache.containsKey(activity.getLocalClassName())) {
            textViewCache.get(activity.getLocalClassName()).remove(textView);
        }
    }

    public void clear() {
        textViewCache.clear();
    }

    public void applyFont(Typeface tf) {
        for (String className : textViewCache.keySet()) {
            for (TextView textView : textViewCache.get(className)) {
                textView.setTypeface(tf);
            }
        }
    }
}
