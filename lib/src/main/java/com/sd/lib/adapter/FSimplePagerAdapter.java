package com.sd.lib.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public abstract class FSimplePagerAdapter<T> extends FPagerAdapter<T>
{
    @Override
    public View getView(ViewGroup container, int position)
    {
        final int layoutId = getLayoutId(position, container);
        if (layoutId != 0)
        {
            final View view = LayoutInflater.from(container.getContext()).inflate(layoutId, container, false);
            final T model = getDataHolder().get(position);
            onBindData(position, view, container, model);
            return view;
        }
        return null;
    }

    public abstract int getLayoutId(int position, ViewGroup parent);

    public abstract void onBindData(int position, View convertView, ViewGroup parent, T model);
}
