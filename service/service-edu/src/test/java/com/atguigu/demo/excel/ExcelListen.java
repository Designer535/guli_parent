package com.atguigu.demo.excel;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;

import java.util.Map;

public class ExcelListen extends AnalysisEventListener<DemoData> {

    @Override
    public void invoke(DemoData demoData, AnalysisContext analysisContext) {
        System.out.println("**************"+demoData);
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {

    }

    @Override
    public void invokeHeadMap(Map<Integer, String> headMap, AnalysisContext context) {
        System.out.println("*********表头"+headMap);
    }

}
