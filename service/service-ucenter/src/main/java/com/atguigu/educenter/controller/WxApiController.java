package com.atguigu.educenter.controller;

import com.atguigu.commonutils.JwtUtils;
import com.atguigu.educenter.entity.UcenterMember;
import com.atguigu.educenter.service.UcenterMemberService;
import com.atguigu.educenter.utils.ConstantWxUtils;
import com.atguigu.educenter.utils.HttpClientUtils;
import com.atguigu.servicebase.exceptionHandler.GuLiException;
import com.google.gson.Gson;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

@CrossOrigin
@Controller
@RequestMapping("/api/ucenter/wx")
public class WxApiController {

    @Autowired
    private UcenterMemberService ucenterMemberService;

    @GetMapping("callback")
    public String callback(String code, String state) {
        try {
            //第一步授权临时票据
            String baseAccessTokenUrl =
                    "https://api.weixin.qq.com/sns/oauth2/access_token" +
                            "?appid=%s" +
                            "&secret=%s" +
                            "&code=%s" +
                            "&grant_type=authorization_code";

            String accessTokenUrl = String.format(baseAccessTokenUrl,
                    ConstantWxUtils.APPID,
                    ConstantWxUtils.APPSECRET,
                    code);
            String result = HttpClientUtils.get(accessTokenUrl);
            System.out.println(result);

            //第二步解析返回信息
            Gson gson = new Gson();
            Map<String, Object> map = gson.fromJson(result, HashMap.class);
            String token = (String) map.get("access_token");
            String openId = (String) map.get("openid");

            UcenterMember member = ucenterMemberService.getByOpenId(openId);

            if(member==null) {
                //第三步获取用户信息
                String baseUserInfoUrl = "https://api.weixin.qq.com/sns/userinfo" +
                        "?access_token=%s" +
                        "&openid=%s";

                String userInfoUrl = String.format(baseUserInfoUrl, token, openId);
                String userInfo = HttpClientUtils.get(userInfoUrl);

                //解析用户信息
                HashMap hashMap = gson.fromJson(userInfo, HashMap.class);
                String nickname = (String) hashMap.get("nickname");
                String headimage = (String) hashMap.get("headimgurl");
                System.out.println(userInfo);

                member=new UcenterMember();
                member.setNickname(nickname);
                member.setOpenid(openId);
                member.setAvatar(headimage);
                ucenterMemberService.save(member);
            }
            String toke=JwtUtils.getJwtToken(member.getId(),member.getNickname());
            return "redirect:http://localhost:3000?token="+toke;
        } catch (Exception e) {
            e.printStackTrace();
            throw new GuLiException(20001, "登录失败");
        }
    }

    @GetMapping("login")
    public String getCode() {
        String baseUrl = "https://open.weixin.qq.com/connect/qrconnect" +
                "?appid=%s" +
                "&redirect_uri=%s" +
                "&response_type=code" +
                "&scope=snsapi_login" +
                "&state=%s" +
                "#wechat_redirect";

        String redirectUrl = ConstantWxUtils.REDIRECTURL;

        try {
            redirectUrl = URLEncoder.encode(redirectUrl, "utf-8");
        } catch (Exception e) {

        }

        String url = String.format(
                baseUrl,
                ConstantWxUtils.APPID,
                redirectUrl,
                "atguigu");

        return "redirect:" + url;

    }
}
