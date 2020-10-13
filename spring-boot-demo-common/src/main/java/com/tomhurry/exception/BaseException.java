package com.tomhurry.exception;

import com.tomhurry.enums.ApiResponseEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 自定义异常基类
 *
 * @author taozhi
 * @date 2020/10/9
 * @since 1.0.0
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class BaseException extends RuntimeException {
    private Integer code;
    private String message;

    public BaseException(ApiResponseEnum apiResponseEnum) {
        this.code = apiResponseEnum.getCode();
        this.message = apiResponseEnum.getMessage();
    }

    public BaseException(Integer code, String message) {
        super(message);
        this.code = code;
        this.message = message;
    }
}
