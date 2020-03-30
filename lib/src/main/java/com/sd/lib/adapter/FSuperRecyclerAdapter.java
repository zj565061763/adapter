package com.sd.lib.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import com.sd.lib.adapter.annotation.ASuperViewHolder;
import com.sd.lib.adapter.viewholder.FRecyclerViewHolder;
import com.sd.lib.adapter.viewholder.FSuperRecyclerViewHolder;

import java.lang.reflect.Constructor;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;
import java.util.WeakHashMap;

public class FSuperRecyclerAdapter<T> extends FRecyclerAdapter<T>
{
    private final Map<Class<?>, ViewHolderInfo> mMapModelViewHolderInfo = new HashMap<>();
    private final Map<Integer, ViewHolderInfo> mMapTypeViewHolderInfo = new HashMap<>();
    private boolean mSearchParentModel = true;

    private final Map<RecyclerView.ViewHolder, ViewHolderInfo> mMapViewHolder = new WeakHashMap<>();

    private ViewHolderFactory mViewHolderFactory;

    /**
     * 如果集合中的实体未被注册，是否查找缓存中与之匹配的父类
     *
     * @param search
     */
    public void setSearchParentModel(boolean search)
    {
        mSearchParentModel = search;
    }

    /**
     * 注册ViewHolder
     *
     * @param clazz
     * @param <T>
     */
    public final <T extends FSuperRecyclerViewHolder> void registerViewHolder(Class<T> clazz)
    {
        registerViewHolder(clazz, null);
    }

    /**
     * 注册ViewHolder
     *
     * @param clazz
     * @param viewHolderCallback
     * @param <T>
     */
    public final <T extends FSuperRecyclerViewHolder> void registerViewHolder(Class<T> clazz, ViewHolderCallback<T> viewHolderCallback)
    {
        final ASuperViewHolder annotation = getAnnotation(clazz);

        final String layoutName = annotation.layoutName();
        if (TextUtils.isEmpty(layoutName))
            throw new IllegalArgumentException(ASuperViewHolder.class.getSimpleName() + "'s layoutName is empty in " + clazz.getName());

        final Class<?> modelClass = getModelClass(clazz, annotation);
        if (modelClass == null)
            throw new IllegalArgumentException("model class was not found in " + clazz.getName());

        Constructor<?> constructor = null;
        try
        {
            constructor = clazz.getConstructor(View.class);
            constructor.setAccessible(true);
        } catch (Exception e)
        {
            throw new IllegalArgumentException("Constructor with View params was not found");
        }

        if (mMapModelViewHolderInfo.containsKey(modelClass))
            throw new IllegalArgumentException("ViewHolder with model class " + modelClass.getName() + " has been registered:" + clazz);

        final int viewType = System.identityHashCode(modelClass);
        final ViewHolderInfo viewHolderInfo = new ViewHolderInfo(
                clazz,
                viewType,
                layoutName,
                constructor,
                viewHolderCallback
        );

        mMapModelViewHolderInfo.put(modelClass, viewHolderInfo);
        mMapTypeViewHolderInfo.put(viewType, viewHolderInfo);
    }

    /**
     * {@link ViewHolderFactory}
     *
     * @return
     */
    public final ViewHolderFactory getViewHolderFactory()
    {
        if (mViewHolderFactory == null)
            mViewHolderFactory = new DefaultViewHolderFactory();
        return mViewHolderFactory;
    }

    /**
     * {@link ViewHolderFactory}
     *
     * @param viewHolderFactory
     */
    public final void setViewHolderFactory(ViewHolderFactory viewHolderFactory)
    {
        mViewHolderFactory = viewHolderFactory;
    }

    @Override
    public final int getItemViewType(int position)
    {
        final Class<?> modelClass = getDataHolder().get(position).getClass();

        ViewHolderInfo info = mMapModelViewHolderInfo.get(modelClass);
        if (info == null)
        {
            if (mSearchParentModel)
            {
                for (Map.Entry<Class<?>, ViewHolderInfo> item : mMapModelViewHolderInfo.entrySet())
                {
                    final Class<?> key = item.getKey();
                    final ViewHolderInfo value = item.getValue();
                    if (key.isAssignableFrom(modelClass))
                    {
                        info = value;
                        mMapModelViewHolderInfo.put(modelClass, value);
                        break;
                    }
                }
            }
        }

        if (info == null)
            throw new RuntimeException("ViewHolder for model " + modelClass.getName() + " has not been registered");

        return info.mViewType;
    }

    @Override
    public final FRecyclerViewHolder<T> onCreateVHolder(ViewGroup parent, int viewType)
    {
        final ViewHolderInfo viewHolderInfo = mMapTypeViewHolderInfo.get(viewType);

        final FSuperRecyclerViewHolder viewHolder = getViewHolderFactory().create(viewHolderInfo, parent);
        if (viewHolder == null)
            throw new RuntimeException(ViewHolderFactory.class.getSimpleName() + " create view holder null for:" + viewHolderInfo.mViewHolderClass.getName());

        mMapViewHolder.put(viewHolder, viewHolderInfo);
        return viewHolder;
    }

    @Override
    protected void onViewHolderCreated(FRecyclerViewHolder<T> viewHolder)
    {
        super.onViewHolderCreated(viewHolder);

        final ViewHolderInfo viewHolderInfo = mMapViewHolder.remove(viewHolder);
        if (viewHolderInfo != null)
            viewHolderInfo.notifyViewHolderCreated((FSuperRecyclerViewHolder) viewHolder);
    }

    @Override
    public void onBindData(FRecyclerViewHolder<T> holder, int position, T model)
    {

    }

    private static <T extends FSuperRecyclerViewHolder> ASuperViewHolder getAnnotation(Class<T> clazz)
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

    private static <T extends FSuperRecyclerViewHolder> Class<?> getModelClass(Class<T> clazz, ASuperViewHolder annotation)
    {
        if (clazz == null)
            throw new IllegalArgumentException("clazz is null");

        final Class<?> modelClass = annotation.modelClass();
        if (modelClass != null && modelClass != ASuperViewHolder.class)
            return modelClass;

        final Type type = getGenericType(clazz);
        if (type == null)
            throw new IllegalArgumentException("model generic type for " + clazz.getSimpleName() + " was not found");

        return (Class<?>) type;
    }

    private static Type getGenericType(Class<?> clazz)
    {
        final ParameterizedType parameterizedType = (ParameterizedType) clazz.getGenericSuperclass();
        final Type[] types = parameterizedType.getActualTypeArguments();
        if (types != null && types.length > 0)
        {
            return types[0];
        } else
        {
            return null;
        }
    }

    public static final class ViewHolderInfo
    {
        private final Class<? extends FSuperRecyclerViewHolder> mViewHolderClass;
        private final int mViewType;
        private final String mLayoutName;
        private final Constructor<?> mConstructor;
        private final ViewHolderCallback mViewHolderCallback;

        private Integer mLayoutId = null;

        public ViewHolderInfo(Class<? extends FSuperRecyclerViewHolder> clazz, int viewType, String layoutName, Constructor<?> constructor, ViewHolderCallback viewHolderCallback)
        {
            mViewHolderClass = clazz;
            mViewType = viewType;
            mLayoutName = layoutName;
            mConstructor = constructor;
            mViewHolderCallback = viewHolderCallback;
        }

        public Class<? extends FSuperRecyclerViewHolder> getViewHolderClass()
        {
            return mViewHolderClass;
        }

        public String getLayoutName()
        {
            return mLayoutName;
        }

        public int getLayoutId(Context context)
        {
            if (mLayoutId != null)
                return mLayoutId;

            final int layoutId = context.getResources().getIdentifier(mLayoutName, "layout", context.getPackageName());
            if (layoutId == 0)
                throw new RuntimeException("layout was not found:" + mLayoutName);

            mLayoutId = layoutId;
            return mLayoutId;
        }

        private void notifyViewHolderCreated(FSuperRecyclerViewHolder viewHolder)
        {
            if (mViewHolderCallback != null)
                mViewHolderCallback.onCreated(viewHolder);
        }
    }

    private final class DefaultViewHolderFactory implements ViewHolderFactory
    {
        @Override
        public FSuperRecyclerViewHolder create(ViewHolderInfo viewHolderInfo, ViewGroup parent)
        {
            final int layoutId = viewHolderInfo.getLayoutId(parent.getContext());
            final View view = LayoutInflater.from(parent.getContext()).inflate(layoutId, parent, false);

            FSuperRecyclerViewHolder viewHolder = null;
            try
            {
                viewHolder = (FSuperRecyclerViewHolder) viewHolderInfo.mConstructor.newInstance(view);
            } catch (Exception e)
            {
                throw new RuntimeException("ViewHolder create failed: " + e);
            }
            return viewHolder;
        }
    }

    /**
     * ViewHolder回调
     *
     * @param <T>
     */
    public interface ViewHolderCallback<T extends FSuperRecyclerViewHolder>
    {
        /**
         * ViewHolder被创建
         *
         * @param viewHolder
         */
        void onCreated(T viewHolder);
    }

    /**
     * ViewHolder工厂
     */
    public interface ViewHolderFactory
    {
        /**
         * 创建ViewHolder
         *
         * @param viewHolderInfo
         * @param parent
         * @return
         */
        FSuperRecyclerViewHolder create(ViewHolderInfo viewHolderInfo, ViewGroup parent);
    }
}
