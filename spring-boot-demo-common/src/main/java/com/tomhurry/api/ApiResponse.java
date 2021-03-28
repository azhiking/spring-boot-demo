package com.tomhurry.api;

import com.tomhurry.enums.ApiResponseEnum;
import com.tomhurry.exception.BaseException;

/**
 * api返回结果
 *
 * @author taozhi
 * @date 2020/10/9
 * @since 1.0.0
 */
public class ApiResponse<T> extends Result<T> {
    public ApiResponse(Integer code, String message, T data) {
        this.setCode(code);
        this.setMessage(message);
        this.setData(data);
    }

    public static <T> ApiResponse<T> genOkResponse() {
        return genOkResponse(null);
    }

    public static <T> ApiResponse<T> genOkResponse(T data) {
        return new ApiResponse<>(200, "success", data);
    }

    public static <T> ApiResponse<T> genFailedResponse() {
        return genFailedResponse(null);
    }

    public static <T> ApiResponse<T> genFailedResponse(T data) {
        return new ApiResponse<>(500, "failed", data);
    }

    public static <T> ApiResponse<T> genFailedResponse(String msg, T data) {
        return new ApiResponse<>(500, msg, data);
    }

    public static <T> ApiResponse<T> genResponse(ApiResponseEnum apiResponseEnum) {
        return new ApiResponse<>(apiResponseEnum.getCode(), apiResponseEnum.getMessage(), null);
    }

    public static <T> ApiResponse<T> genResponse(ApiResponseEnum apiResponseEnum, T data) {
        return new ApiResponse<>(apiResponseEnum.getCode(), apiResponseEnum.getMessage(), data);
    }

    public static <E extends BaseException, T> ApiResponse<T> genExceptionResponse(E e, T t) {
        return new ApiResponse<>(e.getCode(), e.getMessage(), t);
    }

    public static <E extends BaseException, T> ApiResponse<T> genExceptionResponse(E e) {
        return genExceptionResponse(e, null);
    }

}
