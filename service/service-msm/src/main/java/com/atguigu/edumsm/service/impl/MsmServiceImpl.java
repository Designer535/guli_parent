package com.atguigu.edumsm.service.impl;

import com.atguigu.edumsm.service.MsmService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;

@Service
public class MsmServiceImpl implements MsmService {

    @Autowired
    private MailSender mailSender;

    @Override
    public boolean send(String code, String mail) {
        SimpleMailMessage simpleMailMessage=new SimpleMailMessage();
        simpleMailMessage.setFrom("13762056524@163.com");
        simpleMailMessage.setTo(mail);
        simpleMailMessage.setSubject("验证码");
        simpleMailMessage.setText("你的验证码是:"+code);
        mailSender.send(simpleMailMessage);
        return true;
    }

}
