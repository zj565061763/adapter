package com.sd.adapter.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.sd.adapter.databinding.ActViewPager2Binding;
import com.sd.lib.adapter.FViewRecyclerAdapter;

import java.util.ArrayList;
import java.util.List;

public class ViewPager2Activity extends AppCompatActivity {
    private ActViewPager2Binding mBinding;
    private final FViewRecyclerAdapter mAdapter = new FViewRecyclerAdapter();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = ActViewPager2Binding.inflate(getLayoutInflater());
        setContentView(mBinding.getRoot());

        mAdapter.setMatchParent(true);
        mBinding.viewPager.setAdapter(mAdapter);

        List<View> list = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            final Button button = new Button(this);
            button.setText(String.valueOf(i));
            list.add(button);
        }

        mAdapter.getDataHolder().setData(list);
    }
}
