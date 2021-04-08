package com.aliyuncs.DefalutAcsClient;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.profile.DefaultProfile;

public class InitObject {

    public static DefaultAcsClient initVodClient(String accessKid, String accessKeySecret){
        String regionId="cn-shanghai";
        DefaultProfile profile = DefaultProfile.getProfile(regionId, accessKid, accessKeySecret);
        DefaultAcsClient defaultAcsClient = new DefaultAcsClient(profile);
        return defaultAcsClient;
    }
}
