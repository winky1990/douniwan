package com.winky.expand.skin;

import android.view.View;

public abstract class SkinAttr implements Cloneable {
    protected static final String RES_TYPE_NAME_COLOR = "color";
    protected static final String RES_TYPE_NAME_DRAWABLE = "drawable";
    protected static final String RES_TYPE_NAME_MIPMAP = "mipmap";
    /**
     * attribute name, eg: background、textColor
     */
    protected String attrName;

    /**
     * attribute reference id
     */
    protected int attrValueRefId;

    /**
     * resources name, eg:app_exit_btn_background
     */
    protected String attrValueRefName;

    /**
     * type of the value , such as color or drawable
     */
    protected String attrValueTypeName;

    /**
     * Use to apply view with new TypedValue
     *
     * @param view view
     */
    public void apply(View view) {
        applySkin(view);
    }

    protected abstract void applySkin(View view);

    protected boolean isDrawable() {
        return RES_TYPE_NAME_DRAWABLE.equals(attrValueTypeName)
                || RES_TYPE_NAME_MIPMAP.equals(attrValueTypeName);
    }

    protected boolean isColor() {
        return RES_TYPE_NAME_COLOR.equals(attrValueTypeName);
    }

    @Override
    public String toString() {
        return "SkinAttr{" +
                "attrName='" + attrName + '\'' +
                ", attrValueRefId=" + attrValueRefId +
                ", attrValueRefName='" + attrValueRefName + '\'' +
                ", attrValueTypeName='" + attrValueTypeName + '\'' +
                '}';
    }

    @Override
    public SkinAttr clone() {
        SkinAttr o = null;
        try {
            o = (SkinAttr) super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return o;
    }
}
