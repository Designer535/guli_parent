package com.atguigu.eduservice.entity.vo;

import lombok.Data;

@Data
public class CoursePublishVo {
    private String id;
    private String title;
    private String teacherName;
    private String subjectLevelOne;
    private String subjectLevelTwo;
    private String cover;
    private Integer lessonNum;
    private String price;
}
