package com.tomhurry.dynamic.datasource.core;

import org.aspectj.lang.annotation.Aspect;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.stereotype.Component;

/**
 * @author taozhi
 * @date 2020/12/21
 * @since 1.0.0
 */
@Aspect
@EnableAspectJAutoProxy(proxyTargetClass = true)
@Component
public class DataSourceAspect {
}
