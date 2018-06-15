package com.winky.expand.view.recycler.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import com.winky.expand.view.recycler.ViewHolder;
import com.winky.expand.view.recycler.listener.OnItemClickListener;

/**
 * Created by winky on 2018/4/9.
 */

public abstract class BaseAdapter extends RecyclerView.Adapter<ViewHolder> {

    private Context context;
    private LayoutInflater layoutInflater;
    private OnItemClickListener itemClickListener;

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (context == null) {
            this.context = parent.getContext();
        }
        if (layoutInflater == null) {
            layoutInflater = LayoutInflater.from(context);
        }
        ViewHolder viewHolder = new ViewHolder(layoutInflater.inflate(getLayoutId(viewType), parent, false));
        if (!(viewHolder.getConvertView() instanceof AdapterView)) {
            viewHolder.setOnClickListener(clickListener);
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        convert(holder, position);
    }

    private View.OnClickListener clickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            View view = (View) v.getParent();
            if (view == null || itemClickListener == null) {
                return;
            }
            v.setClickable(false);

            if (view instanceof RecyclerView) {
                RecyclerView recyclerView = (RecyclerView) view;
                int position = recyclerView.getChildAdapterPosition(v);
                itemClickListener.onItemClick(recyclerView, v, position);
            }
            v.setClickable(true);
        }
    };

    public void setItemClickListener(OnItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    protected abstract int getLayoutId(int viewType);

    protected abstract void convert(ViewHolder holder, int position);

}
