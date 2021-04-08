package com.atguigu.eduservice.service.impl;

import com.atguigu.eduservice.entity.EduComment;
import com.atguigu.eduservice.mapper.EduCommentMapper;
import com.atguigu.eduservice.service.EduCommentService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * 评论 服务实现类
 * </p>
 *
 * @author testjava
 * @since 2021-04-04
 */
@Service
public class EduCommentServiceImpl extends ServiceImpl<EduCommentMapper, EduComment> implements EduCommentService {

    @Override
    public Map<String, Object> getCommentPage(Page<EduComment> pageParam, String courseId) {
        QueryWrapper<EduComment>queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("course_id",courseId);
        baseMapper.selectMapsPage(pageParam,queryWrapper);
        Map<String,Object> map=new HashMap<>();
        map.put("total",pageParam.getTotal());
        map.put("current",pageParam.getCurrent());
        map.put("pages",pageParam.getPages());
        map.put("items",pageParam.getRecords());
        map.put("size",pageParam.getSize());
        map.put("hasNext",pageParam.hasNext());
        map.put("hasPrevious",pageParam.hasPrevious());
        return map;
    }
}
