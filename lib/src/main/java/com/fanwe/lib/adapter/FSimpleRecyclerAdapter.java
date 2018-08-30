package com.fanwe.lib.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.fanwe.lib.adapter.viewholder.FRecyclerViewHolder;

public abstract class FSimpleRecyclerAdapter<T> extends FRecyclerAdapter<T>
{
    public FSimpleRecyclerAdapter()
    {
    }

    public FSimpleRecyclerAdapter(Context context)
    {
        super(context);
    }

    @Override
    public FRecyclerViewHolder<T> onCreateVHolder(ViewGroup parent, int viewType)
    {
        final int layoutId = getLayoutId(parent, viewType);
        final View itemView = LayoutInflater.from(getContext()).inflate(layoutId, parent, false);
        FRecyclerViewHolder<T> holder = new FRecyclerViewHolder<T>(itemView)
        {
            @Override
            public void onBindData(int position, T model)
            {
            }
        };
        return holder;
    }

    /**
     * 返回布局id
     *
     * @param parent
     * @param viewType
     * @return
     */
    public abstract int getLayoutId(ViewGroup parent, int viewType);
}
