package com.fanwe.lib.adapter.select;

import android.app.Activity;

import com.fanwe.lib.adapter.FSimpleRecyclerAdapter;
import com.fanwe.lib.selectmanager.FSelectManager;

public abstract class FSelectSimpleRecyclerAdapter<T> extends FSimpleRecyclerAdapter<T> implements FSelectableAdapter<T>
{
    private FSelectManager<T> mSelectManager;

    public FSelectSimpleRecyclerAdapter(Activity activity)
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
