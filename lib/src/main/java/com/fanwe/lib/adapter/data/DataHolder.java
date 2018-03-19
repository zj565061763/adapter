package com.fanwe.lib.adapter.data;

import java.util.List;

/**
 * Created by zhengjun on 2018/3/19.
 */
public interface DataHolder<T>
{
    void addDataChangeCallback(DataChangeCallback<T> callback);

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
     * @param model
     */
    void appendData(T model);

    /**
     * 在末尾添加数据集
     *
     * @param list
     */
    void appendData(List<T> list);

    /**
     * 移除数据
     *
     * @param model
     */
    void removeData(T model);

    /**
     * 移除该位置对应的数据
     *
     * @param index
     * @return
     */
    T removeData(int index);

    /**
     * 插入数据
     *
     * @param index 插入位置
     * @param model
     */
    void insertData(int index, T model);

    /**
     * 插入数据集
     *
     * @param index 插入位置
     * @param list
     */
    void insertData(int index, List<T> list);

    /**
     * 更新该位置对应的数据
     *
     * @param index
     * @param model
     */
    void updateData(int index, T model);

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

    interface DataChangeCallback<T>
    {
        void onSetData(List<T> list);

        void onAppendData(T model);

        void onAppendData(List<T> list);

        void onRemoveData(int index, T model);

        void onInsertData(int index, T model);

        void onInsertData(int index, List<T> list);

        void onUpdateData(int index, T model);
    }
}
