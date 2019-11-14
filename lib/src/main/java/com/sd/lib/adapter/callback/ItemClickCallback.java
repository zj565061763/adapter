package com.sd.lib.adapter.callback;

import android.view.View;

/**
 * {@link OnItemClickCallback}
 *
 * @param <T>
 */
@Deprecated
public interface ItemClickCallback<T>
{
    void onItemClick(int position, T item, View view);
}
