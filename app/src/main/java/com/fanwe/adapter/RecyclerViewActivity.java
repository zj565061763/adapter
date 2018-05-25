package com.fanwe.adapter;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.fanwe.adapter.model.DataModel;
import com.fanwe.lib.adapter.callback.ItemClickCallback;
import com.fanwe.lib.adapter.callback.ItemLongClickCallback;

/**
 * Created by Administrator on 2018/3/19.
 */
public class RecyclerViewActivity extends Activity
{
    public static final String TAG = RecyclerViewActivity.class.getSimpleName();

    private RecyclerView mRecyclerView;
    private RecyclerViewAdapter mAdapter = new RecyclerViewAdapter();

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_recyclerview);
        mRecyclerView = findViewById(R.id.recyclerview);

        GridLayoutManager layoutManager = new GridLayoutManager(this, 3);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setAdapter(mAdapter);

        mAdapter.setItemClickCallback(new ItemClickCallback<DataModel>()
        {
            @Override
            public void onItemClick(int position, DataModel item, View view)
            {
                final DataModel model = new DataModel();
                model.name = String.valueOf(mAdapter.getDataHolder().size());

                /**
                 * 添加数据
                 */
                mAdapter.getDataHolder().addData(model);
            }
        });
        mAdapter.setItemLongClickCallback(new ItemLongClickCallback<DataModel>()
        {
            @Override
            public boolean onItemLongClick(int position, DataModel item, View view)
            {
                /**
                 * 移除数据
                 */
                mAdapter.getDataHolder().removeData(item);
                return false;
            }
        });
        /**
         * 设置数据
         */
        mAdapter.getDataHolder().setData(DataModel.get(5));
    }
}
