package com.sd.adapter.viewholder;

import android.view.View;
import android.widget.TextView;

import com.sd.adapter.R;
import com.sd.lib.adapter.annotation.SuperViewHolder;
import com.sd.lib.adapter.viewholder.FSuperRecyclerViewHolder;

@SuperViewHolder(layout = R.layout.item_super_textview, modelClass = TextViewViewHolder.Model.class)
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
        tv_content.setText(model.name);
    }

    public static class Model
    {
        public String name;
    }
}
