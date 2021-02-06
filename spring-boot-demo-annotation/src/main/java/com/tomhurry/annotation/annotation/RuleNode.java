package com.tomhurry.annotation.annotation;

import com.tomhurry.annotation.model.MessageHandler;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 消息路由自定义注解
 *
 * @author taozhi
 * @date 2020/10/13
 * @since 1.0.0
 */
@Target(value = {ElementType.TYPE})
@Retention(value = RetentionPolicy.RUNTIME)
public @interface RuleNode {
    /**
     * 匹配消息的action属性
     *
     * @return
     */
    String[] action() default {};

    /**
     * 匹配消息的object属性
     *
     * @return
     */
    String object() default "";

    /**
     * 匹配消息的来源
     *
     * @return
     */
    String type() default "";

    /**
     * 匹配消息的状态
     *
     * @return
     */
    String status() default "";

    /**
     * 处理消息前需要执行的handler
     *
     * @return
     */
    Class<? extends MessageHandler>[] before() default {};

    /**
     * 处理消息后需要执行的handler
     *
     * @return
     */
    Class<? extends MessageHandler>[] after() default {};
}
