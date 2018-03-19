package com.fanwe.adapter;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;

import com.fanwe.adapter.model.DataModel;
import com.fanwe.lib.adapter.FSimpleAdapter;
import com.fanwe.lib.adapter.select.FSelectSimpleAdapter;

/**
 * Created by Administrator on 2018/3/19.
 */
public class SimpleAdapterActivity extends Activity
{
    public static final String TAG = SimpleAdapterActivity.class.getSimpleName();

    private ListView mListView;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_simple_adapter);
        mListView = findViewById(R.id.listview);

        mListView.setAdapter(mAdapter);
        mAdapter.getDataHolder().setData(DataModel.get(50));
    }

    private FSimpleAdapter<DataModel> mAdapter = new FSelectSimpleAdapter<DataModel>(this)
    {
        @Override
        public int getLayoutId(int position, View convertView, ViewGroup parent)
        {
            return R.layout.item_list;
        }

        @Override
        protected void onUpdateView(int position, View convertView, ViewGroup parent, DataModel model)
        {
            super.onUpdateView(position, convertView, parent, model);
            Log.i(TAG, "onUpdateView:" + model.name);
        }

        @Override
        public void onBindData(int position, View convertView, ViewGroup parent, final DataModel model)
        {
            Button button = get(R.id.button, convertView);
            button.setText(model.name);

            if (model.isSelected())
            {
                button.setTextColor(Color.RED);
            } else
            {
                button.setTextColor(Color.BLACK);
            }

            button.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    getSelectManager().performClick(model);
                }
            });
        }
    };
}
