package com.fanwe.lib.adapter;

import android.app.Activity;

import com.fanwe.lib.adapter.data.DataHolder;

public interface FAdapter<T>
{
    /**
     * 获得Activity
     *
     * @return
     */
    Activity getActivity();

    /**
     * 调用改变数据的方法之后是否刷新UI，默认-true
     *
     * @param notify
     */
    void setNotifyOnDataChanged(boolean notify);

    /**
     * 刷新position对应的item
     *
     * @param position
     */
    void notifyItemViewChanged(int position);

    DataHolder<T> getDataHolder();
}
