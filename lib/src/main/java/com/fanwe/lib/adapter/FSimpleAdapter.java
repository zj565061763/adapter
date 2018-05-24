/*
 * Copyright (C) 2017 zhengjun, fanwe (http://www.fanwe.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.fanwe.lib.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public abstract class FSimpleAdapter<T> extends FBaseAdapter<T>
{
    public FSimpleAdapter(Activity activity)
    {
        super(activity);
    }

    @Override
    protected View onGetView(int position, View convertView, ViewGroup parent)
    {
        if (convertView == null)
        {
            final int layoutId = getLayoutId(position, convertView, parent);
            if (layoutId != 0)
            {
                convertView = LayoutInflater.from(getActivity()).inflate(layoutId, parent, false);
                onInit(position, convertView, parent);
            }
        }
        onBindData(position, convertView, parent, getItem(position));
        return convertView;
    }

    public abstract int getLayoutId(int position, View convertView, ViewGroup parent);

    public void onInit(int position, View convertView, ViewGroup parent)
    {
    }

    public abstract void onBindData(int position, View convertView, ViewGroup parent, T model);
}
