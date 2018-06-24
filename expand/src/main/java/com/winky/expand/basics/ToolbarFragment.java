package com.winky.expand.basics;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import com.winky.expand.R;
import com.winky.expand.utils.SysUtils;
import com.winky.expand.view.layout.AutoFrameLayout;

import androidx.navigation.fragment.NavHostFragment;

public abstract class ToolbarFragment extends SkinFragment {
    @Override
    public int bindLayout() {
        return R.layout.activity_base_bar;
    }

    private AutoFrameLayout frameLayout;
    private Toolbar toolbar;

    @Override
    public void init(@Nullable View view, @Nullable Bundle savedInstanceState) {
        toolbar = view.findViewById(R.id.toolbar);
        frameLayout = view.findViewById(R.id.fl_content);
        toolbar.setPadding(0, SysUtils.getInstance().getStatusHeight(), 0, 0);
        View contentView = getLayoutInflater().inflate(bindContentLayout(), null);
        frameLayout.addView(contentView);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);//设置toolbar
        setHasOptionsMenu(true);//使用fragment的menu
        initContent(contentView, savedInstanceState);
    }

    public void setTitle(CharSequence title) {
        toolbar.setTitle(title);
    }

    public void setTitle(@StringRes int resId) {
        this.setTitle(getContext().getText(resId));
    }

    protected abstract void initContent(@Nullable View view, @Nullable Bundle savedInstanceState);

    public abstract int bindContentLayout();
}
