package com.atguigu.educenter.utils;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;


@Component
public class ConstantWxUtils implements InitializingBean {

    @Value("${wen.open.app_id}")
    private String appId;

    @Value("${wen.open.appsecret}")
    private String appsecret;

    @Value("${wen.open.redirecturl}")
    private String redirecturl;


    public static String APPID;
    public static String APPSECRET;
    public static String REDIRECTURL;

    @Override
    public void afterPropertiesSet() throws Exception {
        APPID = this.appId;
        APPSECRET = this.appsecret;
        REDIRECTURL = this.redirecturl;
    }
}
