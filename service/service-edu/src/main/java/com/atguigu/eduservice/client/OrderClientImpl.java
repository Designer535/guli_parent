package com.atguigu.eduservice.client;

import com.atguigu.servicebase.exceptionHandler.GuLiException;
import org.springframework.stereotype.Component;

@Component
public class OrderClientImpl implements OrderClient{

    @Override
    public boolean isBuyCourse(String courseId, String memberId) {
        throw new GuLiException(20001,"失败");
    }
}
