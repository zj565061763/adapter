package com.fanwe.lib.adapter;

import android.app.Activity;
import android.support.v4.view.PagerAdapter;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;

import com.fanwe.lib.adapter.callback.ItemClickCallback;
import com.fanwe.lib.adapter.data.DataHolder;

public abstract class FPagerAdapter<T> extends PagerAdapter implements FAdapter<T>
{
    private FAdapterProxy<T> mAdapterProxy;

    private SparseArray<View> mArrCacheView = new SparseArray<>();
    private boolean mAutoCacheView = false;

    private ItemClickCallback<T> mItemClickCallback;

    public FPagerAdapter(Activity activity)
    {
        getAdapterProxy().setActivity(activity);
    }

    /**
     * 设置是否自动缓存view
     *
     * @param autoSaveView
     */
    public void setAutoCacheView(boolean autoSaveView)
    {
        if (mAutoCacheView != autoSaveView)
        {
            mAutoCacheView = autoSaveView;
            if (!autoSaveView)
            {
                mArrCacheView.clear();
            }
        }
    }

    private void saveCacheViewIfNeed(int position, View view)
    {
        if (view == null || !mAutoCacheView)
        {
            return;
        }
        mArrCacheView.put(position, view);
    }

    /**
     * 移除缓存的view
     *
     * @param position
     * @return
     */
    public View removeCacheView(int position)
    {
        View view = mArrCacheView.get(position);
        if (view != null)
        {
            mArrCacheView.remove(position);
        }
        return view;
    }

    /**
     * 清空缓存的view
     */
    public void clearCacheView()
    {
        mArrCacheView.clear();
    }

    public void setItemClickCallback(ItemClickCallback<T> itemClickCallback)
    {
        this.mItemClickCallback = itemClickCallback;
    }

    public final void notifyItemClickCallback(T item, View view)
    {
        if (mItemClickCallback != null)
        {
            final int position = getDataHolder().indexOf(item);
            mItemClickCallback.onItemClick(position, item, view);
        }
    }

    @Override
    public int getCount()
    {
        return getDataHolder().size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object)
    {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position)
    {
        View view = mArrCacheView.get(position);
        if (view == null)
        {
            view = getView(container, position);
            saveCacheViewIfNeed(position, view);
        }
        container.addView(view);
        return view;
    }

    @Override
    public int getItemPosition(Object object)
    {
        return POSITION_NONE;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object)
    {
        container.removeView((View) object);
    }

    public abstract View getView(ViewGroup container, int position);

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
                    FPagerAdapter.this.notifyDataSetChanged();
                }

                @Override
                public void onItemRangeChanged(int index, int itemCount)
                {
                    FPagerAdapter.this.notifyDataSetChanged();
                }

                @Override
                public void onItemRangeInserted(int index, int itemCount)
                {
                    FPagerAdapter.this.notifyDataSetChanged();
                }

                @Override
                public void onItemRangeRemoved(int index, int itemCount)
                {
                    FPagerAdapter.this.notifyDataSetChanged();
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
