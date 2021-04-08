package com.atguigu.eduorder.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.atguigu.eduorder.entity.TOrder;
import com.atguigu.eduorder.entity.TPayLog;
import com.atguigu.eduorder.mapper.TPayLogMapper;
import com.atguigu.eduorder.service.TOrderService;
import com.atguigu.eduorder.service.TPayLogService;
import com.atguigu.eduorder.utils.HttpClient;
import com.atguigu.servicebase.exceptionHandler.GuLiException;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.wxpay.sdk.WXPayUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * 支付日志表 服务实现类
 * </p>
 *
 * @author testjava
 * @since 2021-04-04
 */
@Service
public class TPayLogServiceImpl extends ServiceImpl<TPayLogMapper, TPayLog> implements TPayLogService {

    @Autowired
    private TOrderService tOrderService;

    @Override
    public Map<String, Object> createCode(String orderId) {
        try {
            QueryWrapper<TOrder> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("order_no", orderId);
            TOrder order = tOrderService.getOne(queryWrapper);
            Map<String, String> map = new HashMap<>();

            //1、设置支付参数
            map.put("appid", "wx74862e0dfcf69954");//关联的公众号
            map.put("mch_id", "1558950191");//商户id
            map.put("nonce_str", WXPayUtil.generateNonceStr());
            map.put("body", order.getCourseTitle());//二维码的名字
            map.put("out_trade_no", orderId);//订单编号
            map.put("total_fee", order.getTotalFee().multiply(new
                    BigDecimal("100")).longValue() + "");//订单费用
            map.put("spbill_create_ip", "127.0.0.1");//支付的ip地址
            map.put("notify_url",
                    "http://guli.shop/api/order/weixinPay/weixinNotify\n");//回调地址
            map.put("trade_type", "NATIVE");//支付类型

            //2、HTTPClient来根据URL访问第三方接口并且传递参数
            HttpClient httpClient = new HttpClient("https://api.mch.weixin.qq.com/pay/unifiedorder");
            httpClient.setXmlParam(WXPayUtil.generateSignedXml(map, "T6m9iK73b0kn9g5v426MKfHQH7X8rKwb"));
            httpClient.setHttps(true);
            httpClient.post();

            //3、返回第三方的数据
            String content = httpClient.getContent();
            Map<String, String> resultMap = WXPayUtil.xmlToMap(content);

            //4、封装返回结果集
            Map<String, Object> orderMap = new HashMap<>();
            orderMap.put("out_trade_no", orderId);
            orderMap.put("course_id", order.getCourseId());
            orderMap.put("total_fee", order.getTotalFee());
            orderMap.put("result_code", resultMap.get("result_code"));
            orderMap.put("url", resultMap.get("code_url"));
            return orderMap;
        } catch (Exception e) {
            throw new GuLiException(20001, "失败");
        }
    }

    @Override
    public void updateOrderStatus(Map<String, String> map) {
        String orderId=map.get("out_trade_no");
        QueryWrapper<TOrder> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("order_no",orderId);
        TOrder order = tOrderService.getOne(queryWrapper);

        if(order.getStatus()==1){
            return;
        }
        order.setStatus(1);
        tOrderService.updateById(order);

        TPayLog PayLog=new TPayLog();
        //记录支付日志
        TPayLog payLog=new TPayLog();
        payLog.setOrderNo(order.getOrderNo());//支付订单号
        payLog.setPayTime(new Date());
        payLog.setPayType(1);//支付类型
        payLog.setTotalFee(order.getTotalFee());//总金额(分)
        payLog.setTradeState(map.get("trade_state"));//支付状态
        payLog.setTransactionId(map.get("transaction_id"));
        payLog.setAttr(JSONObject.toJSONString(map));
        baseMapper.insert(payLog);//插入到支付日志表
    }

    @Override
    public Map<String, String> queryOrderStatus(String orderId) {
        try {
            //1、封装参数
            Map m = new HashMap<>();
            m.put("appid", "wx74862e0dfcf69954");
            m.put("mch_id", "1558950191");
            m.put("out_trade_no", orderId);
            m.put("nonce_str", WXPayUtil.generateNonceStr());
            //2、设置请求
            HttpClient client = new
                    HttpClient("https://api.mch.weixin.qq.com/pay/orderquery");
            client.setXmlParam(WXPayUtil.generateSignedXml(m,
                    "T6m9iK73b0kn9g5v426MKfHQH7X8rKwb"));
            client.setHttps(true);
            client.post();
            //3、返回第三方的数据
            String xml = client.getContent();
            Map<String, String> resultMap = WXPayUtil.xmlToMap(xml);
            //6、转成Map
            //7、返回
            return resultMap;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
