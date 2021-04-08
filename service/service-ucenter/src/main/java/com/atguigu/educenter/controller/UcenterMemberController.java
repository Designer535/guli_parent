package com.atguigu.educenter.controller;


import com.atguigu.commonutils.JwtUtils;
import com.atguigu.commonutils.R;
import com.atguigu.commonutils.UcenterMemberOrder;
import com.atguigu.educenter.entity.UcenterMember;
import com.atguigu.educenter.entity.vo.LoginVo;
import com.atguigu.educenter.entity.vo.RegisterVo;
import com.atguigu.educenter.service.UcenterMemberService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * 会员表 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2021-03-26
 */
@RestController
@RequestMapping("/educenter/ucenter-member")
@CrossOrigin
public class UcenterMemberController {

    @Autowired
    private UcenterMemberService ucenterMemberService;

    @PostMapping("login")
    public R login(@RequestBody LoginVo loginVo){
        String token=ucenterMemberService.login(loginVo);
        return R.ok().data("token",token);
    }

    @PostMapping("register")
    public R register(@RequestBody RegisterVo registerVo){
           boolean flag= ucenterMemberService.register(registerVo);
           if(flag) {
               return R.ok();
           }else{
               return R.fail().message("注册失败");
           }
    }

    @GetMapping("getMemberInfo")
    public R getMemberInfo(HttpServletRequest request){
        String memberIdByJwtToken = JwtUtils.getMemberIdByJwtToken(request);
        UcenterMember member = ucenterMemberService.getById(memberIdByJwtToken);
        return R.ok().data("member",member);
    }

    @GetMapping("getMemberById/{id}")
    public UcenterMemberOrder getMemberOrder(@PathVariable("id") String id){
        UcenterMember member = ucenterMemberService.getById(id);
        UcenterMemberOrder ucenterMemberOrder=new UcenterMemberOrder();
        BeanUtils.copyProperties(member,ucenterMemberOrder);
        return ucenterMemberOrder;
    }

    @GetMapping("getRegisterCount/{date}")
    public R getRegisterCount(@PathVariable("date") String date){
        int count=ucenterMemberService.getRegisterCount(date);
        return R.ok().data("registerCount",count);
    }
}

