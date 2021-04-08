package com.atguigu.eduservice.controller;


import com.atguigu.commonutils.R;
import com.atguigu.eduservice.entity.vo.OneSubject;
import com.atguigu.eduservice.service.EduSubjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * <p>
 * 课程科目 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2021-03-10
 */
@RestController
@RequestMapping("/eduservice/edu-subject")
@CrossOrigin
public class EduSubjectController {

    @Autowired
    private EduSubjectService eduSubjectService;

    @PostMapping("/addSubject")
    public R addSubject(@RequestBody MultipartFile file){
        eduSubjectService.addSubject(file,eduSubjectService);
        return R.ok();
    }

    @GetMapping("/getAllSubject")
    public R getAllSubject(){
        List<OneSubject> list=eduSubjectService.getAllSubject();
        return R.ok().data("list",list);
    }
}

