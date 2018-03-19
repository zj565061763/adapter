package com.fanwe.lib.adapter;

import android.app.Activity;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.fanwe.lib.adapter.callback.ItemClickCallback;
import com.fanwe.lib.adapter.callback.ItemLongClickCallback;
import com.fanwe.lib.adapter.data.DataHolder;
import com.fanwe.lib.adapter.select.AdapterSelectManager;
import com.fanwe.lib.selectmanager.FSelectManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.WeakHashMap;

public abstract class FBaseAdapter<T> extends BaseAdapter implements
        FAdapter<T>,
        View.OnClickListener,
        ItemClickCallback<T>
{
    private FAdapterProxy<T> mAdapterProxy;
    /**
     * 保存每个itemView对应的position
     */
    private Map<View, Integer> mMapViewPosition = new WeakHashMap<>();
    private FSelectManager<T> mSelectManager;

    private ItemClickCallback<T> mItemClickCallback;
    private ItemLongClickCallback<T> mItemLongClickCallback;

    public FBaseAdapter(Activity activity)
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
                    FBaseAdapter.this.notifyDataSetChanged();
                }

                @Override
                public void onItemRangeChanged(int positionStart, int itemCount)
                {
                    if (itemCount == 1)
                    {
                        final List<View> list = getItemView(positionStart);
                        if (list != null)
                        {
                            for (View item : list)
                            {
                                FBaseAdapter.this.onUpdateView(positionStart, item, (ViewGroup) item.getParent(), getItem(positionStart));
                            }
                        }
                    } else
                    {
                        FBaseAdapter.this.notifyDataSetChanged();
                    }
                }

                @Override
                public void onItemRangeInserted(int positionStart, int itemCount)
                {
                    FBaseAdapter.this.notifyDataSetChanged();
                }

                @Override
                public void onItemRangeRemoved(int positionStart, int itemCount)
                {
                    FBaseAdapter.this.notifyDataSetChanged();
                }
            });
        }
        return mAdapterProxy;
    }

    public final FSelectManager<T> getSelectManager()
    {
        if (mSelectManager == null)
        {
            final AdapterSelectManager<T> manager = new AdapterSelectManager<>();
            getDataHolder().addDataChangeCallback(manager);

            mSelectManager = manager;
        }
        return mSelectManager;
    }

    /**
     * 设置item点击回调
     *
     * @param itemClickCallback
     */
    public void setItemClickCallback(ItemClickCallback<T> itemClickCallback)
    {
        mItemClickCallback = itemClickCallback;
    }

    /**
     * 设置item长按回调
     *
     * @param itemLongClickCallback
     */
    public void setItemLongClickCallback(ItemLongClickCallback<T> itemLongClickCallback)
    {
        mItemLongClickCallback = itemLongClickCallback;
    }

    /**
     * 通知item点击回调
     *
     * @param position
     * @param item
     * @param view
     */
    public final void notifyItemClickCallback(int position, T item, View view)
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
    public final boolean notifyItemLongClickCallback(int position, T item, View view)
    {
        if (mItemLongClickCallback != null)
        {
            return mItemLongClickCallback.onItemLongClick(position, item, view);
        }
        return false;
    }

    @Override
    public void notifyDataSetChanged()
    {
        mMapViewPosition.clear();
        super.notifyDataSetChanged();
    }

    @Override
    public void onClick(View view)
    {
        final Integer position = mMapViewPosition.get(view);
        if (position != null)
        {
            onItemClick(position, getItem(position), view);
        }
    }

    @Override
    public void onItemClick(int position, T model, View view)
    {
        notifyItemClickCallback(position, model, view);
    }

    @Override
    public int getCount()
    {
        return getDataHolder().size();
    }

    @Override
    public T getItem(int position)
    {
        return getDataHolder().get(position);
    }

    @Override
    public long getItemId(int position)
    {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        convertView = onGetView(position, convertView, parent);
        mMapViewPosition.put(convertView, position);
        return convertView;
    }

    protected abstract View onGetView(int position, View convertView, ViewGroup parent);

    /**
     * 获得该position对应的itemView
     *
     * @param position
     * @return
     */
    public List<View> getItemView(int position)
    {
        if (mMapViewPosition.isEmpty())
        {
            return null;
        }

        final List<View> list = new ArrayList<>();

        final Set<Entry<View, Integer>> set = mMapViewPosition.entrySet();
        for (Entry<View, Integer> item : set)
        {
            if (Integer.valueOf(position).equals(item.getValue()))
            {
                View view = item.getKey();
                if (view != null && view.getParent() != null)
                {
                    list.add(view);
                }
            }
        }

        return list;
    }

    /**
     * 若重写此方法，则应该把需要刷新的逻辑写在重写方法中，然后不调用super的方法<br>
     * 此方法会在调用{@link #notifyItemViewChanged(int)}方法刷新某一项时候触发
     *
     * @param position
     * @param convertView
     * @param parent
     * @param model
     */
    protected void onUpdateView(int position, View convertView, ViewGroup parent, T model)
    {
        onGetView(position, convertView, parent);
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

    @SuppressWarnings("unchecked")
    public static <V extends View> V get(int id, View convertView)
    {
        SparseArray<View> viewHolder = (SparseArray<View>) convertView.getTag();
        if (viewHolder == null)
        {
            viewHolder = new SparseArray<View>();
            convertView.setTag(viewHolder);
        }
        View childView = viewHolder.get(id);
        if (childView == null)
        {
            childView = convertView.findViewById(id);
            viewHolder.put(id, childView);
        }
        return (V) childView;
    }
}
