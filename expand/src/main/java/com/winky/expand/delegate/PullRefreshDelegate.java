package com.winky.expand.delegate;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewStub;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.winky.expand.view.recycler.adapter.QuickAdapter;

import java.util.List;

/**
 * @author winky
 * @date 2018/5/12
 */
public class PullRefreshDelegate<T> {

    //下拉刷新
    private SmartRefreshLayout refreshLayout;
    private RecyclerView recyclerView;
    private ViewStub viewStub;

    private int pageIndex = 1;
    private QuickAdapter<T> adapter;

    private IPullRefresh pullRefresh;

    public PullRefreshDelegate(IPullRefresh pullRefresh) {
        this.pullRefresh = pullRefresh;
    }

    public void init(SmartRefreshLayout refreshLayout, RecyclerView recyclerView) {
        this.refreshLayout = refreshLayout;
        this.recyclerView = recyclerView;
        this.recyclerView.setAdapter(this.adapter = pullRefresh.initAdapter());
        this.refreshLayout.setDisableContentWhenLoading(false);
        this.refreshLayout.setEnableOverScrollDrag(true);
        this.refreshLayout.setEnableFooterFollowWhenLoadFinished(false);
        this.refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                pageIndex = 1;
                pullRefresh.reqData(pageIndex);
            }
        });
        this.refreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(RefreshLayout refreshlayout) {
                pageIndex++;
                pullRefresh.reqData(pageIndex);
            }
        });
    }

    public void setViewStub(ViewStub viewStub) {
        this.viewStub = viewStub;
    }

    public void refreshData(List<T> datas, int count) {
        if (refreshLayout == null || recyclerView == null) {
            return;
        }
        refreshLayout.finishRefresh();
        if (pageIndex == 1) {
            adapter.clear();
        }
        if (datas != null) {
            adapter.addAll(datas);
        }
        if (adapter.getItemCount() >= count) {
            refreshLayout.finishLoadMoreWithNoMoreData();
        } else {
            refreshLayout.finishLoadMore();
        }
        /*如果没有数据则展示空白视图*/
        if (adapter.getItemCount() == 0) {
            if (viewStub != null)
                viewStub.inflate().setVisibility(View.VISIBLE);
            /*设置不可刷新和加载更多,且列表隐藏*/
            refreshLayout.setEnableRefresh(false);
            refreshLayout.setEnableLoadMore(false);
            recyclerView.setVisibility(View.GONE);
        } else {
            if (recyclerView.getVisibility() == View.GONE) {
                refreshLayout.setEnableRefresh(true);
                refreshLayout.setEnableLoadMore(true);
                recyclerView.setVisibility(View.VISIBLE);
            }
        }
    }
}
