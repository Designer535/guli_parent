package com.atguigu.oss.utils;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class ConstantPropertiesUtil implements InitializingBean {

    @Value("${aliyun.oss.file.keyid}")
    private String keyId;

    @Value("${aliyun.oss.file.keysecret}")
    private String keySecret;

    @Value("${aliyun.oss.file.endpoint}")
    private String endpoint;

    @Value("${aliyun.oss.file.bucketname}")
    private String bucketName;

    public static String KEYID;
    public static String KEYSECRET;
    public static String ENDPOINT;
    public static String BUCKETNAME;

    @Override
    public void afterPropertiesSet() throws Exception {
        KEYID=this.keyId;
        KEYSECRET=this.keySecret;
        ENDPOINT=this.endpoint;
        BUCKETNAME=this.bucketName;

    }
}
