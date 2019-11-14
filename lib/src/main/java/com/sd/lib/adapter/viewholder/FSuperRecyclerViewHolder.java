package com.sd.lib.adapter.viewholder;

import android.view.View;

import com.sd.lib.adapter.callback.CallbackHolder;

import java.util.ArrayList;
import java.util.List;

public abstract class FSuperRecyclerViewHolder<T> extends FRecyclerViewHolder<T>
{
    private CallbackHolder<T> mCallbackHolder;

    public FSuperRecyclerViewHolder(View itemView)
    {
        super(itemView);
    }

    /**
     * {@link CallbackHolder}
     *
     * @return
     */
    public final CallbackHolder<T> getCallbackHolder()
    {
        if (mCallbackHolder == null)
            mCallbackHolder = new CallbackHolder<>();
        return mCallbackHolder;
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
