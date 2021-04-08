package com.atguigu.educenter.service.impl;

import com.atguigu.commonutils.JwtUtils;
import com.atguigu.commonutils.MD5;
import com.atguigu.educenter.entity.UcenterMember;
import com.atguigu.educenter.entity.vo.LoginVo;
import com.atguigu.educenter.entity.vo.RegisterVo;
import com.atguigu.educenter.mapper.UcenterMemberMapper;
import com.atguigu.educenter.service.UcenterMemberService;
import com.atguigu.servicebase.exceptionHandler.GuLiException;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 会员表 服务实现类
 * </p>
 *
 * @author testjava
 * @since 2021-03-26
 */
@Service
public class UcenterMemberServiceImpl extends ServiceImpl<UcenterMemberMapper, UcenterMember> implements UcenterMemberService {

    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public String login(LoginVo loginVo) {
        String mail=loginVo.getMobile();
        String password=loginVo.getPassword();
        if(StringUtils.isEmpty(mail)|| StringUtils.isEmpty(password)){
            throw new GuLiException(20001,"邮箱或密码不能为空");
        }

        QueryWrapper<UcenterMember> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("mobile",mail);
        UcenterMember ucenterMember = baseMapper.selectOne(queryWrapper);

        if(ucenterMember==null){
            throw new GuLiException(20001,"登录失败");
        }

        if(!ucenterMember.getPassword().equals(MD5.encrypt(password))){
            throw new GuLiException(20001,"账号或密码错误");
        }

        if(ucenterMember.getIsDeleted()){
            throw new GuLiException(20001,"账号不存在");
        }

        String token = JwtUtils.getJwtToken(ucenterMember.getId(), ucenterMember.getNickname());

        return token;
    }

    @Override
    public boolean register(RegisterVo registerVo) {
        String nickName=registerVo.getNickname();
        String mail=registerVo.getMail();
        String password=registerVo.getPassword();
        String code=registerVo.getCode();

        if(StringUtils.isEmpty(nickName)||StringUtils.isEmpty(mail)|| StringUtils.isEmpty(code)||StringUtils.isEmpty(password)){
            return false;
        }

        QueryWrapper<UcenterMember> queryWrapper=new QueryWrapper();
        queryWrapper.eq("mobile",mail);
        Integer integer = baseMapper.selectCount(queryWrapper);

        if(integer>0){
            return false;
        }

        if(!code.equals(redisTemplate.opsForValue().get(mail))){
            return false;
        }

        UcenterMember ucenterMember=new UcenterMember();
        ucenterMember.setMobile(mail);
        ucenterMember.setPassword(MD5.encrypt(password));
        ucenterMember.setIsDeleted(false);
        ucenterMember.setNickname(nickName);
        ucenterMember.setAvatar("https://studyguli.oss-cn-shanghai.aliyuncs.com/2021/03/09/1d433628df3040d3b8d7bfe457e1e6eafile.jpg");

        baseMapper.insert(ucenterMember);

        return true;
    }

    @Override
    public UcenterMember getByOpenId(String openId) {
        QueryWrapper<UcenterMember> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("openid",openId);
        return baseMapper.selectOne(queryWrapper);
    }

    @Override
    public int getRegisterCount(String date) {
        int count=baseMapper.getRegisterCount(date);
        return count;
    }
}
