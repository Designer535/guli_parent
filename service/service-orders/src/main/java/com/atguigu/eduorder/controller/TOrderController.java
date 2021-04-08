package com.atguigu.eduorder.controller;


import com.atguigu.commonutils.JwtUtils;
import com.atguigu.commonutils.R;
import com.atguigu.eduorder.entity.TOrder;
import com.atguigu.eduorder.service.TOrderService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * 订单 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2021-04-04
 */
@RestController
@CrossOrigin
@RequestMapping("/eduorder/t-order")
public class TOrderController {

    @Autowired
    private TOrderService tOrderService;

    @PostMapping("createOrder/{courseId}")
    public R saveOrder(@PathVariable("courseId")String courseId, HttpServletRequest request){
        String memberIdByJwtToken = JwtUtils.getMemberIdByJwtToken(request);
        if (StringUtils.isEmpty(memberIdByJwtToken)) {
            return R.fail().code(28004).message("请登录");
        }
        String orderId=tOrderService.createOrders(courseId,memberIdByJwtToken);
        return R.ok().data("orderId",orderId);
    }

    @GetMapping("getOrderInfo/{orderId}")
    public R getOrderInfo(@PathVariable("orderId") String orderId){
        QueryWrapper<TOrder> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("order_no",orderId);
        TOrder order = tOrderService.getOne(queryWrapper);
        return R.ok().data("order",order);
    }

    @GetMapping("isBuyCourse/{courseId}/{memberId}")
    public boolean isBuyCourse(@PathVariable("courseId")String courseId,@PathVariable("memberId")String memberId){
        QueryWrapper<TOrder> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("course_id",courseId);
        queryWrapper.eq("member_id",memberId);
        queryWrapper.eq("status",1);
        int count = tOrderService.count(queryWrapper);
        return count>0;
    }
}

