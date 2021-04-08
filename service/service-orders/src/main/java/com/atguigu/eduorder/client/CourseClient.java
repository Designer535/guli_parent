package com.atguigu.eduorder.client;

import com.atguigu.commonutils.CourseOrder;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Component
@FeignClient(value = "service-edu",fallback = CourseClientImp.class)
public interface CourseClient {
    @GetMapping("/eduservice/edu-course/getCourseInfoById/{id}")
    public CourseOrder getCourseInfoById(@PathVariable("id")String id);
}
