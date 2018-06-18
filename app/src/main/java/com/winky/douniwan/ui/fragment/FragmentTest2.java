package com.winky.douniwan.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.winky.douniwan.R;
import com.winky.expand.basics.ToolbarFragment;

public class FragmentTest2 extends ToolbarFragment {

    @Override
    protected void initContent(@Nullable View view, @Nullable Bundle savedInstanceState) {
        setTitle("FragmentTest2");
    }

    @Override
    public int bindContentLayout() {
        return R.layout.fragment_test2;
    }
}
