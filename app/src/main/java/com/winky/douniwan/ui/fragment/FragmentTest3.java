package com.winky.douniwan.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.winky.douniwan.R;
import com.winky.douniwan.tools.navigation.NavigationUtils;
import com.winky.douniwan.ui.activity.NavigationActivity;
import com.winky.expand.basics.ToolbarFragment;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;

public class FragmentTest3 extends ToolbarFragment {

    @Override
    protected void initContent(@Nullable View view, @Nullable Bundle savedInstanceState) {
        findViewById(R.id.tv_test3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavigationUtils.getInstance().toNavigation(getContext(),R.navigation.navigation_home);
            }
        });
    }

    @Override
    public int bindContentLayout() {
        return R.layout.fragment_test3;
    }

}
