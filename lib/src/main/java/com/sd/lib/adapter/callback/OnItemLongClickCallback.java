package com.sd.lib.adapter.callback;

import android.view.View;

public interface OnItemLongClickCallback<T>
{
    boolean onItemLongClick(T item, View view);
}
