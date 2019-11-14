package com.sd.lib.adapter.callback;

import android.view.View;

public class CallbackHolder<T>
{
    private OnItemClickCallback<T> mOnItemClickCallback;
    private OnItemLongClickCallback<T> mOnItemLongClickCallback;

    /**
     * 设置点击回调
     *
     * @param callback
     */
    public void setOnItemClickCallback(OnItemClickCallback<T> callback)
    {
        mOnItemClickCallback = callback;
    }

    /**
     * 设置长按回调
     *
     * @param callback
     */
    public void setOnItemLongClickCallback(OnItemLongClickCallback<T> callback)
    {
        mOnItemLongClickCallback = callback;
    }

    /**
     * 通知item点击回调
     *
     * @param item
     * @param view
     */
    public void notifyItemClickCallback(T item, View view)
    {
        if (mOnItemClickCallback != null)
            mOnItemClickCallback.onItemClick(item, view);
    }

    /**
     * 通知item长按回调
     *
     * @param item
     * @param view
     * @return
     */
    public boolean notifyItemLongClickCallback(T item, View view)
    {
        if (mOnItemLongClickCallback != null)
            return mOnItemLongClickCallback.onItemLongClick(item, view);
        return false;
    }
}
