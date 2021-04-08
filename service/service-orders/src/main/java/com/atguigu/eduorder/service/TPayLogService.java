package com.atguigu.eduorder.service;

import com.atguigu.eduorder.entity.TPayLog;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

/**
 * <p>
 * 支付日志表 服务类
 * </p>
 *
 * @author testjava
 * @since 2021-04-04
 */
public interface TPayLogService extends IService<TPayLog> {

    Map<String, Object> createCode(String orderId);

    void updateOrderStatus(Map<String, String> map);

    Map<String, String> queryOrderStatus(String orderId);
}
