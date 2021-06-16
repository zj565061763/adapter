package com.sd.demo.adapter.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sd.demo.adapter.R;
import com.sd.demo.adapter.model.DataModel;
import com.sd.lib.adapter.FSimpleAdapter;

public class ListViewAdapter extends FSimpleAdapter<DataModel> {
    @Override
    public int getLayoutId(int position, View convertView, ViewGroup parent) {
        /**
         * 返回item布局
         */
        return R.layout.item_list;
    }

    @Override
    public void onBindData(int position, View convertView, ViewGroup parent, final DataModel model) {
        final TextView tv_content = get(R.id.tv_content, convertView);
        tv_content.setText(model.name);

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /**
                 * 通知回调对象
                 */
                getCallbackHolder().notifyItemClickCallback(model, v);
            }
        });
        convertView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                /**
                 * 通知回调对象
                 */
                return getCallbackHolder().notifyItemLongClickCallback(model, v);
            }
        });
    }
}
