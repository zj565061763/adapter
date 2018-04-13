package com.fanwe.lib.adapter.select;

import com.fanwe.lib.adapter.FAdapter;
import com.fanwe.lib.adapter.data.DataHolder;
import com.fanwe.lib.selectmanager.FSelectManager;

import java.util.List;

class AdapterSelectManager<T> extends FSelectManager<T> implements DataHolder.DataChangeCallback<T>
{
    private FAdapter<T> mAdapter;

    public AdapterSelectManager(FAdapter<T> adapter)
    {
        mAdapter = adapter;
        mAdapter.getDataHolder().addDataChangeCallback(this);
    }

    @Override
    protected void onNormal(T item)
    {
        super.onNormal(item);

        if (item instanceof FSelectManager.Selectable)
        {
            FSelectManager.Selectable selectable = (FSelectManager.Selectable) item;
            selectable.setSelected(false);
        }
        final int index = mAdapter.getDataHolder().indexOf(item);
        mAdapter.notifyItemViewChanged(index);
    }

    @Override
    protected void onSelected(T item)
    {
        super.onSelected(item);

        if (item instanceof FSelectManager.Selectable)
        {
            FSelectManager.Selectable selectable = (FSelectManager.Selectable) item;
            selectable.setSelected(true);
        }
        final int index = mAdapter.getDataHolder().indexOf(item);
        mAdapter.notifyItemViewChanged(index);
    }

    @Override
    public void onDataChanged(List<T> list)
    {
        setItems(list);
    }

    @Override
    public void onDataChanged(int index, T data)
    {
        updateItem(index, data);
    }

    @Override
    public void onDataAppended(int index, List<T> list)
    {
        appendItems(list);
    }

    @Override
    public void onDataInserted(int index, List<T> list)
    {
        insertItem(index, list);
    }

    @Override
    public void onDataRemoved(int index, T data)
    {
        removeItem(data);
    }
}
