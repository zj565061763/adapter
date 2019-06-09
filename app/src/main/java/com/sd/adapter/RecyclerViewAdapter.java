package com.sd.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sd.adapter.model.DataModel;
import com.sd.lib.adapter.viewholder.FRecyclerViewHolder;
import com.sd.lib.adapter.FSimpleRecyclerAdapter;

public class RecyclerViewAdapter extends FSimpleRecyclerAdapter<DataModel>
{
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
        TextView textview = holder.findViewById(R.id.textview);
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
