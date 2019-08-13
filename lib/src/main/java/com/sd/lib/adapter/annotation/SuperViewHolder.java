package com.sd.lib.adapter.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface SuperViewHolder
{
    /**
     * 布局id
     *
     * @return
     */
    int layout();

    /**
     * 实体类型
     *
     * @return
     */
    Class<?> modelClass();
}