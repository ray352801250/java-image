package com.dodoca.create_image.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.List;
import java.util.Set;

import static com.dodoca.create_image.exception.ResultEnum.*;

/**
 * @Author: TianGuangHui
 * @Date: 2018/12/17 15:52
 * @Description:
 */
@ControllerAdvice
public class MyControllerAdvice {
    private static final Logger logger = LoggerFactory.getLogger(MyControllerAdvice.class);



    /**
     * 全局异常捕捉处理
     * @param ex
     * @return
     */
    @ResponseBody
    @ExceptionHandler(value = Exception.class)
    public BaseResult<Boolean> errorHandler(Exception ex, HttpServletRequest request) {
        logger.error(ex.getMessage(), ex);
        return ResultUtil.error(UNKNOWN_ERROR);
    }


    /**
     * 捕获集合参数校检异常
     * @param exception
     * @return
     */
    @ExceptionHandler(value = ConstraintViolationException.class)
    @ResponseBody
    public BaseResult handleConstraintViolationException(ConstraintViolationException exception) {
        String msg = "";
        Set<ConstraintViolation<?>> cves = exception.getConstraintViolations();
        for (ConstraintViolation<?> constraintViolation : cves) {
            if ("".equals(msg)) {
                msg = constraintViolation.getMessage();
            }else {
                msg = msg + ";" + constraintViolation.getMessage();
            }
        }
        logger.error(msg, exception);
        return ResultUtil.error(PARAMETRIC_TESTS_ERROR);
    }

    /**
     * 捕获@Validate校验抛出的异常
     * @param exception
     * @return
     */
    @ExceptionHandler(value = {MethodArgumentNotValidException.class, BindException.class})
    @ResponseBody
    public BaseResult<String> handleMethodArgumentNotValidException(Exception exception, HttpServletRequest request) {
        BaseResult<String> result = new BaseResult<String>();
        BindingResult bindingResult = null;
        if (exception instanceof MethodArgumentNotValidException) {
            bindingResult = ((MethodArgumentNotValidException) exception).getBindingResult();
        }
        if (exception instanceof BindException) {
            bindingResult = ((BindException) exception).getBindingResult();
        }
        result.setCode(1010);
        result.setData("URI: " + request.getRequestURI());
        if (bindingResult == null){
            result.setMsg("参数校检出现未知异常");
            logger.error("参数校检出现未知异常", exception);
            return result;
        }
        result.setMsg(getMsg(bindingResult));
        result.setData("false");
        return result;
    }

    /**
     * 获取@Validate校验抛出的异常信息
     * @param bindingResult
     * @return
     */
    private String getMsg(BindingResult bindingResult) {
        String msg = "";
        if (bindingResult.hasErrors()) {
            List<ObjectError> errors = bindingResult.getAllErrors();
            for (ObjectError objectError : errors){
                FieldError fieldError = (FieldError) objectError;
                logger.error("Data check failure : object{"+fieldError.getObjectName()+"},field{"+fieldError.getField()+
                        "},errorMessage{"+fieldError.getDefaultMessage()+"}");
                if ("".equals(msg)) {
                    msg = fieldError.getDefaultMessage();
                }else {
                    msg = msg + ";" + fieldError.getDefaultMessage();
                }
            }
        }
        return msg;
    }

    /**
     * 接口请求类型不符的异常处理
     * @param ex
     * @param request
     * @return
     */
    @ResponseBody
    @ExceptionHandler(value = HttpRequestMethodNotSupportedException.class)
    public BaseResult httpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException ex, HttpServletRequest request) {
        logger.error(ex.getMessage(), ex);
        return ResultUtil.error(REQUEST_METHOD_ERROR);
    }


    /**
     * 自定义异常捕捉处理
     * @param ex
     * @return
     */
    @ResponseBody
    @ExceptionHandler(value = MyServiceException.class)
    public BaseResult myServiceException(MyServiceException ex, HttpServletRequest request) {
        return ResultUtil.error(ex.getCode(), ex.getMessage());
    }

}
