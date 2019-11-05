package com.sd.adapter.activity;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.sd.adapter.R;
import com.sd.adapter.viewholder.ButtonViewHolder;
import com.sd.adapter.viewholder.TextViewViewHolder;
import com.sd.lib.adapter.FSuperRecyclerAdapter;

import java.util.ArrayList;
import java.util.List;

public class SuperRecyclerViewActivity extends AppCompatActivity
{
    public static final String TAG = SuperRecyclerViewActivity.class.getSimpleName();

    private RecyclerView mRecyclerView;
    private FSuperRecyclerAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_super_recyclerview);
        mRecyclerView = findViewById(R.id.recyclerview);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(getAdapter());

        final List<Object> listData = new ArrayList<>();
        for (int i = 0; i < 100; i++)
        {
            if (i % 2 == 0)
            {
                TextViewViewHolder.Model model = new TextViewViewHolder.Model();
                model.name = String.valueOf(i);
                listData.add(model);
            } else
            {
                ButtonViewHolder.Model model = new ButtonViewHolder.Model();
                model.name = String.valueOf(i);
                listData.add(model);
            }
        }

        getAdapter().getDataHolder().setData(listData);
    }

    private FSuperRecyclerAdapter getAdapter()
    {
        if (mAdapter == null)
        {
            mAdapter = new FSuperRecyclerAdapter();
            mAdapter.register(TextViewViewHolder.class, new FSuperRecyclerAdapter.ViewHolderCallback<TextViewViewHolder>()
            {
                @Override
                public void onCreated(TextViewViewHolder viewHolder)
                {
                    Log.i(TAG, "TextViewViewHolder onCreated:" + viewHolder);
                }
            });
            mAdapter.register(ButtonViewHolder.class, new FSuperRecyclerAdapter.ViewHolderCallback<ButtonViewHolder>()
            {
                @Override
                public void onCreated(ButtonViewHolder viewHolder)
                {
                    Log.i(TAG, "ButtonViewHolder onCreated:" + viewHolder);
                }
            });
        }
        return mAdapter;
    }
}
