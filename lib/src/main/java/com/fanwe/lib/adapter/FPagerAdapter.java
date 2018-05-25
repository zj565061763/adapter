/*
 * Copyright (C) 2017 zhengjun, fanwe (http://www.fanwe.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.fanwe.lib.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;

import com.fanwe.lib.adapter.callback.ItemClickCallback;
import com.fanwe.lib.adapter.data.DataHolder;

public abstract class FPagerAdapter<T> extends PagerAdapter implements Adapter<T>
{
    private AdapterProxy<T> mAdapterProxy;

    private SparseArray<View> mArrCacheView = new SparseArray<>();
    private boolean mAutoCacheView = false;

    private ItemClickCallback<T> mItemClickCallback;

    public FPagerAdapter()
    {
    }

    public FPagerAdapter(Context context)
    {
        setContext(context);
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
        setContext(container.getContext());

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

    //----------Adapter implements start----------

    private AdapterProxy<T> getAdapterProxy()
    {
        if (mAdapterProxy == null)
        {
            mAdapterProxy = new AdapterProxy<>(new AdapterProxy.Callback()
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

    //----------Adapter implements end----------
}
