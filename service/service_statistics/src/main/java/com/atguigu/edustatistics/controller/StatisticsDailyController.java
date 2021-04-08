package com.atguigu.edustatistics.controller;


import com.atguigu.commonutils.R;
import com.atguigu.edustatistics.service.StatisticsDailyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * <p>
 * 网站统计日数据 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2021-04-05
 */
@RestController
@CrossOrigin
@RequestMapping("/edustatistics/statistics-daily")
public class StatisticsDailyController {

    @Autowired
    private StatisticsDailyService statisticsDailyService;

    @GetMapping("getRegisterCount/{date}")
    public R getRegisterCount(@PathVariable("date")String date){
        statisticsDailyService.getRegisterCount(date);
        return R.ok();
    }

    @GetMapping("showData/{type}/{begin}/{end}")
    public R showDate(@PathVariable("type") String type,@PathVariable("begin")String begin,@PathVariable("end")String end){
        Map<String,Object> map=statisticsDailyService.getShowData(type,begin,end);
        return R.ok().data("map",map);
    }
}

