package com.sd.adapter.viewholder;

import android.view.View;
import android.widget.TextView;

import com.sd.adapter.R;
import com.sd.adapter.model.SuperDataModel;
import com.sd.lib.adapter.annotation.ASuperViewHolder;
import com.sd.lib.adapter.viewholder.FSuperRecyclerViewHolder;

@ASuperViewHolder(layoutId = R.layout.item_super_textview)
public class TextViewViewHolder extends FSuperRecyclerViewHolder<TextViewViewHolder.Model>
{
    private TextView tv_content;

    public TextViewViewHolder(View itemView)
    {
        super(itemView);
    }

    @Override
    public void onCreate()
    {
        tv_content = findViewById(R.id.tv_content);
    }

    @Override
    public void onBindData(int position, TextViewViewHolder.Model model)
    {
        tv_content.setText(String.valueOf(model.getSource().index));
    }

    /**
     * ViewHolder对应的实体
     */
    public static class Model extends FSuperRecyclerViewHolder.Model<SuperDataModel>
    {
    }
}
