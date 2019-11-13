package com.sd.adapter.activity;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.sd.adapter.R;
import com.sd.adapter.model.SuperDataModel;
import com.sd.adapter.viewholder.ButtonViewHolder;
import com.sd.adapter.viewholder.TextViewViewHolder;
import com.sd.lib.adapter.FSuperRecyclerAdapter;
import com.sd.lib.adapter.data.DataHolder;

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
        mAdapter.registerViewHolder(TextViewViewHolder.class);
        mAdapter.registerViewHolder(ButtonViewHolder.class);

        // 设置数据转换器
        mAdapter.getDataHolder().setDataTransform(new DataHolder.DataTransform<Object>()
        {
            @Override
            public Object transform(Object source)
            {
                if (source instanceof SuperDataModel)
                {
                    final SuperDataModel model = (SuperDataModel) source;
                    if (model.index % 2 == 0)
                        return new ButtonViewHolder.Model().transform(model);
                    else
                        return new TextViewViewHolder.Model().transform(model);
                }

                return null;
            }
        });

        mAdapter.getDataHolder().setData(SuperDataModel.get(100));
    }
}
