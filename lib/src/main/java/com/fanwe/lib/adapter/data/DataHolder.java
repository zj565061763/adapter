package com.fanwe.lib.adapter.data;

import java.util.List;

/**
 * Created by zhengjun on 2018/3/19.
 */
public interface DataHolder<T>
{
    /**
     * 添加数据变化回调
     *
     * @param callback
     */
    void addDataChangeCallback(DataChangeCallback<T> callback);

    /**
     * 移除数据变化回调
     *
     * @param callback
     */
    void removeDataChangeCallback(DataChangeCallback<T> callback);

    //---------- modify start ----------

    /**
     * 设置数据集
     *
     * @param list
     */
    void setData(List<T> list);

    /**
     * 在末尾添加数据
     *
     * @param data
     */
    void appendData(T data);

    /**
     * 在末尾添加数据集
     *
     * @param list
     */
    void appendData(List<T> list);

    /**
     * 插入数据
     *
     * @param index 插入位置
     * @param data
     */
    void insertData(int index, T data);

    /**
     * 插入数据集
     *
     * @param index 插入位置
     * @param list
     */
    void insertData(int index, List<T> list);

    /**
     * 移除数据
     *
     * @param data
     */
    void removeData(T data);

    /**
     * 移除该位置对应的数据
     *
     * @param index
     * @return
     */
    T removeData(int index);

    /**
     * 更新该位置对应的数据
     *
     * @param index
     * @param data
     */
    void updateData(int index, T data);

    //---------- modify end ----------

    /**
     * 位置在数据集中是否合法
     *
     * @param index
     * @return
     */
    boolean isIndexLegal(int index);

    /**
     * 获得某个位置对应的数据
     *
     * @param index
     * @return
     */
    T get(int index);

    /**
     * 获得数据数量
     *
     * @return
     */
    int size();

    /**
     * 实体在数据集中的位置
     *
     * @param data
     * @return
     */
    int indexOf(T data);

    /**
     * 获得数据集
     *
     * @return
     */
    List<T> getData();

    /**
     * 数据变化回调
     *
     * @param <T>
     */
    interface DataChangeCallback<T>
    {
        /**
         * 数据集发生变化
         *
         * @param list
         */
        void onDataChanged(List<T> list);

        /**
         * index位置的数据发生变化
         *
         * @param index
         * @param data
         */
        void onDataChanged(int index, T data);

        /**
         * 末尾添加了数据
         *
         * @param index
         * @param list
         */
        void onDataAppended(int index, List<T> list);

        /**
         * index位置插入了数据
         *
         * @param index
         * @param list
         */
        void onDataInserted(int index, List<T> list);

        /**
         * index位置的数据被删除了
         *
         * @param index
         * @param data
         */
        void onDataRemoved(int index, T data);
    }
}
