package com.atguigu.eduservice.service.impl;

import com.atguigu.eduservice.client.VoClient;
import com.atguigu.eduservice.entity.EduVideo;
import com.atguigu.eduservice.mapper.EduVideoMapper;
import com.atguigu.eduservice.service.EduVideoService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程视频 服务实现类
 * </p>
 *
 * @author testjava
 * @since 2021-03-16
 */
@Service
public class EduVideoServiceImpl extends ServiceImpl<EduVideoMapper, EduVideo> implements EduVideoService {

    @Autowired
    private VoClient voClient;

    @Override
    public void removeByCourseId(String courseId) {

        QueryWrapper<EduVideo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("course_id", courseId);
        queryWrapper.select("video_source_id");
        List<EduVideo> eduVideos = baseMapper.selectList(queryWrapper);

        List<String> list = new ArrayList<>();
        for (EduVideo eduVideo : eduVideos) {
            if (!StringUtils.isEmpty(eduVideo.getVideoSourceId())) {
                list.add(eduVideo.getVideoSourceId());
            }
        }

        if (list.size() > 0) {
            voClient.deleteBatch(list);
        }

        queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("course_id", courseId);
        baseMapper.delete(queryWrapper);
    }
}
