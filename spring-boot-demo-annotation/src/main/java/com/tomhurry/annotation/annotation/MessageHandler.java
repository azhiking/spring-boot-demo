package com.tomhurry.annotation.annotation;

import org.springframework.stereotype.Service;

import java.lang.annotation.*;

/**
 * 用来修饰处理message的handler
 *
 * @author taozhi
 * @date 2020/10/14
 * @since 1.0.0
 */
@Target(value = {ElementType.ANNOTATION_TYPE, ElementType.TYPE})
@Retention(value = RetentionPolicy.RUNTIME)
@Documented
@Service
public @interface MessageHandler {
    String value() default "";
}
