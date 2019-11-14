package com.sd.adapter.activity;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.sd.adapter.R;
import com.sd.adapter.model.DataModel;
import com.sd.adapter.viewholder.ButtonViewHolder;
import com.sd.adapter.viewholder.TextViewViewHolder;
import com.sd.lib.adapter.FSuperRecyclerAdapter;
import com.sd.lib.adapter.callback.OnItemClickCallback;

import java.util.ArrayList;
import java.util.List;

public class SuperRecyclerViewActivity extends AppCompatActivity
{
    private RecyclerView mRecyclerView;
    private final FSuperRecyclerAdapter<Object> mAdapter = new FSuperRecyclerAdapter<>();

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_super_recyclerview);
        mRecyclerView = findViewById(R.id.recyclerview);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(mAdapter);

        // 注册ViewHolder
        mAdapter.registerViewHolder(ButtonViewHolder.class);
        mAdapter.registerViewHolder(TextViewViewHolder.class, new FSuperRecyclerAdapter.ViewHolderCallback<TextViewViewHolder>()
        {
            @Override
            public void onCreated(TextViewViewHolder viewHolder)
            {
                viewHolder.getCallbackHolder().setOnItemClickCallback(new OnItemClickCallback<TextViewViewHolder.Model>()
                {
                    @Override
                    public void onItemClick(TextViewViewHolder.Model item, View view)
                    {
                        /**
                         * 移除数据
                         */
                        mAdapter.getDataHolder().removeData(item);
                    }
                });
            }
        });

        fillData();
    }

    private void fillData()
    {
        final List<Object> listModel = new ArrayList<>();
        for (int i = 0; i < 100; i++)
        {
            final DataModel model = new DataModel();
            model.name = String.valueOf(i);

            if (i % 2 == 0)
            {
                final Object viewHolderModel = new ButtonViewHolder.Model().transform(model);
                listModel.add(viewHolderModel);
            } else
            {
                final Object viewHolderModel = new TextViewViewHolder.Model().transform(model);
                listModel.add(viewHolderModel);
            }
        }

        mAdapter.getDataHolder().setData(listModel);
    }
}
