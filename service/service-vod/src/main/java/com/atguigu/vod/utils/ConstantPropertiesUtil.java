package com.atguigu.vod.utils;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class ConstantPropertiesUtil implements InitializingBean {

    @Value("${aliyun.vod.file.keyid}")
    private String key;

    @Value("${aliyun.vod.file.keysecret}")
    private String secret;

    public static String KEY_ID;

    public static String KEY_SECRET;

    @Override
    public void afterPropertiesSet() throws Exception {
        KEY_ID=this.key;
        KEY_SECRET=this.secret;
    }
}
