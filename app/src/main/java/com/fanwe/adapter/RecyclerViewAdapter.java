package com.fanwe.adapter;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.fanwe.adapter.model.DataModel;
import com.fanwe.lib.adapter.FSimpleRecyclerAdapter;
import com.fanwe.lib.adapter.viewholder.FRecyclerViewHolder;

public class RecyclerViewAdapter extends FSimpleRecyclerAdapter<DataModel>
{
    public RecyclerViewAdapter(Activity activity)
    {
        super(activity);
    }

    @Override
    public int getLayoutId(ViewGroup parent, int viewType)
    {
        /**
         * 返回布局id
         */
        return R.layout.item_list;
    }

    @Override
    public void onBindData(FRecyclerViewHolder<DataModel> holder, int position, final DataModel model)
    {
        TextView textview = holder.get(R.id.textview);
        textview.setText(model.name);

        holder.itemView.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                notifyItemClickCallback(model, v);
            }
        });
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener()
        {
            @Override
            public boolean onLongClick(View v)
            {
                return notifyItemLongClickCallback(model, v);
            }
        });
    }
}
