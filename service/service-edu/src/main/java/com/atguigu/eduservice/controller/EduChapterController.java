package com.atguigu.eduservice.controller;


import com.atguigu.commonutils.R;
import com.atguigu.eduservice.entity.EduChapter;
import com.atguigu.eduservice.entity.chapter.ChapterVo;
import com.atguigu.eduservice.service.EduChapterService;
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
@RequestMapping("/eduservice/edu-chapter")
@CrossOrigin
public class EduChapterController {

    @Autowired
    private EduChapterService eduChapterService;

    @GetMapping("getChapterVideo/{courseId}")
    public R getChapterVideo(@PathVariable("courseId") String courseId){
        List<ChapterVo> list=eduChapterService.getChapterVideoByCourseId(courseId);
        return R.ok().data("allChapterVideo",list);
    }

    @GetMapping("/getChapter/{chapterId}")
    public R getChapter(@PathVariable("chapterId") String chapterId){
        EduChapter chapter = eduChapterService.getById(chapterId);
        return R.ok().data("chapter",chapter);
    }

    @PostMapping("/addChapter")
    public R addChapter(@RequestBody EduChapter eduChapter){
        boolean save = eduChapterService.save(eduChapter);
        return R.ok();
    }

    @PostMapping("/updateChapter")
    public R updateChapter(@RequestBody EduChapter eduChapter){
        boolean b = eduChapterService.updateById(eduChapter);
        return R.ok();
    }
    @DeleteMapping("/delete/{chapterId}")
    public R delete(@PathVariable("chapterId") String chapterId){
        boolean result=eduChapterService.deleteChapter(chapterId);
        if(result){
            return R.ok();
        }else{
            return R.fail();
        }
    }
}

