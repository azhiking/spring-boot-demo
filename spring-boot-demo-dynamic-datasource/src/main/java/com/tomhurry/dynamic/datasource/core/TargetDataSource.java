package com.tomhurry.dynamic.datasource.core;

import java.lang.annotation.*;

/**
 * 多数据源注解
 * 指定要使用的数据源
 *
 * @author taozhi
 * @date 2020/12/21
 * @since 1.0.0
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface TargetDataSource {
    String value();
}
