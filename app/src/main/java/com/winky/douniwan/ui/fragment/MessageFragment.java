package com.winky.douniwan.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.winky.douniwan.R;
import com.winky.douniwan.ui.adapter.FragmentAdapter;
import com.winky.expand.basics.SkinFragment;
import com.winky.expand.basics.ToolbarFragment;
import com.winky.expand.utils.SysUtils;

import java.util.Arrays;

public class MessageFragment extends SkinFragment implements View.OnClickListener {
    @Override
    public int bindLayout() {
        return R.layout.fragment_message;
    }

    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;

    @Override
    public void init(@Nullable View view, @Nullable Bundle savedInstanceState) {
        findViewById(R.id.toolbar_rtxt).setOnClickListener(this);
        toolbar = findViewById(R.id.toolbar);
        tabLayout = findViewById(R.id.tabLayout);
        viewPager = findViewById(R.id.viewPager);
        viewPager.setAdapter(new FragmentAdapter(getChildFragmentManager(), Arrays.asList(
                new FragmentTest1().setTitle(getString(R.string.msg_message)),
                new FragmentTest2().setTitle(getString(R.string.msg_friends)),
                new FragmentTest3().setTitle(getString(R.string.msg_group))
        )));
        tabLayout.setupWithViewPager(viewPager);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.toolbar_rtxt:

                break;
        }
    }
}
