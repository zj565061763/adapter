package com.fanwe.lib.adapter.select;

import android.app.Activity;

import com.fanwe.lib.adapter.FBaseAdapter;
import com.fanwe.lib.selectmanager.FSelectManager;

public abstract class FSelectBaseAdapter<T> extends FBaseAdapter<T> implements FSelectableAdapter<T>
{
    private FSelectManager<T> mSelectManager;

    public FSelectBaseAdapter(Activity activity)
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
