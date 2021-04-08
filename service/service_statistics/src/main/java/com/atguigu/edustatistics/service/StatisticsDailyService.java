package com.atguigu.edustatistics.service;

import com.atguigu.edustatistics.entity.StatisticsDaily;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

/**
 * <p>
 * 网站统计日数据 服务类
 * </p>
 *
 * @author testjava
 * @since 2021-04-05
 */
public interface StatisticsDailyService extends IService<StatisticsDaily> {

    void getRegisterCount(String date);

    Map<String, Object> getShowData(String type, String begin, String end);
}
