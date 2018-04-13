package com.fanwe.lib.adapter;

import android.app.Activity;

import com.fanwe.lib.adapter.data.DataHolder;
import com.fanwe.lib.adapter.data.ListDataHolder;

import java.util.List;

public class FAdapterProxy<T> implements FAdapter<T>
{
    private DataHolder<T> mDataHolder;
    private Activity mActivity;
    private boolean mNotifyOnDataChanged = true;

    private Callback mCallback;

    final void setCallback(Callback callback)
    {
        mCallback = callback;
    }

    final void setActivity(Activity activity)
    {
        mActivity = activity;
    }

    @Override
    public Activity getActivity()
    {
        return mActivity;
    }

    @Override
    public void setNotifyOnDataChanged(boolean notify)
    {
        mNotifyOnDataChanged = notify;
    }

    @Override
    public void notifyItemViewChanged(int position)
    {
        if (!getDataHolder().isIndexLegal(position))
        {
            return;
        }

        final int positionStart = position;
        final int itemCount = 1;
        mCallback.onItemRangeChanged(positionStart, itemCount);
    }

    @Override
    public DataHolder<T> getDataHolder()
    {
        if (mDataHolder == null)
        {
            mDataHolder = new ListDataHolder<>();
            mDataHolder.addDataChangeCallback(new DataHolder.DataChangeCallback<T>()
            {
                @Override
                public void onDataChanged(List<T> list)
                {
                    if (mNotifyOnDataChanged)
                    {
                        mCallback.onDataSetChanged();
                    }
                }

                @Override
                public void onDataChanged(int index, T data)
                {
                    if (mNotifyOnDataChanged)
                    {
                        notifyItemViewChanged(index);
                    }
                }

                @Override
                public void onDataAppended(int index, List<T> list)
                {
                    if (mNotifyOnDataChanged)
                    {
                        mCallback.onItemRangeInserted(index, list.size());
                    }
                }

                @Override
                public void onDataInserted(int index, List<T> list)
                {
                    if (mNotifyOnDataChanged)
                    {
                        mCallback.onItemRangeInserted(index, list.size());
                    }
                }

                @Override
                public void onDataRemoved(int index, T data)
                {
                    if (mNotifyOnDataChanged)
                    {
                        mCallback.onItemRangeRemoved(index, 1);
                    }
                }
            });
        }
        return mDataHolder;
    }

    public interface Callback
    {
        void onDataSetChanged();

        void onItemRangeChanged(int index, int itemCount);

        void onItemRangeInserted(int index, int itemCount);

        void onItemRangeRemoved(int index, int itemCount);
    }
}
