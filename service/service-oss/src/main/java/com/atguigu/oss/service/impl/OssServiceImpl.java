package com.atguigu.oss.service.impl;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.atguigu.oss.service.OssService;
import com.atguigu.oss.utils.ConstantPropertiesUtil;
import org.joda.time.DateTime;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

@Service
public class OssServiceImpl implements OssService {

    @Override
    public String upload(MultipartFile file) {
        // Endpoint以杭州为例，其它Region请按实际情况填写。
        String endpoint = ConstantPropertiesUtil.ENDPOINT;
// 阿里云主账号AccessKey拥有所有API的访问权限，风险很高。强烈建议您创建并使用RAM账号进行API访问或日常运维，请登录RAM控制台创建RAM账号。
        String accessKeyId = ConstantPropertiesUtil.KEYID;
        String accessKeySecret = ConstantPropertiesUtil.KEYSECRET;
        String bucketName = ConstantPropertiesUtil.BUCKETNAME;
// <yourObjectName>上传文件到OSS时需要指定包含文件后缀在内的完整路径，例如abc/efg/123.jpg。

// 创建OSSClient实例。
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);

        String originalFilename = file.getOriginalFilename();

        String uuid = UUID.randomUUID().toString().replaceAll("-","");

        originalFilename=uuid+originalFilename;

        String path = new DateTime().toString("yyyy/MM/dd");

        path=path+"/"+originalFilename;
        // 上传文件到指定的存储空间（bucketName）并将其保存为指定的文件名称（objectName）。
        try {
            InputStream inputStream = file.getInputStream();
            ossClient.putObject(bucketName, path, inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }


// 关闭OSSClient。
        ossClient.shutdown();
        String url = "https://" + bucketName + "." + endpoint + "/" + path;
        return url;
    }
}
