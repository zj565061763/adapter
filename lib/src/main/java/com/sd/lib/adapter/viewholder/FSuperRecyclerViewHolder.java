package com.sd.lib.adapter.viewholder;

import android.view.View;

import com.sd.lib.adapter.annotation.ASuperViewHolder;

import java.util.ArrayList;
import java.util.List;

public abstract class FSuperRecyclerViewHolder<T> extends FRecyclerViewHolder<T>
{
    public FSuperRecyclerViewHolder(View itemView)
    {
        super(itemView);
    }

    /**
     * 返回{@link ASuperViewHolder}注解
     *
     * @param clazz
     * @param <T>
     * @return
     */
    public static <T extends FSuperRecyclerViewHolder> ASuperViewHolder getAnnotation(Class<T> clazz)
    {
        if (clazz == null)
            throw new IllegalArgumentException("clazz is null");

        if (clazz == FSuperRecyclerViewHolder.class)
            throw new IllegalArgumentException("clazz must not be " + FSuperRecyclerViewHolder.class.getName());

        while (true)
        {
            ASuperViewHolder annotation = clazz.getAnnotation(ASuperViewHolder.class);
            if (annotation != null)
                return annotation;

            if (clazz == FSuperRecyclerViewHolder.class)
                break;

            clazz = (Class<T>) clazz.getSuperclass();
        }

        throw new IllegalArgumentException(ASuperViewHolder.class.getSimpleName() + " annotation was not found in " + clazz.getName());
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
        public final Model<T> transform(T source)
        {
            if (source == null)
                return null;

            mSource = source;
            return this;
        }

        /**
         * 把源对象转为当前类型的对象
         *
         * @param source
         * @return
         */
        public final List<Model<T>> transform(List<T> source)
        {
            if (source == null || source.isEmpty())
                return null;

            final List<Model<T>> list = new ArrayList<>();
            for (T item : source)
            {
                final Model<T> model = newInstance();
                if (model == null)
                    throw new RuntimeException("newInstance() return mull");

                if (model.getClass() != getClass())
                    throw new RuntimeException("newInstance() must return instance of " + getClass().getName());

                model.mSource = item;
                list.add(model);
            }
            return list;
        }

        protected Model<T> newInstance()
        {
            try
            {
                final Model<T> model = (Model<T>) getClass().newInstance();
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
    }
}
