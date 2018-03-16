package com.fanwe.lib.adapter;

import android.app.Activity;
import android.support.v4.view.PagerAdapter;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

public abstract class FPagerAdapter<T> extends PagerAdapter implements FAdapter<T>
{
    private FAdapterProxy<T> mAdapterProxy;

    private SparseArray<View> mArrCacheView = new SparseArray<>();
    private boolean mAutoCacheView = false;

    private SDItemClickCallback<T> mItemClickCallback;

    public FPagerAdapter(Activity activity)
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
                    FPagerAdapter.this.notifyDataSetChanged();
                }

                @Override
                public void onItemRangeChanged(int positionStart, int itemCount)
                {
                    FPagerAdapter.this.notifyDataSetChanged();
                }

                @Override
                public void onItemRangeInserted(int positionStart, int itemCount)
                {
                    FPagerAdapter.this.notifyDataSetChanged();
                }

                @Override
                public void onItemRangeRemoved(int positionStart, int itemCount)
                {
                    FPagerAdapter.this.notifyDataSetChanged();
                }
            });
        }
        return mAdapterProxy;
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

    public void setItemClickCallback(SDItemClickCallback<T> itemClickCallback)
    {
        this.mItemClickCallback = itemClickCallback;
    }

    public void notifyItemClickCallback(int position, T item, View view)
    {
        if (mItemClickCallback != null)
        {
            mItemClickCallback.onItemClick(position, item, view);
        }
    }

    @Override
    public int getCount()
    {
        return getDataCount();
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

    //----------FAdapter implements start----------

    @Override
    public Activity getActivity()
    {
        return getAdapterProxy().getActivity();
    }

    @Override
    public void setAutoNotifyDataSetChanged(boolean auto)
    {
        getAdapterProxy().setAutoNotifyDataSetChanged(auto);
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
    public void setData(List<T> listModel)
    {
        getAdapterProxy().setData(listModel);
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
