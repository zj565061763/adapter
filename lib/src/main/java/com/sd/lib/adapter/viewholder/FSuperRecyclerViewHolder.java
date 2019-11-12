package com.sd.lib.adapter.viewholder;

import android.view.View;

import java.util.ArrayList;
import java.util.List;

public abstract class FSuperRecyclerViewHolder<T> extends FRecyclerViewHolder<T>
{
    public FSuperRecyclerViewHolder(View itemView)
    {
        super(itemView);
    }

    public static class Model<T>
    {
        T mSource;

        public T getSource()
        {
            return mSource;
        }

        public static <M extends Model> Object transform(Object source, Class<M> clazz)
        {
            if (source == null)
                return null;

            try
            {
                final M model = clazz.newInstance();
                model.mSource = source;
                return model;
            } catch (IllegalAccessException e)
            {
                e.printStackTrace();
            } catch (InstantiationException e)
            {
                e.printStackTrace();
            }
            return null;
        }

        public static <M extends Model> List<Object> transform(List<? extends Object> source, Class<M> clazz)
        {
            if (source == null || source.isEmpty())
                return null;

            final List<Object> list = new ArrayList<>();
            for (Object item : source)
            {
                final Object model = transform(item, clazz);
                if (model != null)
                    list.add(model);
            }
            return list;
        }
    }
}
