package com.sd.demo.adapter.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.sd.demo.adapter.R;
import com.sd.demo.adapter.adapter.RecyclerViewAdapter;
import com.sd.demo.adapter.model.DataModel;
import com.sd.lib.adapter.callback.OnItemClickCallback;
import com.sd.lib.adapter.callback.OnItemLongClickCallback;

/**
 * Created by Administrator on 2018/3/19.
 */
public class RecyclerViewActivity extends Activity {
    public static final String TAG = RecyclerViewActivity.class.getSimpleName();

    private RecyclerView mRecyclerView;
    private RecyclerViewAdapter mAdapter = new RecyclerViewAdapter();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_recyclerview);
        mRecyclerView = findViewById(R.id.recyclerview);

        GridLayoutManager layoutManager = new GridLayoutManager(this, 2);
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setAdapter(mAdapter);

        /**
         * 点击回调
         */
        mAdapter.getCallbackHolder().setOnItemClickCallback(new OnItemClickCallback<DataModel>() {
            @Override
            public void onItemClick(DataModel item, View view) {
                final DataModel model = new DataModel();
                model.name = String.valueOf(mAdapter.getDataHolder().size());

                /**
                 * 添加数据
                 */
                mAdapter.getDataHolder().addData(model);
            }
        });

        /**
         * 长按回调
         */
        mAdapter.getCallbackHolder().setOnItemLongClickCallback(new OnItemLongClickCallback<DataModel>() {
            @Override
            public boolean onItemLongClick(DataModel item, View view) {
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
        mAdapter.getDataHolder().setData(DataModel.get(100));
    }
}
