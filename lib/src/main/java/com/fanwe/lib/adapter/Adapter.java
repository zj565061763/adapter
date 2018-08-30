package com.fanwe.lib.adapter;

import android.content.Context;

import com.fanwe.lib.adapter.data.DataHolder;

public interface Adapter<T>
{
    /**
     * 设置Context对象
     *
     * @param context
     */
    void setContext(Context context);

    /**
     * 返回Context对象
     *
     * @return
     */
    Context getContext();

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

    /**
     * 返回数据持有者对象
     *
     * @return
     */
    DataHolder<T> getDataHolder();
}
