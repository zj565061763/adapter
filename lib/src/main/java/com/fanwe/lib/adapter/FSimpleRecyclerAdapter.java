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

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.fanwe.lib.adapter.viewholder.FRecyclerViewHolder;

public abstract class FSimpleRecyclerAdapter<T> extends FRecyclerAdapter<T>
{
    @Override
    public FRecyclerViewHolder<T> onCreateVHolder(ViewGroup parent, int viewType)
    {
        final int layoutId = getLayoutId(parent, viewType);
        final View itemView = LayoutInflater.from(getContext()).inflate(layoutId, parent, false);
        FRecyclerViewHolder<T> holder = new FRecyclerViewHolder<T>(itemView)
        {
            @Override
            public void onBindData(int position, T model)
            {
            }
        };
        return holder;
    }

    /**
     * 返回布局id
     *
     * @param parent
     * @param viewType
     * @return
     */
    public abstract int getLayoutId(ViewGroup parent, int viewType);
}
