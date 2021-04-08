package com.atguigu.edustatistics.client;

import com.atguigu.commonutils.R;
import com.atguigu.commonutils.UcenterMemberOrder;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(value = "service-ucenter" ,fallback = UcenterClientImpl.class)
@Component
public interface UcenterClient {

    @GetMapping("/educenter/ucenter-member/getRegisterCount/{date}")
    public R getRegisterCount(@PathVariable("date") String date);
}
