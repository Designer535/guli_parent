package com.atguigu.eduservice.controller.front;

import com.atguigu.commonutils.R;
import com.atguigu.eduservice.entity.EduCourse;
import com.atguigu.eduservice.entity.EduTeacher;
import com.atguigu.eduservice.service.EduCourseService;
import com.atguigu.eduservice.service.EduTeacherService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin
@RequestMapping("eduservice/teacherFront")
public class TeacherFrontController {

    @Autowired
    private EduTeacherService teacherService;

    @Autowired
    private EduCourseService eduCourseService;

    @GetMapping("getTeacherFront/{page}/{limit}")
    public R getTeacherFront(@PathVariable("page")long page,@PathVariable("limit")long limit){
        Page<EduTeacher> pages=new Page<>(page,limit);
        Map<String,Object> map=teacherService.getTeacherFront(pages);
        return R.ok().data("map",map);
    }

    @GetMapping("getTeacherInfo/{teacherId}")
    public R getTeacherInfo(@PathVariable("teacherId")String teacherId){
        EduTeacher teacher = teacherService.getById(teacherId);

        QueryWrapper<EduCourse> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("teacher_id",teacher.getId());
        List<EduCourse> list = eduCourseService.list(queryWrapper);

        return R.ok().data("teacher",teacher).data("list",list);
    }

}
