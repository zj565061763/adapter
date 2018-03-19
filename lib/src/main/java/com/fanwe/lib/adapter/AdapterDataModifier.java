package com.fanwe.lib.adapter;

import java.util.List;

/**
 * Created by zhengjun on 2018/3/19.
 */
public interface AdapterDataModifier<T>
{
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
}
