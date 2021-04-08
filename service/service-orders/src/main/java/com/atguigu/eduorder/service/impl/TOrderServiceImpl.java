package com.atguigu.eduorder.service.impl;

import com.atguigu.commonutils.CourseOrder;
import com.atguigu.commonutils.UcenterMemberOrder;
import com.atguigu.eduorder.client.CourseClient;
import com.atguigu.eduorder.client.UcenterClient;
import com.atguigu.eduorder.entity.TOrder;
import com.atguigu.eduorder.mapper.TOrderMapper;
import com.atguigu.eduorder.service.TOrderService;
import com.atguigu.eduorder.utils.OrderNoUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 订单 服务实现类
 * </p>
 *
 * @author testjava
 * @since 2021-04-04
 */
@Service
public class TOrderServiceImpl extends ServiceImpl<TOrderMapper, TOrder> implements TOrderService {

    @Autowired
    private CourseClient courseClient;

    @Autowired
    private UcenterClient ucenterClient;

    @Override
    public String createOrders(String courseId, String memberIdByJwtToken) {

        UcenterMemberOrder memberOrder = ucenterClient.getMemberOrder(memberIdByJwtToken);
        CourseOrder courseInfoById = courseClient.getCourseInfoById(courseId);
        TOrder order=new TOrder();
        order.setOrderNo(OrderNoUtil.getOrderNo());
        order.setCourseId(courseId);
        order.setCourseCover(courseInfoById.getCover());
        order.setCourseTitle(courseInfoById.getTitle());
        order.setTeacherName(courseInfoById.getTeacherName());
        order.setMemberId(memberIdByJwtToken);
        order.setNickname(memberOrder.getNickname());
        order.setMobile(memberOrder.getMobile());
        order.setTotalFee(courseInfoById.getPrice());
        order.setPayType(1);
        order.setStatus(0);

        baseMapper.insert(order);
        return order.getOrderNo();
    }
}
