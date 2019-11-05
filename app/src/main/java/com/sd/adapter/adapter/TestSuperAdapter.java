package com.sd.adapter.adapter;

import com.sd.adapter.viewholder.ButtonViewHolder;
import com.sd.adapter.viewholder.TextViewViewHolder;
import com.sd.lib.adapter.FSuperRecyclerAdapter;

import java.util.ArrayList;
import java.util.List;

public class TestSuperAdapter extends FSuperRecyclerAdapter
{
    public TestSuperAdapter()
    {
        // 注册ViewHolder
        register(TextViewViewHolder.class);
        register(ButtonViewHolder.class);
    }

    public void fillData()
    {
        final List<Object> listData = new ArrayList<>();
        for (int i = 0; i < 100; i++)
        {
            if (i % 2 == 0)
            {
                TextViewViewHolder.Model model = new TextViewViewHolder.Model();
                model.name = String.valueOf(i);
                listData.add(model);
            } else
            {
                ButtonViewHolder.Model model = new ButtonViewHolder.Model();
                model.name = String.valueOf(i);
                listData.add(model);
            }
        }

        getDataHolder().setData(listData);
    }
}
