package com.winky.douniwan.ui.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.ViewGroup;

import com.winky.expand.basics.BaseFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by winky on 2017/11/24.
 */
public class FragmentAdapter extends FragmentPagerAdapter {

    private final List<BaseFragment> fragments;
    private int curPosition;

    public FragmentAdapter(FragmentManager fm, List<BaseFragment> fragments) {
        super(fm);
        this.fragments = fragments == null ? new ArrayList<BaseFragment>() : fragments;
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return fragments.get(position).getTitle();
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        return super.instantiateItem(container, position);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
//        super.destroyItem(container, position, object);//fragment加载后不自动销毁
    }

    @Override
    public void setPrimaryItem(ViewGroup container, int position, Object object) {
        curPosition = position;
        super.setPrimaryItem(container, position, object);
    }

    public BaseFragment getCurFragment() {
        return fragments.get(curPosition);
    }

    public int getCurPosition() {
        return curPosition;
    }
}

