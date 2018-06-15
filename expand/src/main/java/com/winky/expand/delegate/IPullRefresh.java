package com.winky.expand.delegate;

import android.view.ViewStub;


import com.winky.expand.view.recycler.adapter.QuickAdapter;

import java.util.List;

/**
 * @author winky
 * @date 2018/5/12
 */
public interface IPullRefresh<T> {

    void reqData(int pageIndex);

    QuickAdapter<T> initAdapter();

    void refreshData(List<T> datas, int count);

    ViewStub setEmptyView(ViewStub viewStub);
}
