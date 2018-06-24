package com.winky.douniwan.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.winky.douniwan.R;
import com.winky.expand.basics.SkinFragment;
import com.winky.expand.utils.GlideUtils;
import com.winky.expand.utils.SysUtils;

public class MineFragment extends SkinFragment {

    @Override
    public int bindLayout() {
        return R.layout.fragment_mine;
    }

    @Override
    public void init(@Nullable View view, @Nullable Bundle savedInstanceState) {
        GlideUtils.getInstance().LoadContextBlurBitmap(getContext(), "http://download.sdk.mob.com/e72/83d/e247e8b45bd557f70ac6dcc0cb.png", (ImageView) findViewById(R.id.image1));
        ((TextView) view.findViewById(R.id.text1)).setText(SysUtils.getInstance().getIMEI());
    }
}
