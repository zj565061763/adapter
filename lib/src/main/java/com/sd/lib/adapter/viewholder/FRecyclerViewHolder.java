package com.sd.lib.adapter.viewholder;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import com.sd.lib.adapter.Adapter;

public abstract class FRecyclerViewHolder<T> extends RecyclerView.ViewHolder
{
    private Adapter<T> mAdapter;
    private T mModel;
    private BindDataCallback<T> mBindDataCallback;

    public FRecyclerViewHolder(View itemView)
    {
        super(itemView);
        itemView.addOnAttachStateChangeListener(mOnAttachStateChangeListener);
    }

    public FRecyclerViewHolder(int layoutId, ViewGroup parent)
    {
        this(LayoutInflater.from(parent.getContext()).inflate(layoutId, parent, false));
    }

    public final void setAdapter(Adapter<T> adapter)
    {
        mAdapter = adapter;
    }

    public final Adapter<T> getAdapter()
    {
        return mAdapter;
    }

    /**
     * 返回ViewHolder最后一次绑定数据的实体对象
     *
     * @return
     */
    public T getModel()
    {
        return mModel;
    }

    /**
     * 刷新ViewHolder
     *
     * @return true-通知刷新成功
     */
    public boolean notifyViewHolderChanged()
    {
        final int position = getAdapterPosition();
        if (position == RecyclerView.NO_POSITION)
            return false;

        final Adapter<T> adapter = getAdapter();
        if (adapter == null)
            return false;

        adapter.notifyItemViewChanged(position);
        return true;
    }

    /**
     * {@link BindDataCallback}
     *
     * @param bindDataCallback
     */
    public void setBindDataCallback(BindDataCallback<T> bindDataCallback)
    {
        mBindDataCallback = bindDataCallback;
    }

    /**
     * 根据id从{@link #itemView}中查找View
     *
     * @param id
     * @param <V>
     * @return
     */
    public final <V extends View> V findViewById(int id)
    {
        return itemView.findViewById(id);
    }

    /**
     * 通知{@link #onCreate()}
     */
    public final void notifyCreate()
    {
        onCreate();
    }

    /**
     * 通知{@link #onBindData(int, Object)}
     *
     * @param position
     * @param model
     */
    public final void notifyBindData(int position, T model)
    {
        mModel = model;
        if (mBindDataCallback != null)
        {
            if (mBindDataCallback.onBindData(position, model, false))
                return;
        }

        onBindData(position, model);
    }

    /**
     * 通知{@link #onUpdateData(int, Object)}
     *
     * @param position
     * @param model
     */
    public final void notifyUpdateData(int position, T model)
    {
        mModel = model;
        if (mBindDataCallback != null)
        {
            if (mBindDataCallback.onBindData(position, model, true))
                return;
        }

        this.onUpdateData(position, model);
    }

    /**
     * 创建回调，用来初始化
     */
    protected abstract void onCreate();

    /**
     * 绑定数据
     *
     * @param position
     * @param model
     */
    protected abstract void onBindData(int position, T model);

    /**
     * 刷新item的时候触发，默认整个item重新绑定数据
     *
     * @param position
     * @param model
     */
    protected void onUpdateData(int position, T model)
    {
        onBindData(position, model);
    }

    /**
     * {@link #itemView}添加移除状态回调
     */
    private final View.OnAttachStateChangeListener mOnAttachStateChangeListener = new View.OnAttachStateChangeListener()
    {
        @Override
        public void onViewAttachedToWindow(View v)
        {
            onAttachedToWindow();
        }

        @Override
        public void onViewDetachedFromWindow(View v)
        {
            onDetachedFromWindow();
        }
    };

    /**
     * {@link #itemView}被添加到界面上
     */
    protected void onAttachedToWindow()
    {
    }

    /**
     * {@link #itemView}从界面上被移除
     */
    protected void onDetachedFromWindow()
    {
    }

    public interface BindDataCallback<T>
    {
        /**
         * 绑定数据回调
         *
         * @param position
         * @param model
         * @param isUpdate
         * @return true-不执行ViewHolder中的逻辑
         */
        boolean onBindData(int position, T model, boolean isUpdate);
    }

    /**
     * {@link #findViewById(int)}
     */
    @Deprecated
    public final <V extends View> V get(int id)
    {
        return findViewById(id);
    }
}
