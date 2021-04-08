package com.atguigu.eduservice.service.impl;

import com.atguigu.eduservice.entity.EduTeacher;
import com.atguigu.eduservice.entity.vo.TeacherQuery;
import com.atguigu.eduservice.mapper.EduTeacherMapper;
import com.atguigu.eduservice.service.EduTeacherService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 讲师 服务实现类
 * </p>
 *
 * @author testjava
 * @since 2021-03-02
 */
@Service
public class EduTeacherServiceImpl extends ServiceImpl<EduTeacherMapper, EduTeacher> implements EduTeacherService {

    @Override
    public void pageQuery(Page<EduTeacher> pageParam, TeacherQuery teacherQuery) {
        QueryWrapper<EduTeacher> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByAsc("sort");
        if (teacherQuery == null) {
            baseMapper.selectPage(pageParam, queryWrapper);
            return;
        }
        String name = teacherQuery.getName();
        Integer level = teacherQuery.getLevel();
        String begin = teacherQuery.getBegin();
        String end = teacherQuery.getEnd();

        if (!StringUtils.isEmpty(name)) {
            queryWrapper.like("name", name);
        }
        if (!StringUtils.isEmpty(level)) {
            queryWrapper.eq("level", level);
        }
        if (!StringUtils.isEmpty(begin)) {
            queryWrapper.ge("gmt_create", begin);
        }
        if (!StringUtils.isEmpty(end)) {
            queryWrapper.gt("gmt_create", end);
        }
        queryWrapper.orderByDesc("gmt_create");
        baseMapper.selectPage(pageParam, queryWrapper);
    }

    @Cacheable(key = "'teachers'", value = "teacherList")
    @Override
    public List<EduTeacher> getIndexTeacherList() {
        QueryWrapper<EduTeacher> wrapper1 = new QueryWrapper<>();
        wrapper1.orderByDesc("id");
        wrapper1.last("limit 4");
        return baseMapper.selectList(wrapper1);
    }

    @Override
    public Map<String, Object> getTeacherFront(Page<EduTeacher> pages) {
        QueryWrapper<EduTeacher> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("id");
        baseMapper.selectPage(pages, queryWrapper);
        Map<String, Object> map = new HashMap<>();
        map.put("total", pages.getTotal());
        map.put("size", pages.getSize());
        map.put("current", pages.getCurrent());
        map.put("items", pages.getRecords());
        map.put("hasNext", pages.hasNext());
        map.put("hasPrevious", pages.hasPrevious());
        map.put("pages",pages.getPages());
        return map;
    }
}
