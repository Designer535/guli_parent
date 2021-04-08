package com.atguigu.eduservice.service.impl;

import com.atguigu.eduservice.entity.EduChapter;
import com.atguigu.eduservice.entity.EduVideo;
import com.atguigu.eduservice.entity.chapter.ChapterVo;
import com.atguigu.eduservice.entity.chapter.VideoVo;
import com.atguigu.eduservice.mapper.EduChapterMapper;
import com.atguigu.eduservice.service.EduChapterService;
import com.atguigu.eduservice.service.EduVideoService;
import com.atguigu.servicebase.exceptionHandler.GuLiException;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程 服务实现类
 * </p>
 *
 * @author testjava
 * @since 2021-03-16
 */
@Service
public class EduChapterServiceImpl extends ServiceImpl<EduChapterMapper, EduChapter> implements EduChapterService {

    @Autowired
    private EduVideoService eduVideoService;

    @Override
    public List<ChapterVo> getChapterVideoByCourseId(String courseId) {
        List<ChapterVo> chapterVos=new ArrayList<>();
        QueryWrapper<EduChapter>queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("course_id",courseId);
        List<EduChapter> eduChapters = baseMapper.selectList(queryWrapper);

        QueryWrapper<EduVideo> queryWrapper1=new QueryWrapper<>();
        queryWrapper1.eq("course_id",courseId);
        List<EduVideo> list = eduVideoService.list(queryWrapper1);

        ChapterVo chapterVo;
        VideoVo videoVo;

        for(EduChapter eduChapter:eduChapters){
            chapterVo=new ChapterVo();
            BeanUtils.copyProperties(eduChapter,chapterVo);
            chapterVos.add(chapterVo);
            for(EduVideo eduVideo:list){
                if(eduVideo.getChapterId().equals(eduChapter.getId())) {
                    videoVo = new VideoVo();
                    BeanUtils.copyProperties(eduVideo, videoVo);
                    chapterVo.getChildren().add(videoVo);
                }
            }
        }

        return chapterVos;
    }

    @Override
    public boolean deleteChapter(String chapterId) {
        QueryWrapper<EduVideo> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("chapter_id",chapterId);
        List<EduVideo> list = eduVideoService.list(queryWrapper);
        if(list.size()>0){
            throw new GuLiException(20001,"删除章节失败");
        }else{
            int result = baseMapper.deleteById(chapterId);
            return result>0;
        }
    }

    @Override
    public void removeByCourseId(String courseId) {
        QueryWrapper<EduChapter> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("course_id",courseId);
        baseMapper.delete(queryWrapper);
    }
}
