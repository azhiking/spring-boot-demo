package com.tomhurry.exception.handler.exception;

import com.tomhurry.enums.ApiResponseEnum;
import com.tomhurry.exception.BaseException;
import lombok.Getter;

/**
 * JSON异常
 *
 * @author taozhi
 * @date 2020/10/9
 * @since 1.0.0
 */
@Getter
public class JsonException extends BaseException {
    public JsonException(ApiResponseEnum apiResponseEnum) {
        super(apiResponseEnum);
    }

    public JsonException(Integer code, String message) {
        super(code, message);
    }
}
