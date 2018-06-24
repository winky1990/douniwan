package com.winky.douniwan.ui.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.winky.douniwan.R;
import com.winky.douniwan.tools.navigation.NavigationUtils;
import com.winky.expand.Config;
import com.winky.expand.basics.SkinActivity;

import androidx.navigation.fragment.NavHostFragment;

public class NavigationActivity extends SkinActivity {

    private NavHostFragment hostFragment;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_frame);
        int navigationId = getIntent().getIntExtra(Config.NAVIGATION_ID, 0);
        if (navigationId == 0) {
            throw new IllegalArgumentException("navigation_id == 0");
        }
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.fl_content, hostFragment = NavHostFragment.create(navigationId))
                .commit();
    }


    @Override
    public void onBackPressed() {
        if (!NavHostFragment.findNavController(hostFragment).navigateUp()) {
            finish();
        }
    }
}
