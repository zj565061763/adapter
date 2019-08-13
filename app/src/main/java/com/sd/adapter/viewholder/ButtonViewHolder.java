package com.sd.adapter.viewholder;

import android.view.View;
import android.widget.Button;

import com.sd.adapter.R;
import com.sd.lib.adapter.annotation.SuperViewHolder;
import com.sd.lib.adapter.viewholder.FSuperRecyclerViewHolder;

@SuperViewHolder(layout = R.layout.item_super_button, modelClass = ButtonViewHolder.Model.class)
public class ButtonViewHolder extends FSuperRecyclerViewHolder<ButtonViewHolder.Model>
{
    private Button btn_content;

    public ButtonViewHolder(View itemView)
    {
        super(itemView);
    }

    @Override
    public void onCreate()
    {
        btn_content = findViewById(R.id.btn_content);
    }

    @Override
    public void onBindData(int position, ButtonViewHolder.Model model)
    {
        btn_content.setText(model.name);
    }

    public static class Model
    {
        public String name;
    }
}
