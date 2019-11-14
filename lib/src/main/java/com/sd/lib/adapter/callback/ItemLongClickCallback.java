package com.sd.lib.adapter.callback;

import android.view.View;

/**
 * {@link OnItemLongClickCallback}
 *
 * @param <T>
 */
@Deprecated
public interface ItemLongClickCallback<T>
{
    boolean onItemLongClick(int position, T item, View view);
}
