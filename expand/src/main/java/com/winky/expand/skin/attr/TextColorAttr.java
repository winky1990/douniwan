package com.winky.expand.skin.attr;

import android.view.View;
import android.widget.TextView;

import com.winky.expand.skin.SkinAttr;
import com.winky.expand.skin.SkinManager;


public class TextColorAttr extends SkinAttr {

    @Override
    protected void applySkin(View view) {
        if (view instanceof TextView) {
            TextView tv = (TextView) view;
            if (isColor()) {
                tv.setTextColor(SkinManager.getInstance().getColorStateList(attrValueRefId));
            }
        }
    }

}
