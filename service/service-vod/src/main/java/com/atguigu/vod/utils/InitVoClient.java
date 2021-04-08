package com.atguigu.vod.utils;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.profile.DefaultProfile;

public class InitVoClient {

    public static DefaultAcsClient getClient(String key,String secret){
        String regionId="cn-shanghai";
        DefaultProfile defaultProfile=DefaultProfile.getProfile(regionId,key,secret);
        DefaultAcsClient defaultAcsClient=new DefaultAcsClient(defaultProfile);
        return defaultAcsClient;
    }
}
