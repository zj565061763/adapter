package com.sd.lib.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sd.lib.adapter.annotation.SuperViewHolder;
import com.sd.lib.adapter.viewholder.FRecyclerViewHolder;
import com.sd.lib.adapter.viewholder.FSuperRecyclerViewHolder;

import java.lang.reflect.Constructor;
import java.util.HashMap;
import java.util.Map;

public class FSuperRecyclerAdapter extends FRecyclerAdapter<Object>
{
    private final Map<Class<?>, ViewHolderInfo> mMapViewHolderInfo = new HashMap<>();
    private final Map<Integer, ViewHolderInfo> mMapTypeViewHolderInfo = new HashMap<>();

    /**
     * 注册ViewHolder
     *
     * @param clazz
     * @param <T>
     */
    public <T extends FSuperRecyclerViewHolder> void register(Class<T> clazz)
    {
        register(clazz, null);
    }

    /**
     * 注册ViewHolder
     *
     * @param clazz
     * @param viewHolderCallback
     * @param <T>
     */
    public <T extends FSuperRecyclerViewHolder> void register(Class<T> clazz, ViewHolderCallback<T> viewHolderCallback)
    {
        if (clazz == null || clazz == FSuperRecyclerViewHolder.class)
            throw new IllegalArgumentException();

        final SuperViewHolder annotation = clazz.getAnnotation(SuperViewHolder.class);
        if (annotation == null)
            throw new IllegalArgumentException("SuperViewHolder annotation was not found");

        if (annotation.layout() == 0)
            throw new IllegalArgumentException("SuperViewHolder's layout == 0");

        final Class<?> modelClass = annotation.modelClass();
        if (modelClass == null)
            throw new IllegalArgumentException("SuperViewHolder's modelClass == null");

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

        final ViewHolderInfo viewHolderInfo = new ViewHolderInfo(viewType, annotation, targetConstructor, viewHolderCallback);
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
            throw new RuntimeException("ViewHolder for model " + clazz.getName() + " has not been registered");

        return info.mViewType;
    }

    @Override
    public final FRecyclerViewHolder<Object> onCreateVHolder(ViewGroup parent, int viewType)
    {
        final ViewHolderInfo viewHolderInfo = mMapTypeViewHolderInfo.get(viewType);
        final int layout = viewHolderInfo.mSuperViewHolder.layout();

        final View view = LayoutInflater.from(parent.getContext()).inflate(layout, parent, false);

        FSuperRecyclerViewHolder viewHolder = null;
        try
        {
            viewHolder = (FSuperRecyclerViewHolder) viewHolderInfo.mConstructor.newInstance(view);
        } catch (Exception e)
        {
            throw new RuntimeException("ViewHolder create failed: " + e);
        }

        viewHolderInfo.notifyViewHolderCreated(viewHolder);
        return viewHolder;
    }

    @Override
    public void onBindData(FRecyclerViewHolder<Object> holder, int position, Object model)
    {

    }

    private static class ViewHolderInfo
    {
        private final int mViewType;
        private final SuperViewHolder mSuperViewHolder;
        private final Constructor<?> mConstructor;
        private final ViewHolderCallback mViewHolderCallback;

        public ViewHolderInfo(int viewType, SuperViewHolder superViewHolder, Constructor<?> constructor, ViewHolderCallback viewHolderCallback)
        {
            mViewType = viewType;
            mSuperViewHolder = superViewHolder;
            mConstructor = constructor;
            mViewHolderCallback = viewHolderCallback;
        }

        public void notifyViewHolderCreated(FSuperRecyclerViewHolder viewHolder)
        {
            if (mViewHolderCallback != null)
                mViewHolderCallback.onCreated(viewHolder);
        }
    }

    public interface ViewHolderCallback<T extends FSuperRecyclerViewHolder>
    {
        void onCreated(T viewHolder);
    }
}
