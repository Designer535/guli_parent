package com.atguigu.eduservice.service;

import com.atguigu.eduservice.entity.EduCourse;
import com.atguigu.eduservice.entity.vo.*;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author testjava
 * @since 2021-03-16
 */
public interface EduCourseService extends IService<EduCourse> {

    String saveCourser(CourserInfoForm courserInfoForm);

    CourserInfoForm getCourseInfoById(String courseId);

    void updateCourseInfo(CourserInfoForm courserInfoForm);

    CoursePublishVo getPublishCourseInfo(String courseId);

    void pageQuery(Page<EduCourse> pageParam, CourseQuery courseQuery);

    void deleteById(String courseId);

    List<EduCourse> getIndexCourseList();

    Map<String, Object> getFrontCourseList(CourseQueryVo courseQueryVo, Page<EduCourse> pageParam);

    CourseFrontVo getFrontCourseInfo(String id);
}
