package com.fanwe.lib.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.fanwe.lib.adapter.viewholder.FViewHolder;

public abstract class FViewHolderAdapter<T> extends FBaseAdapter<T>
{
    public FViewHolderAdapter(Activity activity)
    {
        super(activity);
    }

    @Override
    protected final void onUpdateView(int position, View convertView, ViewGroup parent, T model)
    {
        FViewHolder<T> holder = (FViewHolder<T>) convertView.getTag();
        holder.onUpdateData(position, convertView, parent, model);
        onUpdateData(position, convertView, parent, model, holder);
    }

    /**
     * 当调用item刷新的时候会触发此方法
     *
     * @param position
     * @param convertView
     * @param parent
     * @param model
     * @param holder
     */
    protected void onUpdateData(int position, View convertView, ViewGroup parent, T model, FViewHolder<T> holder)
    {
        onBindData(position, convertView, parent, model, holder);
    }

    @Override
    public final View onGetView(int position, View convertView, ViewGroup parent)
    {
        FViewHolder<T> holder = null;
        if (convertView == null)
        {
            holder = onCreateVHolder(position, convertView, parent);
            holder.setAdapter(this);

            int layoutId = holder.getLayoutId(position, convertView, parent);
            convertView = LayoutInflater.from(getActivity()).inflate(layoutId, parent);
            holder.setItemView(convertView);
            holder.onInit(position, convertView, parent);
            convertView.setTag(holder);
        } else
        {
            holder = (FViewHolder<T>) convertView.getTag();
        }
        T model = getData(position);
        holder.setModel(model);

        holder.onBindData(position, convertView, parent, model);
        onBindData(position, convertView, parent, model, holder);
        return convertView;
    }

    public abstract FViewHolder<T> onCreateVHolder(int position, View convertView, ViewGroup parent);

    public abstract void onBindData(int position, View convertView, ViewGroup parent, T model, FViewHolder<T> holder);

}
