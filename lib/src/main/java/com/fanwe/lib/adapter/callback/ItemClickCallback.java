package com.fanwe.lib.adapter.callback;

import android.view.View;

public interface ItemClickCallback<T>
{
    void onItemClick(int position, T item, View view);
}
