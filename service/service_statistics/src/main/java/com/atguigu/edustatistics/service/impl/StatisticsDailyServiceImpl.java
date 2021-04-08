package com.atguigu.edustatistics.service.impl;

import com.atguigu.commonutils.R;
import com.atguigu.edustatistics.client.UcenterClient;
import com.atguigu.edustatistics.entity.StatisticsDaily;
import com.atguigu.edustatistics.mapper.StatisticsDailyMapper;
import com.atguigu.edustatistics.service.StatisticsDailyService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang3.RandomUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 网站统计日数据 服务实现类
 * </p>
 *
 * @author testjava
 * @since 2021-04-05
 */
@Service
public class StatisticsDailyServiceImpl extends ServiceImpl<StatisticsDailyMapper, StatisticsDaily> implements StatisticsDailyService {


    @Autowired
    private UcenterClient ucenterClient;

    @Override
    public void getRegisterCount(String date) {
        QueryWrapper<StatisticsDaily> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("date_calculated",date);
        baseMapper.delete(queryWrapper);

        R registerCount = ucenterClient.getRegisterCount(date);
        Integer count = (Integer) registerCount.getData().get("registerCount");

        StatisticsDaily statisticsDaily=new StatisticsDaily();
        statisticsDaily.setRegisterNum(count);
        statisticsDaily.setLoginNum(RandomUtils.nextInt(100,200));
        statisticsDaily.setVideoViewNum(RandomUtils.nextInt(100,200));
        statisticsDaily.setCourseNum(RandomUtils.nextInt(100,200));
        statisticsDaily.setDateCalculated(date);

        baseMapper.insert(statisticsDaily);
    }

    @Override
    public Map<String, Object> getShowData(String type, String begin, String end) {
        QueryWrapper<StatisticsDaily> queryWrapper=new QueryWrapper<>();
        queryWrapper.between("date_calculated",begin,end);
        queryWrapper.select("date_calculated",type);
        List<StatisticsDaily> statisticsDailies = baseMapper.selectList(queryWrapper);

        List<String> date=new ArrayList<>();
        List<Integer> num=new ArrayList<>();

        for(StatisticsDaily statisticsDaily:statisticsDailies){
            date.add(statisticsDaily.getDateCalculated());
            switch (type){
                case "register_num":
                    num.add(statisticsDaily.getRegisterNum());
                    break;
                case "login_num":
                    num.add(statisticsDaily.getLoginNum());
                    break;
                case "video_view_num":
                    num.add(statisticsDaily.getVideoViewNum());
                    break;
                case "course_num":
                    num.add(statisticsDaily.getCourseNum());
                    break;
            }
        }
        Map<String,Object> map=new HashMap<>();
        map.put("date",date);
        map.put("num",num);
        return map;
    }
}
