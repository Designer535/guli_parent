package com.atguigu.eduservice.client;

import com.atguigu.commonutils.R;
import org.apache.ibatis.annotations.Delete;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(value = "service-vod",fallback =VoClientImpl.class)
@Component
public interface VoClient {

    @DeleteMapping("eduvod/video/deleteVideo/{videoId}")
    public R deleteVideo(@PathVariable("videoId")String videoId);

    @DeleteMapping("eduvod/video/deleteBatch")
    public R deleteBatch(@RequestParam("videoIdList") List<String> videIdList);
}
