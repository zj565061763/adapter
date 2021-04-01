package com.sd.lib.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.sd.lib.adapter.callback.CallbackHolder;
import com.sd.lib.adapter.callback.ItemClickCallback;
import com.sd.lib.adapter.callback.ItemLongClickCallback;
import com.sd.lib.adapter.data.DataHolder;
import com.sd.lib.adapter.viewholder.FRecyclerViewHolder;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * RecyclerView适配器
 *
 * @param <T> 实体类型
 */
public abstract class FRecyclerAdapter<T> extends RecyclerView.Adapter<FRecyclerViewHolder<T>> implements Adapter<T>
{
    private AdapterProxy<T> mAdapterProxy;
    private final Map<FRecyclerViewHolder<T>, String> mViewHolder = new ConcurrentHashMap<>();

    private ItemClickCallback<T> mItemClickCallback;
    private ItemLongClickCallback<T> mItemLongClickCallback;

    public FRecyclerAdapter()
    {
    }

    public FRecyclerAdapter(Context context)
    {
        setContext(context);
    }

    /**
     * {@link #getCallbackHolder()}
     *
     * @param itemClickCallback
     */
    @Deprecated
    public void setItemClickCallback(ItemClickCallback<T> itemClickCallback)
    {
        this.mItemClickCallback = itemClickCallback;
    }

    /**
     * {@link #getCallbackHolder()}
     *
     * @param itemLongClickCallback
     */
    @Deprecated
    public void setItemLongClickCallback(ItemLongClickCallback<T> itemLongClickCallback)
    {
        this.mItemLongClickCallback = itemLongClickCallback;
    }

    /**
     * {@link #getCallbackHolder()}
     *
     * @param item
     * @param view
     */
    @Deprecated
    public final void notifyItemClickCallback(T item, View view)
    {
        if (mItemClickCallback != null)
        {
            final int position = getDataHolder().indexOf(item);
            mItemClickCallback.onItemClick(position, item, view);
        }
    }

    /**
     * {@link #getCallbackHolder()}
     *
     * @param item
     * @param view
     * @return
     */
    @Deprecated
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
        setContext(parent.getContext());

        final FRecyclerViewHolder<T> holder = onCreateVHolder(parent, viewType);
        holder.setAdapter(this);
        holder.notifyCreate();

        onViewHolderCreated(holder);
        return holder;
    }

    protected void onViewHolderCreated(FRecyclerViewHolder<T> viewHolder)
    {
    }

    @Override
    public final void onBindViewHolder(FRecyclerViewHolder<T> holder, int position, List<Object> payloads)
    {
        final boolean isUpdate = payloads != null && payloads.size() > 0;
        onBindViewHolderInternal(holder, position, isUpdate);
    }

    @Override
    public final void onBindViewHolder(FRecyclerViewHolder<T> holder, int position)
    {
        onBindViewHolderInternal(holder, position, false);
    }

    private void onBindViewHolderInternal(FRecyclerViewHolder<T> holder, int position, boolean isUpdate)
    {
        final T model = getDataHolder().get(position);
        dispatchBindData(holder, position, model, isUpdate);
    }

    /**
     * 分发数据绑定
     *
     * @param holder
     * @param position
     * @param model
     * @param isUpdate
     */
    protected void dispatchBindData(FRecyclerViewHolder<T> holder, int position, T model, boolean isUpdate)
    {
        if (isUpdate)
        {
            holder.notifyUpdateData(position, model);
            onUpdateData(holder, position, model);
        } else
        {
            holder.notifyBindData(position, model);
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
    public void onViewAttachedToWindow(@NonNull FRecyclerViewHolder<T> holder)
    {
        super.onViewAttachedToWindow(holder);
        if (holder instanceof FRecyclerViewHolder)
            mViewHolder.put(holder, "");
    }

    @Override
    public void onViewDetachedFromWindow(@NonNull FRecyclerViewHolder<T> holder)
    {
        super.onViewDetachedFromWindow(holder);
        if (holder instanceof FRecyclerViewHolder)
            mViewHolder.remove(holder);
    }

    /**
     * 刷新ViewHolder
     */
    public void refreshViewHolder()
    {
        for (FRecyclerViewHolder<T> item : mViewHolder.keySet())
        {
            item.refreshData();
        }
    }

    /**
     * 返回当前已经添加到UI上面的所有ViewHolder对象
     *
     * @return
     */
    public Map<Integer, FRecyclerViewHolder<T>> getAllViewHolder()
    {
        final Map<Integer, FRecyclerViewHolder<T>> map = new HashMap<>();
        for (FRecyclerViewHolder<T> item : mViewHolder.keySet())
        {
            final int position = item.getAdapterPosition();
            map.put(position, item);
        }
        return map;
    }

    /**
     * 返回某个位置的ViewHolder
     *
     * @param position
     * @return
     */
    public FRecyclerViewHolder<T> getViewHolder(int position)
    {
        if (position < 0)
            return null;

        for (FRecyclerViewHolder<T> item : mViewHolder.keySet())
        {
            final int adapterPosition = item.getAdapterPosition();
            if (adapterPosition == position)
                return item;
        }
        return null;
    }

    //----------Adapter implements start----------

    private AdapterProxy<T> getAdapterProxy()
    {
        if (mAdapterProxy == null)
        {
            mAdapterProxy = new AdapterProxy<>(new AdapterProxy.Callback()
            {
                private byte[] mDefaultPayload;

                public byte[] getDefaultPayload()
                {
                    if (mDefaultPayload == null)
                        mDefaultPayload = new byte[0];
                    return mDefaultPayload;
                }

                @Override
                public void onDataSetChanged()
                {
                    FRecyclerAdapter.this.notifyDataSetChanged();
                }

                @Override
                public void onItemRangeChanged(int index, int itemCount)
                {
                    FRecyclerAdapter.this.notifyItemRangeChanged(index, itemCount, getDefaultPayload());
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

    @Override
    public void setContext(Context context)
    {
        getAdapterProxy().setContext(context);
    }

    @Override
    public Context getContext()
    {
        return getAdapterProxy().getContext();
    }

    @Override
    public void setNotifyDataChangeMode(NotifyDataChangeMode mode)
    {
        getAdapterProxy().setNotifyDataChangeMode(mode);
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

    @Override
    public CallbackHolder<T> getCallbackHolder()
    {
        return getAdapterProxy().getCallbackHolder();
    }

    //----------Adapter implements end----------
}
