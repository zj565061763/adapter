/*
 * Copyright (C) 2017 zhengjun, fanwe (http://www.fanwe.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.fanwe.lib.adapter.data;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

/**
 * Created by zhengjun on 2018/3/19.
 */
public class ListDataHolder<T> implements DataHolder<T>
{
    private List<T> mListData = new ArrayList<>();
    private final List<DataChangeCallback<T>> mListDataChangeCallback = new ArrayList<>();

    @Override
    public void addDataChangeCallback(DataChangeCallback<T> callback)
    {
        if (callback == null || mListDataChangeCallback.contains(callback))
        {
            return;
        }
        mListDataChangeCallback.add(callback);
    }

    @Override
    public void removeDataChangeCallback(DataChangeCallback<T> callback)
    {
        mListDataChangeCallback.remove(callback);
    }

    private ListIterator<DataChangeCallback<T>> getListIteratorPrevious()
    {
        return mListDataChangeCallback.listIterator(mListDataChangeCallback.size());
    }

    //---------- modify start ----------

    @Override
    public void setData(List<T> list)
    {
        if (list != null)
            mListData = list;
        else
            mListData.clear();

        final ListIterator<DataChangeCallback<T>> it = getListIteratorPrevious();
        while (it.hasPrevious())
        {
            it.previous().onDataChanged(list);
        }
    }

    @Override
    public void addData(T data)
    {
        if (data == null)
            return;

        final int index = size();
        mListData.add(data);

        final List<T> list = new ArrayList<>(1);
        list.add(data);

        final ListIterator<DataChangeCallback<T>> it = getListIteratorPrevious();
        while (it.hasPrevious())
        {
            it.previous().onDataAdded(index, list);
        }
    }

    @Override
    public void addData(List<T> list)
    {
        if (list == null || list.isEmpty())
            return;

        final int index = size();
        mListData.addAll(list);

        final ListIterator<DataChangeCallback<T>> it = getListIteratorPrevious();
        while (it.hasPrevious())
        {
            it.previous().onDataAdded(index, list);
        }
    }

    @Override
    public void addData(int index, T data)
    {
        if (data == null)
            return;

        mListData.add(index, data);

        final List<T> list = new ArrayList<>(1);
        list.add(data);

        final ListIterator<DataChangeCallback<T>> it = getListIteratorPrevious();
        while (it.hasPrevious())
        {
            it.previous().onDataAdded(index, list);
        }
    }

    @Override
    public void addData(int index, List<T> list)
    {
        if (list == null || list.isEmpty())
            return;

        mListData.addAll(index, list);

        final ListIterator<DataChangeCallback<T>> it = getListIteratorPrevious();
        while (it.hasPrevious())
        {
            it.previous().onDataAdded(index, list);
        }
    }

    @Override
    public void removeData(T data)
    {
        final int position = mListData.indexOf(data);
        removeData(position);
    }

    @Override
    public T removeData(int index)
    {
        if (!isIndexLegal(index))
            return null;

        final T model = mListData.remove(index);

        final ListIterator<DataChangeCallback<T>> it = getListIteratorPrevious();
        while (it.hasPrevious())
        {
            it.previous().onDataRemoved(index, model);
        }
        return model;
    }

    @Override
    public void updateData(int index, T data)
    {
        if (data == null || !isIndexLegal(index))
            return;

        mListData.set(index, data);

        final ListIterator<DataChangeCallback<T>> it = getListIteratorPrevious();
        while (it.hasPrevious())
        {
            it.previous().onDataChanged(index, data);
        }
    }

    //---------- modify end ----------

    @Override
    public boolean isIndexLegal(int index)
    {
        return index >= 0 && index < size();
    }

    @Override
    public T get(int index)
    {
        if (isIndexLegal(index))
            return mListData.get(index);
        else
            return null;
    }

    @Override
    public int size()
    {
        return mListData.size();
    }

    @Override
    public int indexOf(T data)
    {
        return mListData.indexOf(data);
    }

    @Override
    public List<T> getData()
    {
        return mListData;
    }
}
