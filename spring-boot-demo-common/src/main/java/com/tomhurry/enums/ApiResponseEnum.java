package com.tomhurry.enums;

import lombok.Getter;

/**
 * api错误枚举
 *
 * @author taozhi
 * @date 2020/10/9
 * @since 1.0.0
 */
@Getter
public enum ApiResponseEnum {
    /**
     * 成功
     */
    OK(200, "success"),

    UNKNOWN_ERROR(500, "unknown error"),

    DATA_EXIST(401, "数据已存在");


    private Integer code;
    private String message;

    ApiResponseEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
