package com.sd.demo.adapter.viewholder;

import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.sd.demo.adapter.R;
import com.sd.demo.adapter.model.DataModel;
import com.sd.lib.adapter.annotation.ASuperViewHolder;
import com.sd.lib.adapter.viewholder.FSuperRecyclerViewHolder;

@ASuperViewHolder(layoutName = "item_list")
public class SimpleSuperViewHolder extends FSuperRecyclerViewHolder<DataModel> {
    private static final String TAG = SimpleSuperViewHolder.class.getSimpleName();

    private TextView tv_name;

    public SimpleSuperViewHolder(View itemView) {
        super(itemView);
    }

    @Override
    public void onCreate() {
        tv_name = findViewById(R.id.tv_content);
    }

    @Override
    public void onBindData(int position, DataModel model) {
        tv_name.setText(model.name);
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        Log.i(TAG, "onAttachedToWindow " + this);
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        Log.i(TAG, "onDetachedFromWindow " + this);
    }
}
