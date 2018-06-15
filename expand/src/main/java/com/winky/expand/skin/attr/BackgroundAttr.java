package com.winky.expand.skin.attr;

import android.graphics.drawable.Drawable;
import android.view.View;

import com.winky.expand.skin.SkinAttr;
import com.winky.expand.skin.SkinManager;

public class BackgroundAttr extends SkinAttr {

    @Override
    protected void applySkin(View view) {
        if (isColor()) {
            int color = SkinManager.getInstance().getColor(attrValueRefId);
            view.setBackgroundColor(color);
        } else if (isDrawable()) {
            Drawable bg = SkinManager.getInstance().getDrawable(attrValueRefId);
            view.setBackground(bg);
        }
    }
}
