package com.example.demo;

import com.example.demo.utils.MailUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class MailTest {
    @Autowired
    private MailUtil mailUtil;

    @Test
    public void testSendTextMail() {
        mailUtil.sendTextMail("测试邮件", "这是一封测试邮件", List.of("2490900669@qq.com"));
    }

}
