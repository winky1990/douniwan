package com.winky.expand.view.layout.auto;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;

import com.winky.expand.Config;
import com.winky.expand.utils.Singleton;
import com.winky.expand.utils.SysUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * https://github.com/hongyangAndroid/AndroidAutoLayout
 *
 * @author winky
 * @date 2018/5/26
 */
public class AutoUtils {

    private static final Singleton<AutoUtils> SINGLETON = new Singleton<AutoUtils>() {
        @Override
        protected AutoUtils create() {
            return new AutoUtils();
        }
    };

    public static AutoUtils getInstance() {
        return SINGLETON.get();
    }

    /**
     * 手机屏幕宽高对应设计图宽高比例
     */
    private float scaleWidth, scaleHeight;

    private AutoUtils() {
        DisplayMetrics displayMetrics = SysUtils.getInstance().getDisplayMetrics();
        this.scaleWidth = 1.0f * displayMetrics.widthPixels / Config.DESIGN_SCREEN[0];
        this.scaleHeight = 1.0f * displayMetrics.heightPixels / Config.DESIGN_SCREEN[1];
    }

    private static final int[] ATTRS = new int[]
            {
//                    android.R.attr.textSize,
                    android.R.attr.padding,//
                    android.R.attr.paddingLeft,//
                    android.R.attr.paddingTop,//
                    android.R.attr.paddingRight,//
                    android.R.attr.paddingBottom,//
                    android.R.attr.layout_width,//
                    android.R.attr.layout_height,//
                    android.R.attr.layout_margin,//
                    android.R.attr.layout_marginLeft,//
                    android.R.attr.layout_marginTop,//
                    android.R.attr.layout_marginRight,//
                    android.R.attr.layout_marginBottom,//
                    android.R.attr.maxWidth,//
                    android.R.attr.maxHeight,//
                    android.R.attr.minWidth,//
                    android.R.attr.minHeight,//
            };

    /**
     * 解析attrs里设置的属性以及值
     *
     * @param context
     * @param attrs
     * @return
     */
    public void analysisParams(View view, Context context, AttributeSet attrs) {
        List<AutoParam> paramList = new ArrayList<>();
        TypedArray array = context.obtainStyledAttributes(attrs, ATTRS);
        for (int i = 0; i < array.getIndexCount(); i++) {
            int index = array.getIndex(i);
            if (!isPxVal(array.peekValue(index))) {
                continue;
            }
            int value = 0;
            try {
                value = array.getDimensionPixelOffset(index, 0);
            } catch (Exception ex) {
                ex.printStackTrace();
                continue;
            }
            paramList.add(new AutoParam(ATTRS[index], value));
        }
        array.recycle();
        setViewAttrs(view, paramList);
    }

    /**
     * 判断是否有效的属性类型
     *
     * @param typedValue
     * @return
     */
    private boolean isPxVal(TypedValue typedValue) {
        if ((typedValue != null)
                && (typedValue.type == TypedValue.TYPE_DIMENSION)
                && ((TypedValue.COMPLEX_UNIT_MASK & (typedValue.data >> TypedValue.COMPLEX_UNIT_SHIFT)) == TypedValue.COMPLEX_UNIT_PX)) {
            return true;
        }
        return false;
    }

    public void setViewAttrs(View view, List<AutoParam> paramList) {
        if (view == null || paramList.size() == 0) {
            return;
        }
        for (AutoParam param : paramList) {
            setAttr(view, param);
        }
    }

    public void setLayoutAttrs(ViewGroup viewGroup) {
        for (int i = 0; i < viewGroup.getChildCount(); i++) {
            View view = viewGroup.getChildAt(i);
            if (view instanceof ViewGroup) {
                setLayoutAttrs((ViewGroup) view);
            } else {
                setViewAttrs(view, analysisView(view));
            }
        }
    }

    /**
     * 会直接将view的LayoutParams上设置的width，height直接进行百分比处理
     *
     * @param view
     */
    public List<AutoParam> analysisView(View view) {
        List<AutoParam> paramList = new ArrayList<>();
        ViewGroup.LayoutParams params = view.getLayoutParams();
        if (params == null) return null;
        // width & height
        if (params.height > 0) {
            paramList.add(new AutoParam(android.R.attr.layout_height, (int) (params.height * scaleHeight)));
        }
        if (params.width > 0) {
            paramList.add(new AutoParam(android.R.attr.layout_width, (int) (params.width * scaleWidth)));
        }
        //margin
        if (params instanceof ViewGroup.MarginLayoutParams) {
            ViewGroup.MarginLayoutParams marginParams = (ViewGroup.MarginLayoutParams) params;

            if (marginParams.topMargin != 0) {
                paramList.add(new AutoParam(android.R.attr.layout_marginTop, (int) (marginParams.topMargin * scaleHeight)));
            }
            if (marginParams.bottomMargin != 0) {
                paramList.add(new AutoParam(android.R.attr.layout_marginBottom, (int) (marginParams.bottomMargin * scaleHeight)));
            }
            if (marginParams.leftMargin != 0) {
                paramList.add(new AutoParam(android.R.attr.layout_marginLeft, (int) (marginParams.leftMargin * scaleWidth)));
            }
            if (marginParams.rightMargin != 0) {
                paramList.add(new AutoParam(android.R.attr.layout_marginRight, (int) (marginParams.rightMargin * scaleWidth)));
            }
        }
        //padding
        if (view.getPaddingLeft() != 0) {
            paramList.add(new AutoParam(android.R.attr.paddingLeft, (int) (view.getPaddingLeft() * scaleWidth)));
        }
        if (view.getPaddingEnd() != 0) {
            paramList.add(new AutoParam(android.R.attr.paddingRight, (int) (view.getPaddingEnd() * scaleWidth)));
        }
        if (view.getPaddingTop() != 0) {
            paramList.add(new AutoParam(android.R.attr.paddingTop, (int) (view.getPaddingTop() * scaleHeight)));
        }
        if (view.getPaddingBottom() != 0) {
            paramList.add(new AutoParam(android.R.attr.paddingBottom, (int) (view.getPaddingBottom() * scaleHeight)));
        }
        //textsize
//        if (view instanceof TextView) {
//            paramList.add(new AutoParam(android.R.attr.textSize, (int) ((TextView) view).getTextSize()));
//        }
        return paramList;
    }

    public void setAttr(View view, AutoParam param) {
        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();

        int padleft = 0, padright = 0, padtop = 0, padbottom = 0;
        switch (param.getAttr()) {
            case android.R.attr.layout_height:
                layoutParams.height = param.getValue();
                break;
            case android.R.attr.layout_width:
                layoutParams.width = param.getValue();
                break;
            case android.R.attr.paddingLeft:
                padleft = param.getValue();
                break;
            case android.R.attr.paddingRight:
                padright = param.getValue();
                break;
            case android.R.attr.paddingTop:
                padtop = param.getValue();
                break;
            case android.R.attr.paddingBottom:
                padbottom = param.getValue();
                break;
        }
        if (!(padleft == 0 && padright == 0 && padtop == 0 && padbottom == 0))
            view.setPadding(padleft, padtop, padright, padbottom);
        if (layoutParams instanceof ViewGroup.MarginLayoutParams) {
            ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) layoutParams;
            switch (param.getAttr()) {
                case android.R.attr.layout_marginTop:
                    marginLayoutParams.topMargin = param.getValue();
                    break;
                case android.R.attr.layout_marginBottom:
                    marginLayoutParams.bottomMargin = param.getValue();
                    break;
                case android.R.attr.layout_marginLeft:
                    marginLayoutParams.leftMargin = param.getValue();
                    break;
                case android.R.attr.layout_marginRight:
                    marginLayoutParams.rightMargin = param.getValue();
                    break;
            }
        }
    }

}
