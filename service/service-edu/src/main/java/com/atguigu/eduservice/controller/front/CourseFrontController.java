package com.atguigu.eduservice.controller.front;

import com.atguigu.commonutils.JwtUtils;
import com.atguigu.commonutils.R;
import com.atguigu.eduservice.client.OrderClient;
import com.atguigu.eduservice.entity.EduCourse;
import com.atguigu.eduservice.entity.chapter.ChapterVo;
import com.atguigu.eduservice.entity.vo.CourseFrontVo;
import com.atguigu.eduservice.entity.vo.CourseQueryVo;
import com.atguigu.eduservice.entity.vo.CourserInfoForm;
import com.atguigu.eduservice.service.EduChapterService;
import com.atguigu.eduservice.service.EduCourseService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin
@RequestMapping("/eduservice/courseFront")
public class CourseFrontController {

    @Autowired
    private EduCourseService eduCourseService;

    @Autowired
    private EduChapterService eduChapterService;

    @Autowired
    private OrderClient orderClient;

    @PostMapping("getFrontCourseList/{page}/{limit}")
    public R getFrontCourseList(@RequestBody CourseQueryVo courseQueryVo,@PathVariable("page")long page,@PathVariable("limit")long limit){
        Page<EduCourse> pageParam=new Page<>(page,limit);
        Map<String,Object> map=eduCourseService.getFrontCourseList(courseQueryVo,pageParam);
        return R.ok().data("map",map);
    }

    @GetMapping("getFrontCourseInfo/{id}")
    public R getFrontCourseInfo(@PathVariable("id")String id, HttpServletRequest request){
        CourseFrontVo courseInfo = eduCourseService.getFrontCourseInfo(id);
        List<ChapterVo> chapterVideo = eduChapterService.getChapterVideoByCourseId(id);
        String memberIdByJwtToken = JwtUtils.getMemberIdByJwtToken(request);
        boolean buyCourse=false;
        if(!StringUtils.isEmpty(memberIdByJwtToken)){
             buyCourse= orderClient.isBuyCourse(id, memberIdByJwtToken);
        }
        return R.ok().data("courseInfo",courseInfo).data("chapter",chapterVideo).data("isBuy",buyCourse);
    }
}
