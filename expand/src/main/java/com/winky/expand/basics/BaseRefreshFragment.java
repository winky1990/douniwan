package com.winky.expand.basics;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.winky.expand.delegate.IPullRefresh;
import com.winky.expand.delegate.PullRefreshDelegate;

import java.util.List;

public abstract class BaseRefreshFragment<T> extends BaseSkinFragment implements IPullRefresh<T> {

    private PullRefreshDelegate<T> delegate = new PullRefreshDelegate(this);

    @Override
    public int bindLayout() {
        return 0;
    }

    @Override
    public void init(@Nullable View view, @Nullable Bundle savedInstanceState) {

    }

    @Override
    public void refreshData(List<T> data, int count) {
        delegate.refreshData(data, count);
    }
}
