package com.cn.lucky.morning.model.service;

import java.util.List;

public interface MailService {
    void sendSimpleMail(String to, String subject, String content);

    void sendHtmlMail(String to, String subject, String content);

    List<String> sendAllHtmlMail(List<String> tos, String subject, String content);
}
