package com.sd.lib.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public abstract class FRecyclerViewHolder<T> extends RecyclerView.ViewHolder
{
    private Adapter<T> mAdapter;

    public FRecyclerViewHolder(View itemView)
    {
        super(itemView);
    }

    public FRecyclerViewHolder(int layoutId, ViewGroup parent)
    {
        this(LayoutInflater.from(parent.getContext()).inflate(layoutId, parent, false));
    }

    void setAdapter(Adapter<T> adapter)
    {
        mAdapter = adapter;
    }

    public final Adapter<T> getAdapter()
    {
        return mAdapter;
    }

    public final <V extends View> V findViewById(int id)
    {
        return itemView.findViewById(id);
    }

    /**
     * {@link #findViewById(int)}
     */
    @Deprecated
    public final <V extends View> V get(int id)
    {
        return itemView.findViewById(id);
    }

    /**
     * 创建回调，用来初始化
     */
    protected abstract void onCreate();

    /**
     * 绑定数据
     *
     * @param position
     * @param model
     */
    public abstract void onBindData(int position, T model);

    /**
     * 刷新item的时候触发，默认整个item重新绑定数据
     *
     * @param position
     * @param model
     */
    public void onUpdateData(int position, T model)
    {
        onBindData(position, model);
    }
}
