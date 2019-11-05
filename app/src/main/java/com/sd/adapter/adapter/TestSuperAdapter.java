package com.sd.adapter.adapter;

import com.sd.adapter.viewholder.ButtonViewHolder;
import com.sd.adapter.viewholder.TextViewViewHolder;
import com.sd.lib.adapter.FSuperRecyclerAdapter;

public class TestSuperAdapter extends FSuperRecyclerAdapter
{
    public TestSuperAdapter()
    {
        // 注册ViewHolder
        register(TextViewViewHolder.class);
        register(ButtonViewHolder.class);
    }
}
