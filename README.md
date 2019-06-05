# About
对adapter抽象封装，让使用RecyclerView，ListView等不同view的时候，用的Adapter的接口是一样的

# Gradle
[![](https://jitpack.io/v/zj565061763/adapter.svg)](https://jitpack.io/#zj565061763/adapter)

# Adapter接口
```java
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
```

# DataHolder接口
```java
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
     * @return
     */
    boolean addData(T data);

    /**
     * 在末尾添加数据
     *
     * @param list
     * @return
     */
    boolean addData(List<T> list);

    /**
     * 在index位置添加数据
     *
     * @param index
     * @param data
     */
    void addData(int index, T data);

    /**
     * 在index位置添加数据
     *
     * @param index
     * @param list
     * @return
     */
    boolean addData(int index, List<T> list);

    /**
     * 移除数据
     *
     * @param data
     * @return
     */
    boolean removeData(T data);

    /**
     * 移除index位置的数据
     *
     * @param index
     * @return
     */
    T removeData(int index);

    /**
     * 更新index位置的数据
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
     * 返回index位置的数据
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
         * index位置添加了数据
         *
         * @param index
         * @param list
         */
        void onDataAdded(int index, List<T> list);

        /**
         * index位置的数据被删除了
         *
         * @param index
         * @param data
         */
        void onDataRemoved(int index, T data);
    }
}
```