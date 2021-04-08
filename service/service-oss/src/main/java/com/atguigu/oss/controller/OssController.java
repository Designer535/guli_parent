package com.atguigu.oss.controller;

import com.atguigu.commonutils.R;
import com.atguigu.oss.service.OssService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


@Api(value = "阿里云文件管理")
@RestController
@CrossOrigin
@RequestMapping("eduoss/fileoss")
public class OssController {

    @Autowired
    public OssService ossService;

    @ApiOperation(value = "文件上传")
    @PostMapping("/upload")
    public R getFile(
            @ApiParam(name = "file",value = "文件",required = true)
            @RequestBody MultipartFile file){
        String url=ossService.upload(file);
        return R.ok().message("文件上传成功").data("url",url);
    }
}
