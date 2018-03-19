package com.fanwe.lib.adapter;

import android.app.Activity;

import java.util.List;

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

    //---------- data modify start ----------

    /**
     * 设置数据集并刷新界面
     *
     * @param list
     */
    void setData(List<T> list);

    /**
     * 在末尾添加数据并刷新界面
     *
     * @param model
     */
    void appendData(T model);

    /**
     * 在末尾添加数据集并刷新界面
     *
     * @param list
     */
    void appendData(List<T> list);

    /**
     * 移除数据并刷新界面
     *
     * @param model
     */
    void removeData(T model);

    /**
     * 移除position对应的数据并刷新界面
     *
     * @param position
     * @return
     */
    T removeData(int position);

    /**
     * 插入数据并刷新界面
     *
     * @param position 插入位置
     * @param model
     */
    void insertData(int position, T model);

    /**
     * 插入数据集并刷新界面
     *
     * @param position 插入位置
     * @param list
     */
    void insertData(int position, List<T> list);

    /**
     * 更新position对应的数据并刷新item
     *
     * @param position
     * @param model
     */
    void updateData(int position, T model);

    //---------- data modify end ----------

    /**
     * 刷新position对应的item
     *
     * @param position
     */
    void notifyItemViewChanged(int position);
}
