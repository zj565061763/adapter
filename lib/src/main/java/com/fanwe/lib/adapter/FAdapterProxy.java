package com.fanwe.lib.adapter;

import android.app.Activity;

import java.util.ArrayList;
import java.util.List;

public class FAdapterProxy<T> implements FAdapter<T>
{
    private List<T> mListModel = new ArrayList<>();
    private Activity mActivity;
    private boolean mNotifyOnDataChanged = true;

    private Callback mCallback;

    public final void setCallback(Callback callback)
    {
        mCallback = callback;
    }

    public final void setActivity(Activity activity)
    {
        mActivity = activity;
    }

    @Override
    public Activity getActivity()
    {
        return mActivity;
    }

    @Override
    public boolean isPositionLegal(int position)
    {
        return position >= 0 && position < getDataCount();
    }

    @Override
    public void setNotifyOnDataChanged(boolean notify)
    {
        mNotifyOnDataChanged = notify;
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
        return mListModel.size();
    }

    @Override
    public int indexOf(T model)
    {
        return mListModel.indexOf(model);
    }

    @Override
    public List<T> getData()
    {
        return mListModel;
    }

    @Override
    public void setData(List<T> list)
    {
        if (list != null)
        {
            mListModel = list;
        } else
        {
            mListModel.clear();
        }
        mCallbackProxy.onDataSetChanged();
    }

    @Override
    public void appendData(T model)
    {
        if (model == null)
        {
            return;
        }

        final int positionStart = mListModel.size();
        final int itemCount = 1;

        mListModel.add(model);
        mCallbackProxy.onItemRangeInserted(positionStart, itemCount);
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
        mCallbackProxy.onItemRangeInserted(positionStart, itemCount);
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

        final int positionStart = position;
        final int itemCount = 1;

        final T model = mListModel.remove(position);
        mCallbackProxy.onItemRangeRemoved(positionStart, itemCount);
        return model;
    }

    @Override
    public void insertData(int position, T model)
    {
        if (model == null)
        {
            return;
        }

        final int positionStart = position;
        final int itemCount = 1;

        mListModel.add(position, model);
        mCallbackProxy.onItemRangeInserted(positionStart, itemCount);
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
        mCallbackProxy.onItemRangeInserted(positionStart, itemCount);
    }

    @Override
    public void updateData(int position, T model)
    {
        if (model == null || !isPositionLegal(position))
        {
            return;
        }

        mListModel.set(position, model);
        if (mNotifyOnDataChanged)
        {
            notifyItemViewChanged(position);
        }
    }

    @Override
    public void notifyItemViewChanged(int position)
    {
        if (!isPositionLegal(position))
        {
            return;
        }

        final int positionStart = position;
        final int itemCount = 1;
        mCallback.onItemRangeChanged(positionStart, itemCount);
    }

    private final Callback mCallbackProxy = new Callback()
    {
        @Override
        public void onDataSetChanged()
        {
            if (mNotifyOnDataChanged)
            {
                mCallback.onDataSetChanged();
            }
        }

        @Override
        public void onItemRangeChanged(int positionStart, int itemCount)
        {
            if (mNotifyOnDataChanged)
            {
                mCallback.onItemRangeChanged(positionStart, itemCount);
            }
        }

        @Override
        public void onItemRangeInserted(int positionStart, int itemCount)
        {
            if (mNotifyOnDataChanged)
            {
                mCallback.onItemRangeInserted(positionStart, itemCount);
            }
        }

        @Override
        public void onItemRangeRemoved(int positionStart, int itemCount)
        {
            if (mNotifyOnDataChanged)
            {
                mCallback.onItemRangeRemoved(positionStart, itemCount);
            }
        }
    };

    public interface Callback
    {
        void onDataSetChanged();

        void onItemRangeChanged(int positionStart, int itemCount);

        void onItemRangeInserted(int positionStart, int itemCount);

        void onItemRangeRemoved(int positionStart, int itemCount);
    }
}
