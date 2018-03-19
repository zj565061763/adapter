package com.fanwe.adapter;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.fanwe.adapter.model.DataModel;
import com.fanwe.lib.adapter.select.FSelectSimpleAdapter;
import com.fanwe.lib.selectmanager.FSelectManager;

/**
 * Created by Administrator on 2018/3/19.
 */
public class ListViewActivity extends Activity
{
    public static final String TAG = ListViewActivity.class.getSimpleName();

    private ListView mListView;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_listview);
        mListView = findViewById(R.id.listview);
        findViewById(R.id.btn_add).setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                DataModel model = new DataModel();
                model.name = String.valueOf(mAdapter.getDataHolder().size());

                mAdapter.getDataHolder().appendData(model);
            }
        });
        findViewById(R.id.btn_remove).setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                mAdapter.getDataHolder().removeData(0);
            }
        });

        mListView.setAdapter(mAdapter);
        mAdapter.getDataHolder().setData(DataModel.get(5));

        mAdapter.getSelectManager().setMode(FSelectManager.Mode.MULTI);
        mAdapter.getSelectManager().addCallback(new FSelectManager.Callback<DataModel>()
        {
            @Override
            public void onNormal(DataModel item)
            {
                updateSelectedInfo();
            }

            @Override
            public void onSelected(DataModel item)
            {
                updateSelectedInfo();
            }
        });
    }

    private void updateSelectedInfo()
    {
        final TextView textView = findViewById(R.id.tv_select);

        if (mAdapter.getSelectManager().isSingleMode())
        {
            textView.setText(String.valueOf(mAdapter.getSelectManager().getSelectedItem()));
        } else
        {
            textView.setText(String.valueOf(mAdapter.getSelectManager().getSelectedItems()));
        }
    }

    private FSelectSimpleAdapter<DataModel> mAdapter = new FSelectSimpleAdapter<DataModel>(this)
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
