package com.sd.adapter.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.sd.adapter.R;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_listview:
                startActivity(new Intent(this, ListViewActivity.class));
                break;
            case R.id.btn_recyclerview:
                startActivity(new Intent(this, RecyclerViewActivity.class));
                break;
            case R.id.btn_super_recyclerview:
                startActivity(new Intent(this, SuperRecyclerViewActivity.class));
                break;
            case R.id.btn_simple_super_recyclerview:
                startActivity(new Intent(this, SimpleSuperRecyclerViewActivity.class));
                break;
            case R.id.btn_view_pager2:
                startActivity(new Intent(this, ViewPager2Activity.class));
                break;
            default:
                break;
        }
    }
}
