package com.atguigu.eduservice.controller;


import com.alibaba.excel.util.StringUtils;
import com.atguigu.commonutils.R;
import com.atguigu.eduservice.client.VoClient;
import com.atguigu.eduservice.entity.EduVideo;
import com.atguigu.eduservice.service.EduVideoService;
import com.atguigu.servicebase.exceptionHandler.GuLiException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 课程视频 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2021-03-16
 */
@RestController
@RequestMapping("/eduservice/edu-video")
@CrossOrigin
public class EduVideoController {

    @Autowired
    private EduVideoService eduVideoService;

    @Autowired
    private VoClient voClient;

    @PostMapping("/addEduVideo")
    public R addEduVideo(@RequestBody EduVideo eduVideo) {
        eduVideoService.save(eduVideo);
        return R.ok();
    }

    @GetMapping("/getEduVideo/{videoId}")
    public R getEduVideo(@PathVariable("videoId") String videoId) {
        EduVideo eduVideo = eduVideoService.getById(videoId);
        return R.ok().data("eduVideo", eduVideo);
    }

    @PostMapping("/updateEduVideo")
    public R updateEduVideo(@RequestBody EduVideo eduVideo) {
        eduVideoService.updateById(eduVideo);
        return R.ok();
    }

    @DeleteMapping("/deleteEduVideo/{videoId}")
    @Transactional
    public R deleteEduVideo(@PathVariable("videoId") String videoId) {
        EduVideo eduVideo = eduVideoService.getById(videoId);
        String videoSource = eduVideo.getVideoSourceId();
        if(!StringUtils.isEmpty(videoSource)){
            R r = voClient.deleteVideo(videoSource);
            if(r.getCode()==20001){
                throw new GuLiException(20001,"删除失败 熔断器");
            }
        }
        eduVideoService.removeById(videoId);
        return R.ok();
    }
}

