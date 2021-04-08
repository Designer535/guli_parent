package com.atguigu.eduservice.listen;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.atguigu.eduservice.entity.EduSubject;
import com.atguigu.eduservice.entity.excel.SubjectData;
import com.atguigu.eduservice.service.EduSubjectService;
import com.atguigu.servicebase.exceptionHandler.GlobalExceptionHandler;
import com.atguigu.servicebase.exceptionHandler.GuLiException;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

public class ExcelListen extends AnalysisEventListener<SubjectData> {

    private EduSubjectService eduSubjectService;

    public ExcelListen() {
    }

    public ExcelListen(EduSubjectService eduSubjectService) {
        this.eduSubjectService = eduSubjectService;
    }

    @Override
    public void invoke(SubjectData subjectData, AnalysisContext analysisContext) {
        if (subjectData == null) {
            throw new GuLiException(20001, "文件数据为空");
        }
        EduSubject eduSubject = existOneSubject(eduSubjectService, subjectData.getOneSubjectName());
        if (eduSubject == null) {
            eduSubject=new EduSubject();
            eduSubject.setTitle(subjectData.getOneSubjectName());
            eduSubject.setParentId("0");
            eduSubject.setSort(0);
            eduSubjectService.save(eduSubject);
        }
        String id = eduSubject.getId();
        EduSubject eduSubject1 = existTwoSubject(eduSubjectService, subjectData.getTwoSubjectName(), id);
        if (eduSubject1 == null) {
            eduSubject1=new EduSubject();
            eduSubject1.setTitle(subjectData.getTwoSubjectName());
            eduSubject1.setParentId(id);
            eduSubject1.setSort(0);
            eduSubjectService.save(eduSubject1);
        }
    }

    private EduSubject existOneSubject(EduSubjectService eduSubjectService, String name) {
        QueryWrapper<EduSubject> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("title", name);
        queryWrapper.eq("parent_id", "0");
        EduSubject one = eduSubjectService.getOne(queryWrapper);
        return one;
    }

    private EduSubject existTwoSubject(EduSubjectService eduSubjectService, String name, String id) {
        QueryWrapper<EduSubject> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("title", name);
        queryWrapper.eq("parent_id", id);
        EduSubject two = eduSubjectService.getOne(queryWrapper);
        return two;
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {

    }
}
