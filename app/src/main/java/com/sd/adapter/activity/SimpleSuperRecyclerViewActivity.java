package com.sd.adapter.activity;

import android.app.Activity;
import android.os.Bundle;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.sd.adapter.R;
import com.sd.adapter.model.DataModel;
import com.sd.adapter.viewholder.SimpleSuperViewHolder;
import com.sd.lib.adapter.FSuperRecyclerAdapter;

/**
 * Created by Administrator on 2018/3/19.
 */
public class SimpleSuperRecyclerViewActivity extends Activity
{
    private RecyclerView mRecyclerView;
    private final FSuperRecyclerAdapter<DataModel> mAdapter = new FSuperRecyclerAdapter<>();

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_recyclerview);
        mRecyclerView = findViewById(R.id.recyclerview);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(mAdapter);

        // 注册ViewHolder
        mAdapter.registerViewHolder(SimpleSuperViewHolder.class);

        // 设置数据
        mAdapter.getDataHolder().setData(DataModel.get(100));
    }
}
