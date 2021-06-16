package com.sd.demo.adapter.viewholder;

import android.view.View;
import android.widget.TextView;

import com.sd.demo.adapter.R;
import com.sd.demo.adapter.model.DataModel;
import com.sd.lib.adapter.annotation.ASuperViewHolder;
import com.sd.lib.adapter.viewholder.FSuperRecyclerViewHolder;

@ASuperViewHolder(layoutName = "item_super_textview")
public class TextViewViewHolder extends FSuperRecyclerViewHolder<TextViewViewHolder.Model> {
    private TextView tv_content;

    public TextViewViewHolder(View itemView) {
        super(itemView);
    }

    @Override
    public void onCreate() {
        tv_content = findViewById(R.id.tv_content);
    }

    @Override
    public void onBindData(int position, final TextViewViewHolder.Model model) {
        final DataModel dataModel = model.getSource();
        tv_content.setText(dataModel.name);

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getCallbackHolder().notifyItemClickCallback(model, v);
            }
        });
    }

    /**
     * ViewHolder对应的实体
     */
    public static class Model extends FSuperRecyclerViewHolder.Model<DataModel> {
    }
}
