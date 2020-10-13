package com.tomhurry.api;

import lombok.Data;

/**
 * 返回结果
 *
 * @author taozhi
 * @date 2020/10/9
 * @since 1.0.0
 */
@Data
public class Result<T> {
    private Integer code;

    private String message;

    private T data;
}
