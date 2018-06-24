package com.winky.douniwan.ui.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.view.ViewPager;
import android.view.MenuItem;
import android.view.View;

import com.winky.douniwan.R;
import com.winky.douniwan.ui.adapter.FragmentAdapter;
import com.winky.expand.basics.BaseFragment;
import com.winky.expand.basics.SkinFragment;

import java.util.Arrays;
import java.util.List;
import java.util.NavigableMap;

import androidx.navigation.NavHost;
import androidx.navigation.fragment.NavHostFragment;

public class MainFragment extends SkinFragment {
    @Override
    public int bindLayout() {
        return R.layout.fragment_main;
    }

    private ViewPager viewPager;
    private BottomNavigationView navigationView;

    @Override
    public void init(@Nullable View view, @Nullable Bundle savedInstanceState) {
        viewPager = findViewById(R.id.viewPager);
        navigationView = findViewById(R.id.bottom_navigation);
        navigationView.setOnNavigationItemSelectedListener(itemSelectedListener);
        viewPager.addOnPageChangeListener(pageChangeListener);
        viewPager.setAdapter(new FragmentAdapter(getChildFragmentManager(), Arrays.asList(
                new FragmentTest1().setTitle(getString(R.string.main_home)),
                new MessageFragment().setTitle(getString(R.string.main_chat)),
                new FragmentTest3().setTitle(getString(R.string.main_mine)))));
        viewPager.setOffscreenPageLimit(2);
        viewPager.setCurrentItem(0);
    }

    private BottomNavigationView.OnNavigationItemSelectedListener itemSelectedListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            if (viewPager.getCurrentItem() == item.getOrder()) {
                return false;
            }
            viewPager.setCurrentItem(item.getOrder());
            return true;
        }
    };

    private ViewPager.OnPageChangeListener pageChangeListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            navigationView.getMenu().getItem(position).setChecked(true);
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };
}
