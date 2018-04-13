package com.fanwe.lib.adapter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.fanwe.lib.adapter.callback.ItemClickCallback;
import com.fanwe.lib.adapter.callback.ItemLongClickCallback;
import com.fanwe.lib.adapter.data.DataHolder;
import com.fanwe.lib.adapter.viewholder.FRecyclerViewHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * RecyclerView适配器
 *
 * @param <T> 实体类型
 */
public abstract class FRecyclerAdapter<T> extends RecyclerView.Adapter<FRecyclerViewHolder<T>> implements
        FAdapter<T>
{
    private FAdapterProxy<T> mAdapterProxy;
    private List<Object> mDefaultPayloads = new ArrayList<>();

    private ItemClickCallback<T> mItemClickCallback;
    private ItemLongClickCallback<T> mItemLongClickCallback;

    public FRecyclerAdapter(Activity activity)
    {
        getAdapterProxy().setActivity(activity);
    }

    /**
     * 设置item点击回调
     *
     * @param itemClickCallback
     */
    public void setItemClickCallback(ItemClickCallback<T> itemClickCallback)
    {
        this.mItemClickCallback = itemClickCallback;
    }

    /**
     * 设置item长按回调
     *
     * @param itemLongClickCallback
     */
    public void setItemLongClickCallback(ItemLongClickCallback<T> itemLongClickCallback)
    {
        this.mItemLongClickCallback = itemLongClickCallback;
    }

    /**
     * 通知item点击回调
     *
     * @param item
     * @param view
     */
    public final void notifyItemClickCallback(T item, View view)
    {
        if (mItemClickCallback != null)
        {
            final int position = getDataHolder().indexOf(item);
            mItemClickCallback.onItemClick(position, item, view);
        }
    }

    /**
     * 通知item长按回调
     *
     * @param item
     * @param view
     * @return
     */
    public final boolean notifyItemLongClickCallback(T item, View view)
    {
        if (mItemLongClickCallback != null)
        {
            final int position = getDataHolder().indexOf(item);
            return mItemLongClickCallback.onItemLongClick(position, item, view);
        }
        return false;
    }

    @Override
    public int getItemCount()
    {
        return getDataHolder().size();
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
        final T model = getDataHolder().get(position);

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
                public void onItemRangeChanged(int index, int itemCount)
                {
                    FRecyclerAdapter.this.notifyItemRangeChanged(index, itemCount, mDefaultPayloads);
                }

                @Override
                public void onItemRangeInserted(int index, int itemCount)
                {
                    FRecyclerAdapter.this.notifyItemRangeInserted(index, itemCount);
                }

                @Override
                public void onItemRangeRemoved(int index, int itemCount)
                {
                    FRecyclerAdapter.this.notifyItemRangeRemoved(index, itemCount);
                }
            });
        }
        return mAdapterProxy;
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
    public void notifyItemViewChanged(int position)
    {
        getAdapterProxy().notifyItemViewChanged(position);
    }

    @Override
    public DataHolder<T> getDataHolder()
    {
        return getAdapterProxy().getDataHolder();
    }

    //----------FAdapter implements end----------
}
