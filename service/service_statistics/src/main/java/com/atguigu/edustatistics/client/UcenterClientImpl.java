package com.atguigu.edustatistics.client;

import com.atguigu.commonutils.R;
import com.atguigu.servicebase.exceptionHandler.GuLiException;
import org.springframework.stereotype.Component;

@Component
public class UcenterClientImpl implements UcenterClient{
    @Override
    public R getRegisterCount(String date) {
        throw new GuLiException(20001,"失败");
    }
}
