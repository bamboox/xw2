package com.ace.common.handler;

import com.ace.common.exception.DataFormatException;
import com.ace.common.exception.ResourceNotFoundException;
import com.ace.common.model.ApiErrorResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;

import javax.servlet.http.HttpServletResponse;

/**
 * Created by bamboo on 17-12-1.
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    /**
     * 处理所有不可知的异常
     *
     * @param e
     * @return
     *//*
    @ExceptionHandler(Exception.class)
    @ResponseBody
    AppResponse handleException(Exception e){
        LOGGER.error(e.getMessage(), e);

        AppResponse response = new AppResponse();
        response.setFail("操作失败！");
        return response;
    }

    *//**
     * 处理所有业务异常
     *
     * @param e
     * @return
     *//*
    @ExceptionHandler(BusinessException.class)
    @ResponseBody
    AppResponse handleBusinessException(BusinessException e){
        LOGGER.error(e.getMessage(), e);

        AppResponse response = new AppResponse();
        response.setFail(e.getMessage());
        return response;
    }

    */

    /**
     * 处理所有接口数据验证异常
     *
     * @param e
     * @return
     *//*
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseBody
    AppResponse handleMethodArgumentNotValidException(MethodArgumentNotValidException e){
        LOGGER.error(e.getMessage(), e);

        AppResponse response = new AppResponse();
        response.setFail(e.getBindingResult().getAllErrors().get(0).getDefaultMessage());
        return response;
    }*/
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(DataFormatException.class)
    @ResponseBody
    public ApiErrorResponse handleDataStoreException(DataFormatException ex, WebRequest request, HttpServletResponse response) {
        LOGGER.info("Converting Data Store exception to RestResponse : " + ex.getMessage());
        return new ApiErrorResponse(ex.getMessage(), "You messed up.");
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(ResourceNotFoundException.class)
    @ResponseBody
    public ApiErrorResponse handleResourceNotFoundException(ResourceNotFoundException ex, WebRequest request, HttpServletResponse response) {
        LOGGER.info("ResourceNotFoundException handler:" + ex.getMessage());
        return new ApiErrorResponse(ex.getMessage(), "Sorry I couldn't find it.");
    }
}