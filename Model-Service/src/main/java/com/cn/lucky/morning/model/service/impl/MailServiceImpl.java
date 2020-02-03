package com.cn.lucky.morning.model.service.impl;

import com.alibaba.fastjson.JSON;
import com.cn.lucky.morning.model.common.log.Logs;
import com.cn.lucky.morning.model.common.network.Col;
import com.cn.lucky.morning.model.service.MailService;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.ArrayList;
import java.util.List;


@Service
public class MailServiceImpl implements MailService {
    private final static Logger logger = Logs.get();

    @Autowired
    private JavaMailSender mailSender;

    @Value("${mail.fromMail.addr}")
    private String from;

    @Override
    public void sendSimpleMail(String to, String subject, String content) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(from);
        message.setTo(to);
        message.setSubject(subject);
        message.setText(content);
        try {
            mailSender.send(message);
            logger.info("简单邮件已经发送。");
        } catch (Exception e) {
            logger.error("发送简单邮件时发生异常！", e);
        }
    }

    @Override
    public void sendHtmlMail(String to, String subject, String content) {
        MimeMessage message = mailSender.createMimeMessage();

        try {
            //true表示需要创建一个multipart message
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setFrom(from);
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(content, true);

            mailSender.send(message);
            logger.info("html邮件发送成功");
        } catch (MessagingException e) {
            logger.error("发送html邮件时发生异常！", e);
        }
    }

    @Override
    public List<String> sendAllHtmlMail(List<String> tos, String subject, String content) {
        MimeMessage message = mailSender.createMimeMessage();
        List<String> errorList = new ArrayList<>();
        for (String to : tos) {
            if (StringUtils.isEmpty(to)){
                continue;
            }
            try {
                //true表示需要创建一个multipart message
                MimeMessageHelper helper = new MimeMessageHelper(message, true);
                helper.setFrom(from);
                helper.setTo(to);
                helper.setSubject(subject);
                helper.setText(content, true);

                mailSender.send(message);
            } catch (MessagingException e) {
                logger.error("发送html邮件时发生异常！", e);
                errorList.add(to);
            }

        }
        if (errorList.size() > 0){
            logger.info("html邮件群发完毕，成功列表："+JSON.toJSONString(tos.removeAll(errorList))+",失败列表："+ JSON.toJSONString(errorList));
        }else {
            logger.info("html邮件群发成功："+ JSON.toJSONString(tos));
        }
        return errorList;
    }
}
