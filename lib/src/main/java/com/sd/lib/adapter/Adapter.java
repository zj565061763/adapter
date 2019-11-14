package com.sd.lib.adapter;

import android.content.Context;

import com.sd.lib.adapter.callback.CallbackHolder;
import com.sd.lib.adapter.data.DataHolder;

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
     * 设置数据变更后刷新ui的方式，默认{@link NotifyDataChangeMode#Smart}
     *
     * @param mode {@link NotifyDataChangeMode}
     */
    void setNotifyDataChangeMode(NotifyDataChangeMode mode);

    /**
     * 刷新position对应的item
     *
     * @param position
     */
    void notifyItemViewChanged(int position);

    /**
     * 通知数据变更
     */
    void notifyDataSetChanged();

    /**
     * 返回数据持有者对象
     *
     * @return
     */
    DataHolder<T> getDataHolder();

    /**
     * 回调持有者有对象
     *
     * @return
     */
    CallbackHolder<T> getCallbackHolder();

    enum NotifyDataChangeMode
    {
        /**
         * 不刷新
         */
        None,
        /**
         * 智能刷新需要刷新的项
         */
        Smart,
        /**
         * 刷新全部可见的项
         */
        All
    }
}
