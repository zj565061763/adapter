package com.fanwe.lib.adapter.select;

import com.fanwe.lib.adapter.data.DataHolder;
import com.fanwe.lib.selectmanager.FSelectManager;

import java.util.List;

/**
 * Created by zhengjun on 2018/3/19.
 */
public class AdapterSelectManager<T> extends FSelectManager<T> implements DataHolder.DataChangeCallback<T>
{
    @Override
    public void onSetData(List<T> list)
    {
        setItems(list);
    }

    @Override
    public void onAppendData(T data)
    {
        appendItem(data);
    }

    @Override
    public void onAppendData(List<T> list)
    {
        appendItems(list);
    }

    @Override
    public void onRemoveData(int index, T data)
    {
        removeItem(data);
    }

    @Override
    public void onInsertData(int index, T data)
    {
        insertItem(index, data);
    }

    @Override
    public void onInsertData(int index, List<T> list)
    {
        insertItem(index, list);
    }

    @Override
    public void onUpdateData(int index, T data)
    {
        updateItem(index, data);
    }
}
