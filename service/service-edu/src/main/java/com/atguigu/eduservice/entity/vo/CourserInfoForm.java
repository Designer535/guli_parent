package com.atguigu.eduservice.entity.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@ApiModel(value = "课程基本信息",description = "编辑课程基本信息的表单对象")
@Data
public class CourserInfoForm implements Serializable {

    private static final long serialVersionUID=1L;

    @ApiModelProperty(value = "课程编号")
    private String id;

    @ApiModelProperty(value = "教师编号")
    private String teacherId;

    @ApiModelProperty(value = "课程编号")
    private String subjectId;

    @ApiModelProperty(value = "课程类别")
    private String subjectParentId;

    @ApiModelProperty(value = "课程价格")
    private BigDecimal price;

    @ApiModelProperty(value = "课程标题")
    private String title;

    @ApiModelProperty(value = "总课时")
    private Integer lessonNum;

    @ApiModelProperty(value = "课程封面")
    private String cover;

    @ApiModelProperty(value = "课程描述")
    private String description;
}
