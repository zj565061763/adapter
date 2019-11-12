package com.sd.lib.adapter.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface ASuperViewHolder
{
    /**
     * 布局id
     *
     * @return
     */
    int layoutId();

    /**
     * 实体类型
     *
     * @return
     */
    Class<?> modelClass() default ASuperViewHolder.class;
}