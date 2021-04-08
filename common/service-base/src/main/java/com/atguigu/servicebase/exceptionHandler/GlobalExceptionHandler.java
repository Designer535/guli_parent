package com.atguigu.servicebase.exceptionHandler;

import com.atguigu.commonutils.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler extends RuntimeException{

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public R error(Exception e){
        e.printStackTrace();
        return R.fail();
    }

    @ExceptionHandler(ArithmeticException.class)
    @ResponseBody
    public R error(ArithmeticException e){
        e.printStackTrace();
        return R.fail();
    }

    @ExceptionHandler(GuLiException.class)
    @ResponseBody
    public R error(GuLiException e){
        log.error(e.getMessage());
        e.printStackTrace();
        return R.fail().message(e.getMessage()).code(e.getCode());
    }
}
