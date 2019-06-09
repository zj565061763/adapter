package com.sd.lib.adapter.viewholder;

import android.view.View;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

public abstract class FSuperRecyclerViewHolder<T> extends FRecyclerViewHolder<T>
{
    public FSuperRecyclerViewHolder(View itemView)
    {
        super(itemView);
    }

    public Class<T> getModelClass()
    {
        final ParameterizedType parameterizedType = (ParameterizedType) getClass().getGenericSuperclass();
        final Type[] types = parameterizedType.getActualTypeArguments();
        if (types != null && types.length > 0)
        {
            return (Class<T>) types[0];
        } else
        {
            throw new RuntimeException("generic type not found");
        }
    }
}
