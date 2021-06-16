package com.sd.adapter.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.sd.adapter.R;
import com.sd.adapter.model.DataModel;
import com.sd.adapter.viewholder.SimpleSuperViewHolder;
import com.sd.lib.adapter.FSuperRecyclerAdapter;

/**
 * Created by Administrator on 2018/3/19.
 */
public class SimpleSuperRecyclerViewActivity extends Activity {
    private RecyclerView mRecyclerView;
    private final FSuperRecyclerAdapter<DataModel> mAdapter = new FSuperRecyclerAdapter<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_recyclerview);
        mRecyclerView = findViewById(R.id.recyclerview);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(mAdapter);

        // 注册ViewHolder
        mAdapter.registerViewHolder(SimpleSuperViewHolder.class, new FSuperRecyclerAdapter.ViewHolderCallback<SimpleSuperViewHolder>() {
            @Override
            public void onCreated(final SimpleSuperViewHolder viewHolder) {
                viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(SimpleSuperRecyclerViewActivity.this, "click:" + viewHolder.getAdapterPosition(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        // 设置数据
        mAdapter.getDataHolder().setData(DataModel.get(20));
    }
}
