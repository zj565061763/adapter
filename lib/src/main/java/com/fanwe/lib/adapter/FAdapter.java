package com.fanwe.lib.adapter;

import android.app.Activity;

import java.util.List;

public interface FAdapter<T> extends AdapterDataModifier<T>
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
     * 位置在数据集中是否合法
     *
     * @param position
     * @return
     */
    boolean isPositionLegal(int position);

    /**
     * 获得某个位置对应的数据
     *
     * @param position
     * @return
     */
    T getData(int position);

    /**
     * 获得数据数量
     *
     * @return
     */
    int getDataCount();

    /**
     * 实体在数据集中的位置
     *
     * @param model
     * @return
     */
    int indexOf(T model);

    /**
     * 获得数据集
     *
     * @return
     */
    List<T> getData();

    /**
     * 刷新position对应的item
     *
     * @param position
     */
    void notifyItemViewChanged(int position);
}
