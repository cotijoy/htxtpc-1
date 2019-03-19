package com.magustek.com.htxtpc.config;

import com.magustek.com.htxtpc.util.base.BaseResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 全局controller异常处理
 * */

@Slf4j
@ControllerAdvice
public class ControllerExceptionFilter {
    /**
     * 全局异常捕捉处理
     */
    @ResponseBody
    @ExceptionHandler
    public String errorHandler(Exception ex) {
        BaseResponse resp = new BaseResponse();
        log.error(ex.getMessage());
        ex.printStackTrace();
        return resp.setStateCode(BaseResponse.ERROR).setMsg(ex.getMessage()).toJson();
    }
}
