package com.sd.adapter.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import com.sd.adapter.R;
import com.sd.adapter.adapter.ListViewAdapter;
import com.sd.adapter.model.DataModel;
import com.sd.lib.adapter.callback.OnItemClickCallback;
import com.sd.lib.adapter.callback.OnItemLongClickCallback;

/**
 * Created by Administrator on 2018/3/19.
 */
public class ListViewActivity extends Activity {
    private ListView mListView;
    private ListViewAdapter mAdapter = new ListViewAdapter();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_listview);
        mListView = findViewById(R.id.listview);
        mListView.setAdapter(mAdapter);

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
        mAdapter.getDataHolder().setData(DataModel.get(5));
    }
}
