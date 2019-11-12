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

    public static abstract class Model<T>
    {
        T mSource;

        protected Model()
        {
        }

        public T getSource()
        {
            return mSource;
        }

        /**
         * 把源对象转为当前类型的对象
         *
         * @param source
         * @return
         */
        public Model<T> transform(T source)
        {
            if (source == null)
                return null;

            final Class<?> clazz = getClass();

            try
            {
                final Model<T> model = (Model<T>) clazz.newInstance();
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

        /**
         * 把源对象转为当前类型的对象
         *
         * @param source
         * @return
         */
        public List<Model<T>> transform(List<T> source)
        {
            if (source == null || source.isEmpty())
                return null;

            final List<Model<T>> list = new ArrayList<>();
            for (T item : source)
            {
                final Model<T> model = transform(item);
                if (model != null)
                    list.add(model);
            }
            return list;
        }
    }
}
