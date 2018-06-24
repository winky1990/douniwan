package com.winky.douniwan.ui.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.view.ViewPager;
import android.view.MenuItem;

import com.winky.douniwan.R;
import com.winky.douniwan.ui.adapter.FragmentAdapter;
import com.winky.douniwan.ui.fragment.FragmentTest1;
import com.winky.douniwan.ui.fragment.FragmentTest3;
import com.winky.douniwan.ui.fragment.MessageFragment;
import com.winky.douniwan.ui.fragment.MineFragment;
import com.winky.expand.basics.SkinActivity;

import java.util.Arrays;

import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;

public class MainActivity extends SkinActivity {

    // Used to load the 'native-lib' library on application startup.
    static {
//        System.loadLibrary("native-lib");
    }

    private ViewPager viewPager;
    private BottomNavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_main);
        viewPager = findViewById(R.id.viewPager);
        navigationView = findViewById(R.id.bottom_navigation);
        navigationView.setOnNavigationItemSelectedListener(itemSelectedListener);
        viewPager.addOnPageChangeListener(pageChangeListener);
        viewPager.setAdapter(new FragmentAdapter(getSupportFragmentManager(), Arrays.asList(
                new FragmentTest1().setTitle(getString(R.string.main_home)),
                new MessageFragment().setTitle(getString(R.string.main_chat)),
                new MineFragment().setTitle(getString(R.string.main_mine)))));
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

    /**
     * A native method that is implemented by the 'native-lib' native library,
     * which is packaged with this application.
     */
//    public native String stringFromJNI();
}
