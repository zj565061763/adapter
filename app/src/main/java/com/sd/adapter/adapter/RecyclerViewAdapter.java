package com.sd.adapter.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sd.adapter.R;
import com.sd.adapter.model.DataModel;
import com.sd.lib.adapter.FSimpleRecyclerAdapter;
import com.sd.lib.adapter.viewholder.FRecyclerViewHolder;

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
        TextView tv_content = holder.findViewById(R.id.tv_content);
        tv_content.setText(model.name);

        holder.itemView.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                getCallbackHolder().notifyItemClickCallback(model, v);
            }
        });
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener()
        {
            @Override
            public boolean onLongClick(View v)
            {
                return getCallbackHolder().notifyItemLongClickCallback(model, v);
            }
        });
    }
}
