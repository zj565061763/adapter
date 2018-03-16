package com.fanwe.lib.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public abstract class FSimpleAdapter<T> extends FBaseAdapter<T>
{
    public FSimpleAdapter(Activity activity)
    {
        super(activity);
    }

    @Override
    protected View onGetView(int position, View convertView, ViewGroup parent)
    {
        if (convertView == null)
        {
            int layoutId = getLayoutId(position, convertView, parent);
            if (layoutId != 0)
            {
                convertView = LayoutInflater.from(getActivity()).inflate(layoutId, parent, false);
                onInit(position, convertView, parent);
            }
        }
        bindData(position, convertView, parent, getItem(position));
        return convertView;
    }

    @Override
    protected void onUpdateView(int position, View convertView, ViewGroup parent, T model)
    {
        bindData(position, convertView, parent, model);
    }

    public abstract int getLayoutId(int position, View convertView, ViewGroup parent);

    public void onInit(int position, View convertView, ViewGroup parent)
    {

    }

    public abstract void bindData(int position, View convertView, ViewGroup parent, T model);

}
