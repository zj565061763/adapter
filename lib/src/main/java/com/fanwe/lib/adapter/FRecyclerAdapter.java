package com.fanwe.lib.adapter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.fanwe.lib.adapter.viewholder.FRecyclerViewHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * RecyclerView适配器
 *
 * @param <T> 实体类型
 */
public abstract class FRecyclerAdapter<T> extends RecyclerView.Adapter<FRecyclerViewHolder<T>> implements
        FAdapter<T>,
        View.OnClickListener
{
    private FAdapterProxy<T> mAdapterProxy;
    private List<Object> mDefaultPayloads = new ArrayList<>();

    private SDItemClickCallback<T> mItemClickCallback;
    private SDItemLongClickCallback<T> mItemLongClickCallback;

    public FRecyclerAdapter(Activity activity)
    {
        getAdapterProxy().setActivity(activity);
    }

    private FAdapterProxy<T> getAdapterProxy()
    {
        if (mAdapterProxy == null)
        {
            mAdapterProxy = new FAdapterProxy<>();
            mAdapterProxy.setCallback(new FAdapterProxy.Callback()
            {
                @Override
                public void onDataSetChanged()
                {
                    FRecyclerAdapter.this.notifyDataSetChanged();
                }

                @Override
                public void onItemRangeChanged(int positionStart, int itemCount)
                {
                    FRecyclerAdapter.this.notifyItemRangeChanged(positionStart, itemCount, mDefaultPayloads);
                }

                @Override
                public void onItemRangeInserted(int positionStart, int itemCount)
                {
                    FRecyclerAdapter.this.notifyItemRangeInserted(positionStart, itemCount);
                }

                @Override
                public void onItemRangeRemoved(int positionStart, int itemCount)
                {
                    FRecyclerAdapter.this.notifyItemRangeRemoved(positionStart, itemCount);
                }
            });
        }
        return mAdapterProxy;
    }

    /**
     * 设置item点击回调
     *
     * @param itemClickCallback
     */
    public void setItemClickCallback(SDItemClickCallback<T> itemClickCallback)
    {
        this.mItemClickCallback = itemClickCallback;
    }

    /**
     * 设置item长按回调
     *
     * @param itemLongClickCallback
     */
    public void setItemLongClickCallback(SDItemLongClickCallback<T> itemLongClickCallback)
    {
        this.mItemLongClickCallback = itemLongClickCallback;
    }

    /**
     * 通知item点击回调
     *
     * @param position
     * @param item
     * @param view
     */
    public void notifyItemClickCallback(int position, T item, View view)
    {
        if (mItemClickCallback != null)
        {
            mItemClickCallback.onItemClick(position, item, view);
        }
    }

    /**
     * 通知item长按回调
     *
     * @param position
     * @param item
     * @param view
     */
    public void notifyItemLongClickCallback(int position, T item, View view)
    {
        if (mItemLongClickCallback != null)
        {
            mItemLongClickCallback.onItemLongClick(position, item, view);
        }
    }

    @Override
    public int getItemCount()
    {
        return getDataCount();
    }

    @Override
    public final FRecyclerViewHolder<T> onCreateViewHolder(ViewGroup parent, int viewType)
    {
        FRecyclerViewHolder<T> holder = onCreateVHolder(parent, viewType);
        holder.setAdapter(this);

        return holder;
    }

    @Override
    public final void onBindViewHolder(FRecyclerViewHolder<T> holder, int position, List<Object> payloads)
    {
        onBindViewHolderInternal(holder, position, true);
    }

    @Override
    public final void onBindViewHolder(FRecyclerViewHolder<T> holder, int position)
    {
        onBindViewHolderInternal(holder, position, false);
    }

    private void onBindViewHolderInternal(FRecyclerViewHolder<T> holder, int position, boolean isUpdate)
    {
        T model = getData(position);
        holder.setModel(model);

        if (isUpdate)
        {
            holder.onUpdateData(position, model);
            onUpdateData(holder, position, model);
        } else
        {
            holder.onBindData(position, model);
            onBindData(holder, position, model);
        }
    }

    /**
     * 创建ViewHolder
     *
     * @param parent
     * @param viewType
     * @return
     */
    public abstract FRecyclerViewHolder<T> onCreateVHolder(ViewGroup parent, int viewType);

    /**
     * 绑定数据
     *
     * @param holder
     * @param position
     * @param model
     */
    public abstract void onBindData(FRecyclerViewHolder<T> holder, int position, T model);

    /**
     * 刷新item的时候触发，默认整个item重新绑定数据
     *
     * @param holder
     * @param position
     * @param model
     */
    public void onUpdateData(FRecyclerViewHolder<T> holder, int position, T model)
    {
        onBindData(holder, position, model);
    }

    @Override
    public void onClick(View view)
    {

    }

    //----------FAdapter implements start----------

    @Override
    public Activity getActivity()
    {
        return getAdapterProxy().getActivity();
    }

    @Override
    public void setNotifyOnDataChanged(boolean notify)
    {
        getAdapterProxy().setNotifyOnDataChanged(notify);
    }

    @Override
    public boolean isPositionLegal(int position)
    {
        return getAdapterProxy().isPositionLegal(position);
    }

    @Override
    public T getData(int position)
    {
        return getAdapterProxy().getData(position);
    }

    @Override
    public int getDataCount()
    {
        return getAdapterProxy().getDataCount();
    }

    @Override
    public int indexOf(T model)
    {
        return getAdapterProxy().indexOf(model);
    }

    @Override
    public List<T> getData()
    {
        return getAdapterProxy().getData();
    }

    @Override
    public void setData(List<T> list)
    {
        getAdapterProxy().setData(list);
    }

    @Override
    public void appendData(T model)
    {
        getAdapterProxy().appendData(model);
    }

    @Override
    public void appendData(List<T> list)
    {
        getAdapterProxy().appendData(list);
    }

    @Override
    public void removeData(T model)
    {
        getAdapterProxy().removeData(model);
    }

    @Override
    public T removeData(int position)
    {
        return getAdapterProxy().removeData(position);
    }

    @Override
    public void insertData(int position, T model)
    {
        getAdapterProxy().insertData(position, model);
    }

    @Override
    public void insertData(int position, List<T> list)
    {
        getAdapterProxy().insertData(position, list);
    }

    @Override
    public void updateData(int position, T model)
    {
        getAdapterProxy().updateData(position, model);
    }

    @Override
    public void notifyItemViewChanged(int position)
    {
        getAdapterProxy().notifyItemViewChanged(position);
    }

    @Override
    public void clearData()
    {
        getAdapterProxy().clearData();
    }

    //----------FAdapter implements end----------
}
