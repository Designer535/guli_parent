package com.atguigu.eduservice.controller;


import com.atguigu.commonutils.R;
import com.atguigu.eduservice.entity.EduTeacher;
import com.atguigu.eduservice.entity.vo.TeacherQuery;
import com.atguigu.eduservice.service.EduTeacherService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 讲师 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2021-03-02
 */
@ApiModel(description = "讲师管理")
@CrossOrigin(allowCredentials = "true", allowedHeaders = "*")
@RestController
@RequestMapping("/eduservice/edu-teacher")
public class EduTeacherController{

    @Autowired
    private EduTeacherService eduTeacherService;

    @ApiOperation(value = "所有讲师列表")
    @GetMapping("findAll")
    public R findAllTeacher() {
//        try {
//            int t=1/0;
//        }catch (Exception e){
//            throw new GuLiException(400,"运行异常");
//        }
        List<EduTeacher> list = eduTeacherService.list(null);
        return R.ok().data("item", list);
    }

    @ApiOperation(value = "根据ID删除讲师")
    @DeleteMapping("delete/{id}")
    public R removeById(@ApiParam(name = "id", value = "讲师ID", required = true) @PathVariable("id") String id) {
        boolean b = eduTeacherService.removeById(id);
        if (b) {
            return R.ok();
        } else {
            return R.fail();
        }
    }

    @ApiOperation(value = "分页查询")
    @GetMapping("pageTeacher/{page}/{limit}")
    public R pageList(
            @ApiParam(name = "page", value = "当前页", required = true)
            @PathVariable("page") Long page,
            @ApiParam(name = "limit", value = "分页查询数", required = true)
            @PathVariable("limit") Long limit) {
        Page<EduTeacher> pageParam = new Page<>(page, limit);
        eduTeacherService.page(pageParam, null);
        List<EduTeacher> records = pageParam.getRecords();
        long total = pageParam.getTotal();
        return R.ok().data("total", total).data("rows", records);
    }

    @ApiOperation(value = "条件分页查询")
    @PostMapping("pageTeacherCondition/{page}/{limit}")
    public R pageQuery(
            @ApiParam(name = "page", value = "当前页数", required = true)
            @PathVariable("page") Long page,
            @ApiParam(name = "limit", value = "每页记录数", required = true)
            @PathVariable("limit") Long limit,
            @ApiParam(name = "teacherQuery", value = "查询条件", required = false)
            @RequestBody(required = false) TeacherQuery teacherQuery) {
        Page<EduTeacher> pageParam = new Page<>(page, limit);
        eduTeacherService.pageQuery(pageParam, teacherQuery);
        List<EduTeacher> records = pageParam.getRecords();
        long total = pageParam.getTotal();
        return R.ok().data("total", total).data("rows", records);
    }

    @ApiOperation(value = "添加教师")
    @PostMapping("addTeacher")
    public R save(
            @ApiParam(name = "eduTeacher",value = "讲师对象",required = true)
            @RequestBody EduTeacher eduTeacher ){
        boolean save = eduTeacherService.save(eduTeacher);
        if(save){
            return R.ok();
        }else{
            return R.fail();
        }
    }

    @ApiOperation(value = "跟据id查找")
    @GetMapping("getTeacher/{id}")
    public R findById(
            @ApiParam(name="id",value = "id",required = true)
            @PathVariable("id") String id){
        EduTeacher byId = eduTeacherService.getById(id);
        return R.ok().data("teacher",byId);
    }

    @ApiOperation(value = "更新讲师信息")
    @PutMapping("updateTeacher")
    public R updateTeacher(
            @ApiParam(name = "eduTeacher",value = "讲师对象",required = true)
            @RequestBody EduTeacher eduTeacher){
        boolean update = eduTeacherService.updateById(eduTeacher);
        if(update){
            return R.ok();
        }else{
            return R.fail();
        }
    }
}

