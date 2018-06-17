package com.winky.douniwan.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.winky.douniwan.R;
import com.winky.douniwan.tools.NavigationUtils;
import com.winky.expand.basics.BaseFragment;
import com.winky.expand.basics.BaseSwipeBackFragment;

public class FragmentTest1 extends BaseSwipeBackFragment {
    @Override
    public int bindLayout() {
        return R.layout.fragment_test1;
    }

    @Override
    public void init(@Nullable View view, @Nullable Bundle savedInstanceState) {
        view.findViewById(R.id.tv_test1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavigationUtils.getInstance().navigate(getView(), R.id.fragment_test2);
            }
        });
        view.findViewById(R.id.tv_test2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavigationUtils.getInstance().navigateUp(getView());
            }
        });
    }
}
