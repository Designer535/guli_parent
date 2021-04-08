package com.atguigu.eduorder.client;

import com.atguigu.commonutils.UcenterMemberOrder;
import com.atguigu.servicebase.exceptionHandler.GuLiException;
import org.springframework.stereotype.Component;

@Component
public class UcenterClientImpl implements UcenterClient{
    @Override
    public UcenterMemberOrder getMemberOrder(String id) {
        throw new GuLiException(20001,"失败");
    }
}
