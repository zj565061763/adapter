package com.sd.adapter;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onClickListViewActivity(View view)
    {
        startActivity(new Intent(this, ListViewActivity.class));
    }
    public void onClickRecyclerViewActivity(View view)
    {
        startActivity(new Intent(this, RecyclerViewActivity.class));
    }
}
