package com.cn.lucky.morning.model.service;

import com.cn.lucky.morning.model.common.mvc.MvcResult;

import java.util.List;
import java.util.concurrent.Future;

public interface MailService {
    void sendSimpleMail(String to, String subject, String content);

    void sendHtmlMail(String to, String subject, String content);

    Future<MvcResult> sendAllHtmlMail(List<String> tos, String subject, String content);
}
