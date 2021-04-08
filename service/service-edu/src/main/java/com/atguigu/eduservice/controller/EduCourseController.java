package com.atguigu.eduservice.controller;


import com.atguigu.commonutils.CourseOrder;
import com.atguigu.commonutils.R;
import com.atguigu.eduservice.entity.EduCourse;
import com.atguigu.eduservice.entity.EduTeacher;
import com.atguigu.eduservice.entity.vo.*;
import com.atguigu.eduservice.service.EduCourseService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 课程 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2021-03-16
 */
@RestController
@RequestMapping("/eduservice/edu-course")
@CrossOrigin
public class EduCourseController {

    @Autowired
    private EduCourseService eduCourseService;

    @PostMapping("/addCourseInfo")
    public R saveCourse(@RequestBody CourserInfoForm courserInfoForm){
        String id=eduCourseService.saveCourser(courserInfoForm);
        return R.ok().data("id",id);
    }

    @GetMapping("/getCourseInfo/{courseId}")
    public R getCourseInfo(@PathVariable("courseId")String courseId){
        CourserInfoForm courserInfoForm=eduCourseService.getCourseInfoById(courseId);
        return R.ok().data("courseInfo",courserInfoForm);
    }

    @PostMapping("/updateCourseInfo")
    public R updateCourseInfo(@RequestBody CourserInfoForm courserInfoForm){
        eduCourseService.updateCourseInfo(courserInfoForm);
        return R.ok();
    }

    @GetMapping("/getPublishCourseInfo/{courseId}")
    public R getPublishCourseInfo(@PathVariable("courseId") String courseId){
        CoursePublishVo coursePublishVo=eduCourseService.getPublishCourseInfo(courseId);
        return R.ok().data("coursePublish",coursePublishVo);
    }

    @PostMapping("publishCourse/{courseId}")
    public R publishCourse(@PathVariable("courseId")String courseId){
        EduCourse eduCourse=new EduCourse();
        eduCourse.setId(courseId);
        eduCourse.setStatus("Normal");
        eduCourseService.updateById(eduCourse);
        return R.ok();
    }

    @ApiOperation(value = "条件分页查询")
    @PostMapping("pageCourseCondition/{page}/{limit}")
    public R pageQuery(
            @ApiParam(name = "page", value = "当前页数", required = true)
            @PathVariable("page") Long page,
            @ApiParam(name = "limit", value = "每页记录数", required = true)
            @PathVariable("limit") Long limit,
            @ApiParam(name = "courseQuery", value = "查询条件", required = false)
            @RequestBody(required = false) CourseQuery courseQuery) {
        Page<EduCourse> pageParam = new Page<>(page, limit);
        eduCourseService.pageQuery(pageParam, courseQuery);
        List<EduCourse> records = pageParam.getRecords();
        long total = pageParam.getTotal();
        return R.ok().data("total", total).data("rows", records);
    }

    @DeleteMapping("deleteCourse/{courseId}")
    public R deleteCourse(@PathVariable("courseId")String courseId){
        eduCourseService.deleteById(courseId);
        return R.ok();
    }

    @GetMapping("getCourseInfoById/{id}")
    public CourseOrder getCourseInfoById(@PathVariable("id")String id){
        CourseFrontVo frontCourseInfo = eduCourseService.getFrontCourseInfo(id);
        CourseOrder courseOrder=new CourseOrder();
        BeanUtils.copyProperties(frontCourseInfo,courseOrder);
        return courseOrder;
    }
}

