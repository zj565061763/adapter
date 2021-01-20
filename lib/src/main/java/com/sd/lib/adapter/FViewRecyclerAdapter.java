package com.sd.lib.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.sd.lib.adapter.viewholder.FRecyclerViewHolder;

public class FViewRecyclerAdapter extends FRecyclerAdapter<View>
{
    private boolean mIsMatchParent = false;

    /**
     * 设置宽高是否{@link ViewGroup.LayoutParams#MATCH_PARENT}
     *
     * @param matchParent
     */
    public void setMatchParent(boolean matchParent)
    {
        mIsMatchParent = matchParent;
    }

    @Override
    public FRecyclerViewHolder<View> onCreateVHolder(ViewGroup parent, int viewType)
    {
        final View itemView = onCreateItemView(parent, viewType);
        final FRecyclerViewHolder<View> holder = new FRecyclerViewHolder<View>(itemView)
        {
            @Override
            public void onCreate()
            {
            }

            @Override
            public void onBindData(int position, View model)
            {
                final ViewGroup viewGroup = (ViewGroup) this.itemView;
                if (model.getParent() != viewGroup)
                {
                    viewGroup.addView(model);
                }
            }
        };
        return holder;
    }

    @Override
    public void onBindData(FRecyclerViewHolder<View> holder, int position, View model)
    {

    }

    protected ViewGroup onCreateItemView(ViewGroup parent, int viewType)
    {
        final FrameLayout frameLayout = new FrameLayout(parent.getContext());
        if (mIsMatchParent)
            frameLayout.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        return frameLayout;
    }
}
