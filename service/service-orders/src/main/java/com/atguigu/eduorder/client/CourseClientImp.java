package com.atguigu.eduorder.client;

import com.atguigu.commonutils.CourseOrder;
import com.atguigu.servicebase.exceptionHandler.GuLiException;
import org.springframework.stereotype.Component;

@Component
public class CourseClientImp implements CourseClient{
    @Override
    public CourseOrder getCourseInfoById(String id) {
        throw new GuLiException(20001,"失败");
    }
}
