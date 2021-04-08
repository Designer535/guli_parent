package com.atguigu.eduservice.service.impl;

import com.atguigu.eduservice.entity.EduCourse;
import com.atguigu.eduservice.entity.EduCourseDescription;
import com.atguigu.eduservice.entity.vo.*;
import com.atguigu.eduservice.mapper.EduCourseDescriptionMapper;
import com.atguigu.eduservice.mapper.EduCourseMapper;
import com.atguigu.eduservice.service.EduChapterService;
import com.atguigu.eduservice.service.EduCourseService;
import com.atguigu.eduservice.service.EduVideoService;
import com.atguigu.servicebase.exceptionHandler.GuLiException;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 课程 服务实现类
 * </p>
 *
 * @author testjava
 * @since 2021-03-16
 */
@Service
public class EduCourseServiceImpl extends ServiceImpl<EduCourseMapper, EduCourse> implements EduCourseService {

    @Autowired
    private EduCourseDescriptionMapper eduCourseDescriptionMapper;

    @Autowired
    private EduVideoService eduVideoService;

    @Autowired
    private EduChapterService eduChapterService;



    @Override
    @Transactional
    public String saveCourser(CourserInfoForm courserInfoForm) {
        EduCourse eduCourse = new EduCourse();
        BeanUtils.copyProperties(courserInfoForm, eduCourse);
        int insert = baseMapper.insert(eduCourse);

        if (insert == 0) {
            throw new GuLiException(20001, "添加课程信息失败");
        }

        EduCourseDescription eduCourseDescription = new EduCourseDescription();
        eduCourseDescription.setId(eduCourse.getId());
        eduCourseDescription.setDescription(courserInfoForm.getDescription());
        eduCourseDescriptionMapper.insert(eduCourseDescription);
        return eduCourse.getId();
    }

    @Override
    public CourserInfoForm getCourseInfoById(String courseId) {
        CourserInfoForm courserInfoForm = new CourserInfoForm();
        EduCourse eduCourse = baseMapper.selectById(courseId);
        BeanUtils.copyProperties(eduCourse, courserInfoForm);

        EduCourseDescription eduCourseDescription = eduCourseDescriptionMapper.selectById(courseId);
        courserInfoForm.setDescription(eduCourseDescription.getDescription());
        return courserInfoForm;
    }

    @Override
    @Transactional
    public void updateCourseInfo(CourserInfoForm courserInfoForm) {
        EduCourse eduCourse = new EduCourse();
        EduCourseDescription eduCourseDescription = new EduCourseDescription();
        BeanUtils.copyProperties(courserInfoForm, eduCourse);
        BeanUtils.copyProperties(courserInfoForm, eduCourseDescription);

        int update = baseMapper.updateById(eduCourse);
        if (update == 0) {
            throw new GuLiException(20001, "修改课程失败");
        }
        eduCourseDescriptionMapper.updateById(eduCourseDescription);
    }

    @Override
    public CoursePublishVo getPublishCourseInfo(String courseId) {
        return baseMapper.getPublicCourseInfo(courseId);
    }


    @Override
    public void pageQuery(Page<EduCourse> pageParam, CourseQuery courseQuery) {
        QueryWrapper<EduCourse> queryWrapper = new QueryWrapper<>();
        if (!StringUtils.isEmpty(courseQuery.getTitle())) {
            queryWrapper.like("title", courseQuery.getTitle());
        }
        if (!StringUtils.isEmpty(courseQuery.getTeacherId())) {
            queryWrapper.eq("teacher_id", courseQuery.getTeacherId());
        }
        if (!StringUtils.isEmpty(courseQuery.getStatus())) {
            queryWrapper.eq("status", courseQuery.getStatus());
        }
        queryWrapper.orderByDesc("gmt_create");
        baseMapper.selectPage(pageParam, queryWrapper);
    }

    @Override
    public void deleteById(String courseId) {
        eduVideoService.removeByCourseId(courseId);

        eduChapterService.removeByCourseId(courseId);

        eduCourseDescriptionMapper.deleteById(courseId);

        int result = baseMapper.deleteById(courseId);

        if (result == 0) {
            throw new GuLiException(20001, "删除失败");
        }
    }

    @Cacheable(key = "'courses'", value = "courseList")
    @Override
    public List<EduCourse> getIndexCourseList() {
        QueryWrapper<EduCourse> wrapper2 = new QueryWrapper<>();
        wrapper2.orderByDesc("id");
        wrapper2.last("limit 8");
        return baseMapper.selectList(wrapper2);
    }


    @Override
    public Map<String, Object> getFrontCourseList(CourseQueryVo courseQueryVo, Page<EduCourse> pageParam) {
        QueryWrapper<EduCourse> queryWrapper = new QueryWrapper<>();
        if (courseQueryVo == null) {
            baseMapper.selectPage(pageParam, queryWrapper);
        } else {
            if (!StringUtils.isEmpty(courseQueryVo.getSubjectParentId())) {
                queryWrapper.eq("subject_parent_id", courseQueryVo.getSubjectParentId());
            }
            if (!StringUtils.isEmpty(courseQueryVo.getSubjectId())) {
                queryWrapper.eq("subject_id", courseQueryVo.getSubjectId());
            }
            if (!StringUtils.isEmpty(courseQueryVo.getBuyCountSort())) {
                queryWrapper.orderByDesc("buy_count");
            }
            if (!StringUtils.isEmpty(courseQueryVo.getPriceSort())) {
                queryWrapper.orderByDesc("price");
            }
            if (!StringUtils.isEmpty(courseQueryVo.getGmtCreateSort())) {
                queryWrapper.orderByDesc("gmt_create");
            }
            baseMapper.selectPage(pageParam, queryWrapper);
        }

        Map<String, Object> map = new HashMap<>();
        map.put("total", pageParam.getTotal());
        map.put("pages", pageParam.getPages());
        map.put("current", pageParam.getCurrent());
        map.put("size", pageParam.getSize());
        map.put("items", pageParam.getRecords());
        map.put("hasNext", pageParam.hasNext());
        map.put("hasPrevious", pageParam.hasPrevious());

        return map;
    }

    @Override
    public CourseFrontVo getFrontCourseInfo(String id) {
        CourseFrontVo courseFrontVo = baseMapper.selectCourseFront(id);
        return courseFrontVo;
    }
}
