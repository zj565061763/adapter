package com.fanwe.lib.adapter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.fanwe.lib.adapter.viewholder.SDRecyclerViewHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * RecyclerView适配器
 *
 * @param <T> 实体类型
 */
public abstract class SDRecyclerAdapter<T> extends RecyclerView.Adapter<SDRecyclerViewHolder<T>> implements
        ISDAdapter<T>,
        View.OnClickListener
{
    private Activity mActivity;
    private List<T> mListModel = new ArrayList<>();
    private List<Object> mDefaultPayloads = new ArrayList<>();

    private boolean mAutoNotifyDataSetChanged = true;

    private SDItemClickCallback<T> mItemClickCallback;
    private SDItemLongClickCallback<T> mItemLongClickCallback;

    public SDRecyclerAdapter(Activity activity)
    {
        this(null, activity);
    }

    public SDRecyclerAdapter(List<T> listModel, Activity activity)
    {
        super();
        this.mActivity = activity;
        setData(listModel);
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
    public final SDRecyclerViewHolder<T> onCreateViewHolder(ViewGroup parent, int viewType)
    {
        SDRecyclerViewHolder<T> holder = onCreateVHolder(parent, viewType);
        holder.setAdapter(this);

        return holder;
    }

    @Override
    public final void onBindViewHolder(SDRecyclerViewHolder<T> holder, int position, List<Object> payloads)
    {
        onBindViewHolderInternal(holder, position, true);
    }

    @Override
    public final void onBindViewHolder(SDRecyclerViewHolder<T> holder, int position)
    {
        onBindViewHolderInternal(holder, position, false);
    }

    private void onBindViewHolderInternal(SDRecyclerViewHolder<T> holder, int position, boolean isUpdate)
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
    public abstract SDRecyclerViewHolder<T> onCreateVHolder(ViewGroup parent, int viewType);

    /**
     * 绑定数据
     *
     * @param holder
     * @param position
     * @param model
     */
    public abstract void onBindData(SDRecyclerViewHolder<T> holder, int position, T model);

    /**
     * 刷新item的时候触发，默认整个item重新绑定数据
     *
     * @param holder
     * @param position
     * @param model
     */
    public void onUpdateData(SDRecyclerViewHolder<T> holder, int position, T model)
    {
        onBindData(holder, position, model);
    }

    @Override
    public void onClick(View view)
    {

    }

    //----------ISDAdapter implements start----------

    @Override
    public Activity getActivity()
    {
        return mActivity;
    }

    @Override
    public View inflate(int resource, ViewGroup root)
    {
        return getActivity().getLayoutInflater().inflate(resource, root, false);
    }

    @Override
    public boolean isPositionLegal(int position)
    {
        if (position >= 0 && position < mListModel.size())
        {
            return true;
        }
        return false;
    }

    @Override
    public void setAutoNotifyDataSetChanged(boolean auto)
    {
        mAutoNotifyDataSetChanged = auto;
    }

    @Override
    public T getData(int position)
    {
        if (isPositionLegal(position))
        {
            return mListModel.get(position);
        }
        return null;
    }

    @Override
    public int getDataCount()
    {
        if (mListModel != null)
        {
            return mListModel.size();
        }
        return 0;
    }

    @Override
    public int indexOf(T model)
    {
        return mListModel.indexOf(model);
    }

    @Override
    public void updateData(List<T> list)
    {
        setData(list);
        if (mAutoNotifyDataSetChanged)
        {
            notifyDataSetChanged();
        }
    }

    @Override
    public void setData(List<T> list)
    {
        if (list != null)
        {
            this.mListModel = list;
        } else
        {
            this.mListModel.clear();
        }
    }

    @Override
    public List<T> getData()
    {
        return mListModel;
    }

    @Override
    public void appendData(T model)
    {
        if (model == null)
        {
            return;
        }

        mListModel.add(model);
        if (mAutoNotifyDataSetChanged)
        {
            notifyItemInserted(mListModel.size() - 1);
        }
    }

    @Override
    public void appendData(List<T> list)
    {
        if (list == null || list.isEmpty())
        {
            return;
        }

        final int positionStart = mListModel.size();
        final int itemCount = list.size();

        mListModel.addAll(list);
        if (mAutoNotifyDataSetChanged)
        {
            notifyItemRangeInserted(positionStart, itemCount);
        }
    }

    @Override
    public void removeData(T model)
    {
        removeData(indexOf(model));
    }

    @Override
    public T removeData(int position)
    {
        if (!isPositionLegal(position))
        {
            return null;
        }

        final T model = mListModel.remove(position);
        if (mAutoNotifyDataSetChanged)
        {
            notifyItemRemoved(position);
        }
        return model;
    }

    @Override
    public void insertData(int position, T model)
    {
        if (model == null)
        {
            return;
        }

        mListModel.add(position, model);
        if (mAutoNotifyDataSetChanged)
        {
            notifyItemInserted(position);
        }
    }

    @Override
    public void insertData(int position, List<T> list)
    {
        if (list == null || list.isEmpty())
        {
            return;
        }

        final int positionStart = position;
        final int itemCount = list.size();

        mListModel.addAll(position, list);
        if (mAutoNotifyDataSetChanged)
        {
            notifyItemRangeInserted(positionStart, itemCount);
        }
    }

    @Override
    public void updateData(int position, T model)
    {
        if (model == null || !isPositionLegal(position))
        {
            return;
        }

        mListModel.set(position, model);
        if (mAutoNotifyDataSetChanged)
        {
            updateData(position);
        }
    }

    @Override
    public void updateData(int position)
    {
        if (!isPositionLegal(position))
        {
            return;
        }
        notifyItemChanged(position, mDefaultPayloads);
    }

    @Override
    public void clearData()
    {
        updateData(null);
    }

    //----------ISDAdapter implements end----------
}
