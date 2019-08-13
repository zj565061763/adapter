package com.sd.lib.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sd.lib.adapter.annotation.TagViewHolder;
import com.sd.lib.adapter.viewholder.FRecyclerViewHolder;
import com.sd.lib.adapter.viewholder.FSuperRecyclerViewHolder;

import java.lang.reflect.Constructor;
import java.util.HashMap;
import java.util.Map;

public class FSuperRecyclerAdapter extends FRecyclerAdapter<Object>
{
    private final Map<Class<?>, ViewHolderInfo> mMapViewHolderInfo = new HashMap<>();
    private final Map<Integer, ViewHolderInfo> mMapTypeViewHolderInfo = new HashMap<>();

    private final View mView;

    public FSuperRecyclerAdapter(Activity context)
    {
        super(context);
        mView = new View(context.getApplicationContext());
    }

    public void register(Class<? extends FSuperRecyclerViewHolder> clazz)
    {
        if (clazz == FSuperRecyclerViewHolder.class)
            throw new IllegalArgumentException();

        final TagViewHolder tagViewHolder = clazz.getAnnotation(TagViewHolder.class);
        if (tagViewHolder == null)
            throw new IllegalArgumentException("TagViewHolder was not found");

        if (tagViewHolder.layout() == 0)
            throw new IllegalArgumentException("TagViewHolder's layout == 0");

        final Class<?> modelClass = tagViewHolder.modelClass();
        if (modelClass == null)
            throw new IllegalArgumentException("TagViewHolder's modelClass == null");

        Constructor<?> targetConstructor = null;

        try
        {
            targetConstructor = clazz.getConstructor(View.class);
        } catch (Exception e)
        {
            throw new IllegalArgumentException("Constructor with View params was not found");
        }

        if (mMapViewHolderInfo.containsKey(modelClass))
            throw new IllegalArgumentException("ViewHolder with model class " + modelClass.getName() + " has been registered:" + clazz);

        final int viewType = System.identityHashCode(modelClass);
        if (mMapTypeViewHolderInfo.containsKey(viewType))
            throw new IllegalArgumentException("ViewHolder with view type " + viewType + "  has been registered:" + clazz);

        final ViewHolderInfo viewHolderInfo = new ViewHolderInfo(viewType, tagViewHolder, targetConstructor);
        mMapViewHolderInfo.put(modelClass, viewHolderInfo);
        mMapTypeViewHolderInfo.put(viewType, viewHolderInfo);
    }

    @Override
    public final int getItemViewType(int position)
    {
        final Object data = getDataHolder().get(position);
        final Class<?> clazz = data.getClass();

        final ViewHolderInfo info = mMapViewHolderInfo.get(clazz);
        if (info == null)
            throw new RuntimeException("ViewHolder for model " + clazz.getName() + " was not register");

        return info.mViewType;
    }

    @Override
    public final FRecyclerViewHolder<Object> onCreateVHolder(ViewGroup parent, int viewType)
    {
        final ViewHolderInfo viewHolderInfo = mMapTypeViewHolderInfo.get(viewType);
        final int layout = viewHolderInfo.mTagViewHolder.layout();

        final View view = LayoutInflater.from(parent.getContext()).inflate(layout, parent, false);

        try
        {
            final FRecyclerViewHolder viewHolder = (FRecyclerViewHolder) viewHolderInfo.mConstructor.newInstance(view);
            return viewHolder;
        } catch (Exception e)
        {
            throw new RuntimeException("ViewHolder create failed: " + e);
        }
    }

    @Override
    public void onBindData(FRecyclerViewHolder<Object> holder, int position, Object model)
    {

    }

    private static class ViewHolderInfo
    {
        private final int mViewType;
        private final TagViewHolder mTagViewHolder;
        private final Constructor<?> mConstructor;

        public ViewHolderInfo(int viewType, TagViewHolder tagViewHolder, Constructor<?> constructor)
        {
            mViewType = viewType;
            mTagViewHolder = tagViewHolder;
            mConstructor = constructor;
        }
    }
}
