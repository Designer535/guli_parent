package com.atguigu.edumsm.controller;

import com.atguigu.commonutils.R;
import com.atguigu.edumsm.service.MsmService;
import com.atguigu.edumsm.utils.RandomUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@RestController
@CrossOrigin
@RequestMapping("/edumsm/msm")
public class MsmController {

    @Autowired
    private MsmService msmService;

    @Autowired
    private RedisTemplate redisTemplate;

    @GetMapping("send/{mail}")
    public R sendMsm(@PathVariable("mail") String mail){
        String code= RandomUtil.getSixBitRandom();
        Map<String,Object> param=new HashMap<>();
        param.put("code",code);
        boolean flag=msmService.send(code,mail);
        if(flag){
            redisTemplate.opsForValue().set(mail,code,3, TimeUnit.MINUTES);
            System.out.println(redisTemplate.opsForValue().get(mail));
            return R.ok();
        }else{
            return R.fail().message("验证码发送失败");
        }
    }
}
