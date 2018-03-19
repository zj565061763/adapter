package com.fanwe.adapter;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;

import com.fanwe.adapter.model.DataModel;
import com.fanwe.lib.adapter.FSimpleAdapter;

/**
 * Created by Administrator on 2018/3/19.
 */
public class SimpleAdapterActivity extends Activity
{
    private ListView mListView;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_simple_adapter);
        mListView = findViewById(R.id.listview);

        mListView.setAdapter(mAdapter);
        mAdapter.setData(DataModel.get(50));
    }

    private FSimpleAdapter<DataModel> mAdapter = new FSimpleAdapter<DataModel>(this)
    {
        @Override
        public int getLayoutId(int position, View convertView, ViewGroup parent)
        {
            return R.layout.item_list;
        }

        @Override
        public void onBindData(int position, View convertView, ViewGroup parent, DataModel model)
        {
            Button button = get(R.id.button, convertView);
            button.setText(model.name);
        }
    };
}
