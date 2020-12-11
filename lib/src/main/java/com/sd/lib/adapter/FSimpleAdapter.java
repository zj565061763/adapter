package com.sd.lib.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public abstract class FSimpleAdapter<T> extends FBaseAdapter<T>
{
    public FSimpleAdapter()
    {
    }

    public FSimpleAdapter(Context context)
    {
        super(context);
    }

    @Override
    protected View onGetView(int position, View convertView, ViewGroup parent)
    {
        if (convertView == null)
        {
            convertView = onCreateItemView(position, convertView, parent);
            if (convertView == null)
                throw new NullPointerException();

            onInitItemView(position, convertView, parent);
        }
        onBindData(position, convertView, parent, getItem(position));
        return convertView;
    }

    public View onCreateItemView(int position, View convertView, ViewGroup parent)
    {
        final int layoutId = getLayoutId(position, convertView, parent);
        if (layoutId != 0)
            return LayoutInflater.from(getContext()).inflate(layoutId, parent, false);

        return null;
    }

    public void onInitItemView(int position, View convertView, ViewGroup parent)
    {
    }

    /**
     * 返回布局Id
     *
     * @param position
     * @param convertView
     * @param parent
     * @return
     */
    public abstract int getLayoutId(int position, View convertView, ViewGroup parent);

    /**
     * 绑定数据
     *
     * @param position
     * @param convertView
     * @param parent
     * @param model
     */
    public abstract void onBindData(int position, View convertView, ViewGroup parent, T model);
}
