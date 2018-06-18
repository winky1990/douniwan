package com.winky.douniwan.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.winky.douniwan.R;
import com.winky.expand.basics.BaseFragment;
import com.winky.expand.basics.BaseSwipeBackFragment;
import com.winky.expand.basics.BaseToolbarFragment;

public class FragmentTest2 extends BaseToolbarFragment {

    @Override
    protected void initContent(@Nullable View view, @Nullable Bundle savedInstanceState) {
        setTitle("FragmentTest2");
    }

    @Override
    public int bindContentLayout() {
        return R.layout.fragment_test2;
    }
}
