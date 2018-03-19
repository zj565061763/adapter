package com.fanwe.lib.adapter.select;

import android.app.Activity;

import com.fanwe.lib.adapter.FRecyclerAdapter;
import com.fanwe.lib.selectmanager.FSelectManager;

public abstract class FSelectRecyclerAdapter<T> extends FRecyclerAdapter<T> implements FSelectableAdapter<T>
{
    private FSelectManager<T> mSelectManager;

    public FSelectRecyclerAdapter(Activity activity)
    {
        super(activity);
        getSelectManager().setMode(FSelectManager.Mode.SINGLE);
    }

    @Override
    public final FSelectManager<T> getSelectManager()
    {
        if (mSelectManager == null)
        {
            mSelectManager = new AdapterSelectManager<>(this);
        }
        return mSelectManager;
    }
}
