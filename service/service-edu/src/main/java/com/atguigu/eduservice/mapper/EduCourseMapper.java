package com.atguigu.eduservice.mapper;

import com.atguigu.eduservice.entity.EduCourse;
import com.atguigu.eduservice.entity.vo.CourseFrontVo;
import com.atguigu.eduservice.entity.vo.CoursePublishVo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 课程 Mapper 接口
 * </p>
 *
 * @author testjava
 * @since 2021-03-16
 */
@Mapper
public interface EduCourseMapper extends BaseMapper<EduCourse> {
    CoursePublishVo getPublicCourseInfo(@Param(value = "courseId") String courseId);

    CourseFrontVo selectCourseFront(@Param(value = "id") String id);
}
