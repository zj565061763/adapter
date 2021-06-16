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
        if (callback == null || mListDataChangeCallback.contains(callback)) {
            return;
        }

        mListDataChangeCallback.add(callback);
    }

    @Override
    public void removeDataChangeCallback(DataChangeCallback<T> callback) {
        mListDataChangeCallback.remove(callback);
    }

    @Override
    public void setDataTransform(DataTransform<T> dataTransform) {
        mDataTransform = dataTransform;
    }

    private ListIterator<DataChangeCallback<T>> getListIteratorPrevious() {
        return mListDataChangeCallback.listIterator(mListDataChangeCallback.size());
    }

    //---------- modify start ----------

    @Override
    public void setData(List<? extends T> list) {
        if (list != null) {
            mListData = transformData(list);
        } else {
            mListData = new ArrayList<>();
        }

        final List<T> listCopy = new ArrayList<>(mListData);
        final ListIterator<DataChangeCallback<T>> it = getListIteratorPrevious();
        while (it.hasPrevious()) {
            it.previous().onDataChanged(listCopy);
        }
    }

    @Override
    public boolean add(T data) {
        if (data == null) {
            return false;
        }

        data = transformData(data);

        final int index = size();
        final boolean result = mListData.add(data);

        final List<T> listCopy = new ArrayList<>(1);
        listCopy.add(data);

        final ListIterator<DataChangeCallback<T>> it = getListIteratorPrevious();
        while (it.hasPrevious()) {
            it.previous().onDataAdded(index, listCopy);
        }
        return result;
    }

    @Override
    public void add(int index, T data) {
        if (data == null) {
            return;
        }

        data = transformData(data);
        mListData.add(index, data);

        final List<T> list = new ArrayList<>(1);
        list.add(data);

        final ListIterator<DataChangeCallback<T>> it = getListIteratorPrevious();
        while (it.hasPrevious()) {
            it.previous().onDataAdded(index, list);
        }
    }

    @Override
    public boolean addAll(List<? extends T> list) {
        if (list == null || list.isEmpty()) {
            return false;
        }

        list = transformData(list);

        final int index = size();
        final boolean result = mListData.addAll(list);

        final List<T> listCopy = new ArrayList<>(list);
        final ListIterator<DataChangeCallback<T>> it = getListIteratorPrevious();
        while (it.hasPrevious()) {
            it.previous().onDataAdded(index, listCopy);
        }
        return result;
    }

    @Override
    public boolean addAll(int index, List<? extends T> list) {
        if (list == null || list.isEmpty()) {
            return false;
        }

        list = transformData(list);
        final boolean result = mListData.addAll(index, list);

        final List<T> listCopy = new ArrayList<>(list);
        final ListIterator<DataChangeCallback<T>> it = getListIteratorPrevious();
        while (it.hasPrevious()) {
            it.previous().onDataAdded(index, listCopy);
        }
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

        final ListIterator<DataChangeCallback<T>> it = getListIteratorPrevious();
        while (it.hasPrevious()) {
            it.previous().onDataRemoved(index, model);
        }
        return model;
    }

    @Override
    public void updateData(int index, T data) {
        if (data == null || !isIndexLegal(index)) {
            return;
        }

        data = transformData(data);
        mListData.set(index, data);

        final ListIterator<DataChangeCallback<T>> it = getListIteratorPrevious();
        while (it.hasPrevious()) {
            it.previous().onDataChanged(index, data);
        }
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

    private List<T> transformData(List<? extends T> data) {
        if (mDataTransform == null) {
            return (List<T>) data;
        }

        if (data == null || data.isEmpty()) {
            return (List<T>) data;
        }

        final List<T> listResult = new ArrayList<>(data.size());
        for (T item : data) {
            final T transform = transformData(item);
            listResult.add(transform);
        }
        return listResult;
    }

    private T transformData(T data) {
        if (mDataTransform == null) {
            return data;
        }

        final T transform = mDataTransform.transform(data);
        if (transform == null) {
            return data;
        }
        return transform;
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
}