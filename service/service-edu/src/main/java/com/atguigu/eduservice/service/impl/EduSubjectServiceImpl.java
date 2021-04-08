package com.atguigu.eduservice.service.impl;

import com.alibaba.excel.EasyExcel;
import com.atguigu.eduservice.entity.EduSubject;
import com.atguigu.eduservice.entity.excel.SubjectData;
import com.atguigu.eduservice.entity.vo.OneSubject;
import com.atguigu.eduservice.entity.vo.TwoSubject;
import com.atguigu.eduservice.listen.ExcelListen;
import com.atguigu.eduservice.mapper.EduSubjectMapper;
import com.atguigu.eduservice.service.EduSubjectService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程科目 服务实现类
 * </p>
 *
 * @author testjava
 * @since 2021-03-10
 */
@Service
public class EduSubjectServiceImpl extends ServiceImpl<EduSubjectMapper, EduSubject> implements EduSubjectService {

    @Override
    public void addSubject(MultipartFile file, EduSubjectService eduSubjectService) {
        try {
            InputStream inputStream = file.getInputStream();
            EasyExcel.read(inputStream, SubjectData.class,new ExcelListen(eduSubjectService)).sheet().doRead();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public List<OneSubject> getAllSubject() {
        List<OneSubject> list=new ArrayList<>();
        List<EduSubject> eduSubjects = baseMapper.selectList(null);
        OneSubject oneSubject;
        for(EduSubject eduSubject:eduSubjects){
            if(eduSubject.getParentId().equals("0")){
                oneSubject=new OneSubject();
                oneSubject.setId(eduSubject.getId());
                oneSubject.setTitle(eduSubject.getTitle());
                list.add(oneSubject);
            }
        }

        for(EduSubject eduSubject:eduSubjects){
            for(OneSubject temp:list){
                if(eduSubject.getParentId().equals(temp.getId())){
                    temp.getList().add(new TwoSubject(eduSubject.getId(),eduSubject.getTitle()));
                }
            }
        }
        return list;
    }
}
