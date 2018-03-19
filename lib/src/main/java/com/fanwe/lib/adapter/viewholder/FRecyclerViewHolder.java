package com.fanwe.lib.adapter.viewholder;

import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.fanwe.lib.adapter.FAdapter;

public abstract class FRecyclerViewHolder<T> extends RecyclerView.ViewHolder
{
    private FAdapter<T> mAdapter;
    private SparseArray<View> mArrayView;

    public FRecyclerViewHolder(View itemView)
    {
        super(itemView);
    }

    public FRecyclerViewHolder(int layoutId, ViewGroup parent)
    {
        this(LayoutInflater.from(parent.getContext()).inflate(layoutId, parent, false));
    }

    public void setAdapter(FAdapter<T> adapter)
    {
        mAdapter = adapter;
    }

    public final FAdapter<T> getAdapter()
    {
        return mAdapter;
    }

    public final <V extends View> V findViewById(int id)
    {
        return itemView.findViewById(id);
    }

    /**
     * 在itemView中通过id查找view，如果需要频繁的通过id查找view，调用此方法查找效率较高
     *
     * @param id
     * @param <V>
     * @return
     */
    public final <V extends View> V get(int id)
    {
        if (mArrayView == null)
        {
            mArrayView = new SparseArray<>();
        }
        View view = mArrayView.get(id);
        if (view == null)
        {
            view = itemView.findViewById(id);
            if (view != null)
            {
                mArrayView.put(id, view);
            }
        }
        return (V) view;
    }

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
