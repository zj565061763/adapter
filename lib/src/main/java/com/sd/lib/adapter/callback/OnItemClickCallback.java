package com.sd.lib.adapter.callback;

import android.view.View;

public interface OnItemClickCallback<T>
{
    void onItemClick(T item, View view);
}
