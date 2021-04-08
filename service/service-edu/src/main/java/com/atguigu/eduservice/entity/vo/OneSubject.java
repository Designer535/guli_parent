package com.atguigu.eduservice.entity.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.poi.util.NotImplemented;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OneSubject {

    private String id;
    private String title;

    private List<TwoSubject> list=new ArrayList<>();
}
