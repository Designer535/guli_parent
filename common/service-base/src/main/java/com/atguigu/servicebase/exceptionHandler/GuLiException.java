package com.atguigu.servicebase.exceptionHandler;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GuLiException extends RuntimeException{
    private Integer code;
    private String message;
}
