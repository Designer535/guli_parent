package com.atguigu.vod.controller;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.kms.model.v20160120.DeleteAliasRequest;
import com.aliyuncs.vod.model.v20170321.DeleteVideoRequest;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthRequest;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthResponse;
import com.atguigu.commonutils.R;
import com.atguigu.servicebase.exceptionHandler.GuLiException;
import com.atguigu.vod.service.VodService;
import com.atguigu.vod.utils.ConstantPropertiesUtil;
import com.atguigu.vod.utils.InitVoClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("eduvod/video")
@CrossOrigin
public class VodController {

    @Autowired
    private VodService vodService;

    @PostMapping("uploadVideo")
    public R uploadALiYunVideo(@RequestBody MultipartFile file) {
        String videoId = vodService.uploadVideo(file);
        return R.ok().data("videoId", videoId);
    }

    @DeleteMapping("deleteVideo/{videoId}")
    public R deleteVideo(@PathVariable("videoId") String videoId) {
        try {
            DefaultAcsClient defaultAcsClient = InitVoClient.getClient(ConstantPropertiesUtil.KEY_ID, ConstantPropertiesUtil.KEY_SECRET);
            DeleteVideoRequest deleteVideoRequest = new DeleteVideoRequest();
            deleteVideoRequest.setVideoIds(videoId);
            defaultAcsClient.getAcsResponse(deleteVideoRequest);
            return R.ok();
        } catch (ClientException e) {
            e.printStackTrace();
            throw new GuLiException(20001,"删除视频失败");
        }
    }

    @DeleteMapping("deleteBatch")
    public R deleteBatch(@RequestParam("videoIdList") List<String> videoIdList){
        vodService.deleteBatch(videoIdList);
        return R.ok().message("视频删除成功");
    }

    @GetMapping("getPlayAuth/{id}")
    public R getPlayAuth(@PathVariable("id") String id){
        try {
            DefaultAcsClient client = InitVoClient.getClient(ConstantPropertiesUtil.KEY_ID, ConstantPropertiesUtil.KEY_SECRET);
            GetVideoPlayAuthRequest request = new GetVideoPlayAuthRequest();
            GetVideoPlayAuthResponse response = new GetVideoPlayAuthResponse();
            request.setVideoId(id);
            response = client.getAcsResponse(request);
            return R.ok().data("playAuth",response.getPlayAuth());
        }catch (Exception e){
            throw new GuLiException(20001,"获取视频凭证失败");
        }
    }
}
