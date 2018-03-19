package com.fanwe.lib.adapter.callback;

import java.util.List;

public interface AdapterDataChangeCallback<T>
{
    void onSetData(List<T> list);

    void onAppendData(T model);

    void onAppendData(List<T> list);

    void onRemoveData(T model);

    void onInsertData(int position, T model);

    void onInsertData(int position, List<T> list);

    void onUpdateData(int position, T model);
}
