package com.atguigu.vod.service.impl;

import com.aliyun.vod.upload.impl.UploadVideoImpl;
import com.aliyun.vod.upload.req.UploadStreamRequest;
import com.aliyun.vod.upload.resp.UploadStreamResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.vod.model.v20170321.DeleteVideoRequest;
import com.aliyuncs.vod.model.v20170321.DeleteVideoResponse;
import com.atguigu.servicebase.exceptionHandler.GuLiException;
import com.atguigu.vod.service.VodService;
import com.atguigu.vod.utils.ConstantPropertiesUtil;
import com.atguigu.vod.utils.InitVoClient;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@Service
public class VodServiceImpl implements VodService {
    @Override
    public String uploadVideo(MultipartFile multipartFile) {
        InputStream inputStream = null;
        String fileName=null,title=null;
        try {
            fileName=multipartFile.getOriginalFilename();
            title=fileName.substring(0,fileName.lastIndexOf("."));
            inputStream = multipartFile.getInputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }
        UploadStreamRequest request = new UploadStreamRequest(ConstantPropertiesUtil.KEY_ID, ConstantPropertiesUtil.KEY_SECRET, title, fileName, inputStream);

        UploadVideoImpl uploader = new UploadVideoImpl();
        UploadStreamResponse response = uploader.uploadStream(request);
        String videoId=response.getVideoId();
        if(StringUtils.isEmpty(videoId)){
            throw new GuLiException(20001,"阿里云上传错误：" + "code：" +
                    response.getCode() + ", message：" + response.getMessage());
        }
        return videoId;
    }

    @Override
    public void deleteBatch(List<String> videoIdList) {
        try {
            String str = org.apache.commons.lang.StringUtils.join(videoIdList.toArray(), ",");
            DefaultAcsClient client = InitVoClient.getClient(ConstantPropertiesUtil.KEY_ID, ConstantPropertiesUtil.KEY_SECRET);

            DeleteVideoRequest deleteVideoRequest = new DeleteVideoRequest();

            deleteVideoRequest.setVideoIds(str);

            DeleteVideoResponse acsResponse = client.getAcsResponse(deleteVideoRequest);

        }catch (Exception e){
            e.printStackTrace();
            throw new GuLiException(20001,"删除失败");
        }

    }
}
