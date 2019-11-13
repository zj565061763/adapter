package com.sd.adapter.model;

import java.util.ArrayList;
import java.util.List;

public class SuperDataModel
{
    public int index;

    public static List<SuperDataModel> get(int count)
    {
        final List<SuperDataModel> list = new ArrayList<>();
        for (int i = 0; i < count; i++)
        {
            final SuperDataModel model = new SuperDataModel();
            model.index = i;
            list.add(model);
        }
        return list;
    }
}
