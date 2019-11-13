package com.sd.adapter.viewholder;

import android.view.View;
import android.widget.TextView;

import com.sd.adapter.R;
import com.sd.adapter.model.DataModel;
import com.sd.lib.adapter.annotation.ASuperViewHolder;
import com.sd.lib.adapter.viewholder.FSuperRecyclerViewHolder;

@ASuperViewHolder(layoutId = R.layout.item_list)
public class SimpleSuperViewHolder extends FSuperRecyclerViewHolder<DataModel>
{
    private TextView tv_name;

    public SimpleSuperViewHolder(View itemView)
    {
        super(itemView);
    }

    @Override
    public void onCreate()
    {
        tv_name = findViewById(R.id.tv_content);
    }

    @Override
    public void onBindData(int position, DataModel model)
    {
        tv_name.setText(model.name);
    }
}
