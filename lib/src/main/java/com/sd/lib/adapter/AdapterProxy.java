package com.sd.lib.adapter;

import android.content.Context;

import com.sd.lib.adapter.data.DataHolder;
import com.sd.lib.adapter.data.ListDataHolder;

import java.util.List;

public class AdapterProxy<T> implements Adapter<T>
{
    private Context mContext;
    private DataHolder<T> mDataHolder;
    private NotifyDataChangeMode mNotifyDataChangeMode = NotifyDataChangeMode.Smart;

    private final Callback mCallback;

    public AdapterProxy(Callback callback)
    {
        if (callback == null)
            throw new NullPointerException("callback is null");

        mCallback = callback;
    }

    @Override
    public void setContext(Context context)
    {
        if (context == null)
            throw new NullPointerException("context is null");

        mContext = context;
    }

    @Override
    public Context getContext()
    {
        return mContext;
    }

    @Override
    public void setNotifyDataChangeMode(NotifyDataChangeMode mode)
    {
        if (mode != null)
            mNotifyDataChangeMode = mode;
    }

    @Override
    public void notifyItemViewChanged(int position)
    {
        if (!getDataHolder().isIndexLegal(position))
            return;

        if (mNotifyDataChangeMode == NotifyDataChangeMode.All)
        {
            mCallback.onDataSetChanged();
        } else if (mNotifyDataChangeMode == NotifyDataChangeMode.Smart)
        {
            mCallback.onItemRangeChanged(position, 1);
        }
    }

    @Override
    public void notifyDataSetChanged()
    {
        mCallback.onDataSetChanged();
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
                    if (mNotifyDataChangeMode != NotifyDataChangeMode.None)
                        mCallback.onDataSetChanged();
                }

                @Override
                public void onDataChanged(int index, T data)
                {
                    if (mNotifyDataChangeMode == NotifyDataChangeMode.All)
                    {
                        mCallback.onDataSetChanged();
                    } else if (mNotifyDataChangeMode == NotifyDataChangeMode.Smart)
                    {
                        notifyItemViewChanged(index);
                    }
                }

                @Override
                public void onDataAdded(int index, List<T> list)
                {
                    if (mNotifyDataChangeMode == NotifyDataChangeMode.All)
                    {
                        mCallback.onDataSetChanged();
                    } else if (mNotifyDataChangeMode == NotifyDataChangeMode.Smart)
                    {
                        mCallback.onItemRangeInserted(index, list.size());
                    }
                }

                @Override
                public void onDataRemoved(int index, T data)
                {
                    if (mNotifyDataChangeMode == NotifyDataChangeMode.All)
                    {
                        mCallback.onDataSetChanged();
                    } else if (mNotifyDataChangeMode == NotifyDataChangeMode.Smart)
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
