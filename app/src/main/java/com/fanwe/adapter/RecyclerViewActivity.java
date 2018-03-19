package com.fanwe.adapter;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.fanwe.adapter.model.DataModel;
import com.fanwe.lib.adapter.select.FSelectSimpleRecyclerAdapter;
import com.fanwe.lib.adapter.viewholder.FRecyclerViewHolder;
import com.fanwe.lib.selectmanager.FSelectManager;

/**
 * Created by Administrator on 2018/3/19.
 */
public class RecyclerViewActivity extends Activity
{
    public static final String TAG = RecyclerViewActivity.class.getSimpleName();

    private RecyclerView mRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_recyclerview);
        mRecyclerView = findViewById(R.id.recyclerview);
        GridLayoutManager layoutManager = new GridLayoutManager(this, 3);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(layoutManager);

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

        mRecyclerView.setAdapter(mAdapter);
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

    private FSelectSimpleRecyclerAdapter<DataModel> mAdapter = new FSelectSimpleRecyclerAdapter<DataModel>(this)
    {
        @Override
        public void onBindData(FRecyclerViewHolder<DataModel> holder, int position, final DataModel model)
        {
            TextView textview = holder.get(R.id.textview);
            textview.setText(model.name);

            if (model.isSelected())
            {
                textview.setTextColor(Color.RED);
            } else
            {
                textview.setTextColor(Color.BLACK);
            }

            holder.itemView.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    getSelectManager().performClick(model);
                }
            });
            holder.itemView.setOnLongClickListener(new View.OnLongClickListener()
            {
                @Override
                public boolean onLongClick(View v)
                {
                    getDataHolder().removeData(model);
                    return false;
                }
            });
        }

        @Override
        public int getLayoutId(ViewGroup parent, int viewType)
        {
            return R.layout.item_list;
        }
    };
}
