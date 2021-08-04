package com.sd.lib.adapter.data;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

public class ListDataHolder<T> implements DataHolder<T> {
    private List<T> mListData = new ArrayList<>();
    private final List<DataChangeCallback<T>> mListDataChangeCallback = new ArrayList<>();
    private DataTransform<T> mDataTransform;

    @Override
    public void addDataChangeCallback(DataChangeCallback<T> callback) {
        if (callback != null) {
            mListDataChangeCallback.add(callback);
        }
    }

    @Override
    public void removeDataChangeCallback(DataChangeCallback<T> callback) {
        if (callback != null) {
            mListDataChangeCallback.remove(callback);
        }
    }

    @Override
    public void setDataTransform(DataTransform<T> dataTransform) {
        mDataTransform = dataTransform;
    }

    private ListIterator<DataChangeCallback<T>> getListIteratorPrevious() {
        return mListDataChangeCallback.listIterator(mListDataChangeCallback.size());
    }

    private void foreachCallback(ForeachCallback<DataChangeCallback<T>> callback) {
        final ListIterator<DataChangeCallback<T>> it = getListIteratorPrevious();
        while (it.hasPrevious()) {
            callback.onNext(it.previous());
        }
    }

    //---------- modify start ----------

    @Override
    public void setData(List<? extends T> list) {
        if (list == null || list.isEmpty()) {
            mListData = new ArrayList<>();
        } else {
            final List<T> listTransform = transformDataList(list);
            if (list == listTransform) {
                // 数据未转换
                mListData = new ArrayList<>(list);
            } else {
                // 数据转换了
                mListData = listTransform;
            }
        }

        final List<T> listCopy = new ArrayList<>(mListData);
        foreachCallback(new ForeachCallback<DataChangeCallback<T>>() {
            @Override
            public void onNext(DataChangeCallback<T> item) {
                item.onDataChanged(listCopy);
            }
        });
    }

    @Override
    public boolean add(T data) {
        if (data == null) {
            return false;
        }

        final T transform = transformData(data);
        final int index = size();
        final boolean result = mListData.add(transform);

        final List<T> listCopy = new ArrayList<>(1);
        listCopy.add(transform);
        foreachCallback(new ForeachCallback<DataChangeCallback<T>>() {
            @Override
            public void onNext(DataChangeCallback<T> item) {
                item.onDataAdded(index, listCopy);
            }
        });
        return result;
    }

    @Override
    public void add(int index, T data) {
        if (data == null) {
            return;
        }

        final int size = mListData.size();
        if (index > size) {
            addData(data);
            return;
        }

        final T transform = transformData(data);
        mListData.add(index, transform);

        final List<T> listCopy = new ArrayList<>(1);
        listCopy.add(transform);
        foreachCallback(new ForeachCallback<DataChangeCallback<T>>() {
            @Override
            public void onNext(DataChangeCallback<T> item) {
                item.onDataAdded(index, listCopy);
            }
        });
    }

    @Override
    public boolean addAll(List<? extends T> list) {
        if (list == null || list.isEmpty()) {
            return false;
        }

        final List<T> transform = transformDataList(list);
        final int index = size();
        final boolean result = mListData.addAll(transform);

        final List<T> listCopy = new ArrayList<>(transform);
        foreachCallback(new ForeachCallback<DataChangeCallback<T>>() {
            @Override
            public void onNext(DataChangeCallback<T> item) {
                item.onDataAdded(index, listCopy);
            }
        });
        return result;
    }

    @Override
    public boolean addAll(int index, List<? extends T> list) {
        if (list == null || list.isEmpty()) {
            return false;
        }

        final List<T> transform = transformDataList(list);
        final boolean result = mListData.addAll(index, transform);

        final List<T> listCopy = new ArrayList<>(transform);
        foreachCallback(new ForeachCallback<DataChangeCallback<T>>() {
            @Override
            public void onNext(DataChangeCallback<T> item) {
                item.onDataAdded(index, listCopy);
            }
        });
        return result;
    }

    @Override
    public boolean remove(T data) {
        final int position = indexOf(data);
        return removeAt(position) != null;
    }

    @Override
    public T removeAt(int index) {
        if (!isIndexLegal(index)) {
            return null;
        }

        final T model = mListData.remove(index);
        foreachCallback(new ForeachCallback<DataChangeCallback<T>>() {
            @Override
            public void onNext(DataChangeCallback<T> item) {
                item.onDataRemoved(index, model);
            }
        });
        return model;
    }

    @Override
    public void set(int index, T data) {
        if (data == null || !isIndexLegal(index)) {
            return;
        }

        final T transform = transformData(data);
        mListData.set(index, transform);

        foreachCallback(new ForeachCallback<DataChangeCallback<T>>() {
            @Override
            public void onNext(DataChangeCallback<T> item) {
                item.onDataChanged(index, transform);
            }
        });
    }

    //---------- modify end ----------

    @Override
    public boolean isIndexLegal(int index) {
        return index >= 0 && index < size();
    }

    @Override
    public T get(int index) {
        if (isIndexLegal(index)) {
            return mListData.get(index);
        } else {
            return null;
        }
    }

    @Override
    public int size() {
        return mListData.size();
    }

    @Override
    public int indexOf(Object data) {
        if (data == null || mListData.isEmpty()) {
            return -1;
        }

        final List<T> list = new ArrayList<>(mListData);
        for (int i = 0; i < list.size(); i++) {
            final T item = list.get(i);
            if (item != null && item.equals(data)) {
                return i;
            }
        }
        return -1;
    }

    @Override
    public List<T> getData() {
        return mListData;
    }

    private List<T> transformDataList(List<? extends T> list) {
        if (mDataTransform == null) {
            return (List<T>) list;
        }

        if (list == null || list.isEmpty()) {
            return (List<T>) list;
        }

        final List<T> listResult = new ArrayList<>(list.size());
        for (T item : list) {
            final T transform = transformData(item);
            if (transform != null) {
                listResult.add(transform);
            }
        }
        return listResult;
    }

    private T transformData(T data) {
        if (data == null) {
            return null;
        }

        final DataTransform<T> transform = mDataTransform;
        if (transform == null) {
            return data;
        }

        final T transformData = transform.transform(data);
        return transformData != null ? transformData : data;
    }

    @Override
    public boolean addData(T data) {
        return add(data);
    }

    @Override
    public void addData(int index, T data) {
        add(index, data);
    }

    @Override
    public boolean addData(List<? extends T> list) {
        return addAll(list);
    }

    @Override
    public boolean addData(int index, List<? extends T> list) {
        return addAll(index, list);
    }

    @Override
    public boolean removeData(T data) {
        return remove(data);
    }

    @Override
    public T removeData(int index) {
        return removeAt(index);
    }

    @Override
    public void updateData(int index, T data) {
        set(index, data);
    }

    private interface ForeachCallback<T> {
        void onNext(T item);
    }
}