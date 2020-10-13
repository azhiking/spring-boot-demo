package com.tomhurry.exception.handler.controller;

import com.tomhurry.api.ApiResponse;
import com.tomhurry.enums.ApiResponseEnum;
import com.tomhurry.exception.handler.exception.JsonException;
import com.tomhurry.exception.handler.exception.PageException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

/**
 * 测试Controller
 *
 * @author taozhi
 * @date 2020/10/9
 * @since 1.0.0
 */
@Controller
public class TestController {

    @GetMapping("/json")
    @ResponseBody
    public ApiResponse jsonException() {
        throw new JsonException(ApiResponseEnum.UNKNOWN_ERROR);
    }

    @GetMapping("/page")
    public ModelAndView pageException() {
        throw new PageException(ApiResponseEnum.UNKNOWN_ERROR);
    }
}
