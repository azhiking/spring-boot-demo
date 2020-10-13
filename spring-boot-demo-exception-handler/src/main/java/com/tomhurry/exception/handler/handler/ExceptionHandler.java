package com.tomhurry.exception.handler.handler;

import com.tomhurry.api.ApiResponse;
import com.tomhurry.exception.handler.exception.JsonException;
import com.tomhurry.exception.handler.exception.PageException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

/**
 * 统一异常处理
 *
 * @author taozhi
 * @date 2020/10/9
 * @since 1.0.0
 */
@ControllerAdvice
@Slf4j
public class ExceptionHandler {

    private static final String DEFAULT_ERROR_VIEW = "error";

    @org.springframework.web.bind.annotation.ExceptionHandler(value = JsonException.class)
    @ResponseBody
    public ApiResponse<JsonException> jsonErrorHandler(JsonException exception) {
        log.error("【JsonException】:{}", exception.getMessage());
        return ApiResponse.genExceptionResponse(exception);
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(value = PageException.class)
    public ModelAndView pageErrorHandler(PageException exception) {
        log.error("【PageException】:{}", exception.getMessage());
        ModelAndView view = new ModelAndView();
        view.addObject("message", exception.getMessage());
        view.setViewName(DEFAULT_ERROR_VIEW);
        return view;
    }
}
