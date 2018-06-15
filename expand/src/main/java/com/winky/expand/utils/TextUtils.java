package com.winky.expand.utils;

import android.content.Context;
import android.support.annotation.ColorRes;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;

import java.util.List;

public class TextUtils {

    public static boolean isEmpty(@Nullable CharSequence str) {
        return str == null || str.length() == 0;
    }

    public static <V> boolean isEmpty(List<V> sourceList) {
        return (sourceList == null || sourceList.size() == 0);
    }

    public static SpannableString makeTxt(Context context, @ColorRes int id, String str, int start, int length) {
        SpannableString styled = new SpannableString(str);
        styled.setSpan(new ForegroundColorSpan(ContextCompat.getColor(context, id)), start, start + length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        return styled;
    }

}
