package com.fanwe.lib.adapter.callback;

import android.view.View;

public interface ItemLongClickCallback<T>
{
    boolean onItemLongClick(T item, View view);
}
