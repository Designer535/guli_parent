package com.atguigu.eduorder.controller;


import com.atguigu.commonutils.R;
import com.atguigu.eduorder.service.TPayLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * <p>
 * 支付日志表 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2021-04-04
 */
@CrossOrigin
@RestController
@RequestMapping("/eduorder/t-pay-log")
public class TPayLogController {

    @Autowired
    private TPayLogService tPayLogService;

    @GetMapping("createCode/{orderId}")
    public R createCode(@PathVariable("orderId")String orderId){
        Map<String,Object> map=tPayLogService.createCode(orderId);
        System.out.println(map.toString());
        return R.ok().data("map",map);
    }

    @GetMapping("queryOrderStatus/{orderId}")
    public R queryOrderStatus(@PathVariable("orderId")String orderId){
        Map<String,String>map=tPayLogService.queryOrderStatus(orderId);
        if(map==null){
            return R.fail().message("支付出错");
        }
        if(map.get("trade_state").equals("SUCCESS")){
            tPayLogService.updateOrderStatus(map);
            return R.ok().message("支付成功");
        }
        return R.ok().code(25000).message("支付中");
    }
}

