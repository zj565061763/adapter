package com.fanwe.lib.adapter.select;

import android.app.Activity;

import com.fanwe.lib.adapter.FSimpleAdapter;
import com.fanwe.lib.selectmanager.FSelectManager;

public abstract class FSelectSimpleAdapter<T> extends FSimpleAdapter<T> implements FSelectableAdapter<T>
{
    private FSelectManager<T> mSelectManager;

    public FSelectSimpleAdapter(Activity activity)
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
