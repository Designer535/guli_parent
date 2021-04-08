package com.atguigu.demo.excel;

import com.alibaba.excel.EasyExcel;

import java.util.ArrayList;
import java.util.List;

public class TestExcel {

    public static void main(String[] args) {
//        String str="d://text.xlsx";
//        EasyExcel.write(str,DemoData.class).sheet("学生列表").doWrite(getData());
        String str = "d://text.xlsx";
        EasyExcel.read(str,DemoData.class,new ExcelListen()).sheet().doRead();
    }

    private static List getData() {
        List<DemoData> list=new ArrayList<>();
        for(int i=0;i<3;i++){
            list.add(new DemoData(i,"张三"+i));
        }
        return list;
    }
}
