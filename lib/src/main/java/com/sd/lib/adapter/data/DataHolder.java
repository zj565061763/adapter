package com.sd.lib.adapter.data;

import java.util.List;

public interface DataHolder<T>
{
    /**
     * 添加数据变化回调
     */
    void addDataChangeCallback(DataChangeCallback<T> callback);

    /**
     * 移除数据变化回调
     */
    void removeDataChangeCallback(DataChangeCallback<T> callback);

    /**
     * 设置数据转换器
     */
    void setDataTransform(DataTransform<T> transform);

    //---------- modify start ----------

    /**
     * 设置数据集
     */
    void setData(List<? extends T> list);

    /**
     * 在末尾添加数据
     */
    boolean addData(T data);

    /**
     * 在末尾添加数据
     */
    boolean addData(List<? extends T> list);

    /**
     * 在index位置添加数据
     */
    void addData(int index, T data);

    /**
     * 在index位置添加数据
     */
    boolean addData(int index, List<? extends T> list);

    /**
     * 移除数据
     */
    boolean removeData(T data);

    /**
     * 移除index位置的数据
     */
    T removeData(int index);

    /**
     * 更新index位置的数据
     */
    void updateData(int index, T data);

    //---------- modify end ----------

    /**
     * 位置在数据集中是否合法
     */
    boolean isIndexLegal(int index);

    /**
     * 返回index位置的数据
     */
    T get(int index);

    /**
     * 获得数据数量
     */
    int size();

    /**
     * 实体在数据集中的位置
     */
    int indexOf(Object data);

    /**
     * 获得数据集
     */
    List<T> getData();

    /**
     * 数据变化回调
     */
    interface DataChangeCallback<T>
    {
        /**
         * 数据集发生变化
         */
        void onDataChanged(List<T> list);

        /**
         * index位置的数据发生变化
         */
        void onDataChanged(int index, T data);

        /**
         * index位置添加了数据
         */
        void onDataAdded(int index, List<T> list);

        /**
         * index位置的数据被删除了
         */
        void onDataRemoved(int index, T data);
    }

    /**
     * 数据转换器
     */
    interface DataTransform<T>
    {
        /**
         * 转换数据
         *
         * @return null-不进行转换
         */
        T transform(T source);
    }
}
