package com.example.demo.utils;

import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.mail.MailProperties;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;


import java.util.List;

@Component
public class MailUtil {

    @Autowired
    private MailProperties mailProperties;

    @Autowired
    private JavaMailSender javaMailSender;

    /**
     *
     * @param subject 邮件主题
     * @param text 邮件内容
     * @param toList 邮件接收者列表
     *
     *
     *   发送纯文本邮件
     * */

    public void sendTextMail(String subject, String text, List<String> toList) {
        try {
            String[] to = toList.toArray(new String[0]);
            var message = javaMailSender.createMimeMessage();
            var helper = new MimeMessageHelper(message, true);
            helper.setFrom(mailProperties.getUsername());
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(text);
            javaMailSender.send(message);
        } catch (MessagingException e) {
            throw new RuntimeException("发送文本邮件失败", e);
        }
    }

    /**
     * 发送 HTML 邮件
     */
    public void sendHtmlMail(String subject, String htmlContent, List<String> toList) {
        try {
            String[] to = toList.toArray(new String[0]);
            var message = javaMailSender.createMimeMessage();
            var helper = new MimeMessageHelper(message, true);
            helper.setFrom(mailProperties.getUsername());
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(htmlContent, true); // 第二个参数为 true 表示是 HTML 内容
            javaMailSender.send(message);
        } catch (MessagingException e) {
            throw new RuntimeException("发送 HTML 邮件失败", e);
        }
    }

    /**
     * 发送带附件的邮件
     */
    public void sendMailWithAttachment(String subject, String text, List<String> toList, String attachmentPath, String attachmentName) {
        try {
            String[] to = toList.toArray(new String[0]);
            var message = javaMailSender.createMimeMessage();
            var helper = new MimeMessageHelper(message, true);
            helper.setFrom(mailProperties.getUsername());
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(text);
            helper.addAttachment(attachmentName, new java.io.File(attachmentPath));
            javaMailSender.send(message);
        } catch (MessagingException | IllegalArgumentException e) {
            throw new RuntimeException("发送带附件邮件失败", e);
        }
    }
}
