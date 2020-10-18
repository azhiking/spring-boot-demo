package com.tomhurry.annotation.annotation;

import org.springframework.core.annotation.AliasFor;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.lang.annotation.*;

/**
 * 消息路由自定义注解
 *
 * @author taozhi
 * @date 2020/10/13
 * @since 1.0.0
 */
@Target(value = {ElementType.TYPE})
@Retention(value = RetentionPolicy.RUNTIME)
@Documented
@MessageHandler
public @interface MessageRouterMapping {

    String[] action() default {};

    String object() default "";

    String type() default "";

    String status() default "";

    Class[] before() default {};

    Class[] after() default {};
}
