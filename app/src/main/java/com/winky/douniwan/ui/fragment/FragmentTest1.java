package com.winky.douniwan.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.winky.douniwan.R;
import com.winky.expand.basics.BaseSwipeBackFragment;

public class FragmentTest1 extends BaseSwipeBackFragment {
    @Override
    public int bindLayout() {
        return R.layout.fragment_test1;
    }

    @Override
    public void init(@Nullable View view, @Nullable Bundle savedInstanceState) {

    }
}
