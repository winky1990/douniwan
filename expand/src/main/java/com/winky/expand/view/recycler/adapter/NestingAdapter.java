package com.winky.expand.view.recycler.adapter;

import android.support.annotation.LayoutRes;
import android.util.SparseArray;

/**
 * 主要针对嵌套布局适配
 * Created by winky on 2018/4/9.
 */
public abstract class NestingAdapter extends BaseAdapter {

    private SparseArray<Integer> layouts;

    @Override
    protected int getLayoutId(int viewType) {
        return layouts.get(viewType);
    }

    public void addItemType(int type, @LayoutRes int layoutResId) {
        if (layouts == null) {
            layouts = new SparseArray<>();
        }
        layouts.put(type, layoutResId);
    }

    @Override
    public int getItemCount() {
        return layouts.size();
    }

    @Override
    public int getItemViewType(int position) {
        return layouts.keyAt(position);
    }

    public int getItemViewLayout(int position) {
        return layouts.valueAt(position);
    }
}
