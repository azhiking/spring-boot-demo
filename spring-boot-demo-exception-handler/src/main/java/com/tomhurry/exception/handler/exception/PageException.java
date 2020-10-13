package com.tomhurry.exception.handler.exception;

import com.tomhurry.enums.ApiResponseEnum;
import com.tomhurry.exception.BaseException;
import lombok.Getter;

/**
 * 页面异常
 *
 * @author taozhi
 * @date 2020/10/9
 * @since 1.0.0
 */
@Getter
public class PageException extends BaseException {
    public PageException(ApiResponseEnum apiResponseEnum) {
        super(apiResponseEnum);
    }

    public PageException(Integer code, String message) {
        super(code, message);
    }
}
