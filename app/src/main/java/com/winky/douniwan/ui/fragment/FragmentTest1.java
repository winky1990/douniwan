package com.winky.douniwan.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.winky.douniwan.R;
import com.winky.douniwan.tools.NavigationUtils;
import com.winky.expand.basics.BaseFragment;
import com.winky.expand.basics.BaseSkinFragment;
import com.winky.expand.basics.BaseSwipeBackFragment;
import com.winky.expand.basics.BaseToolbarFragment;

public class FragmentTest1 extends BaseToolbarFragment {

    @Override
    protected void initContent(@Nullable View view, @Nullable Bundle savedInstanceState) {
        setTitle("FragmentTest1");
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

    @Override
    public int bindContentLayout() {
        return R.layout.fragment_test1;
    }
}
