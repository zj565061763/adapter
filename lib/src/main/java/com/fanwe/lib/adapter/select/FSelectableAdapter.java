package com.fanwe.lib.adapter.select;

import com.fanwe.lib.selectmanager.FSelectManager;

public interface FSelectableAdapter<T>
{
    FSelectManager<T> getSelectManager();
}
