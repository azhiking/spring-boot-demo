package com.tomhurry.email;

import cn.hutool.core.date.DateUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import javax.mail.MessagingException;
import java.util.HashMap;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
class MailSenderImplTest {

    @Resource(name = "mailSenderImpl")
    private MailSender mailSender;
    @Resource
    private FreeMarkerTemplateUtil freeMarkerTemplateUtil;

    private static final String to = "taozhi@ecloudtech.com";
    private static final String subject = "test";
    private static final String[] cc = {};


    @Test
    void sendSimpleMail() {
        mailSender.sendSimpleMail(to, subject, "this is test email", cc);
    }

    @Test
    void sendHtmlMail() throws MessagingException {
        Map<String, Object> map = new HashMap<>();
        map.put("user", "陶治");
        map.put("dataCenterName", "默认数据中心");
        map.put("finishTime", DateUtil.now());
        map.put("description", "oracle全量备份");
        map.put("policyName", "oracle备份策略");
        map.put("jobType", "全备");
        map.put("usedTime", "30分钟");
        map.put("dataSize", "30G");
        map.put("jobResult", "成功");
        map.put("serverIp", "10.0.61.126");
        map.put("jobInfoUrl", "http://www.baidu.com");
        String htmlText = freeMarkerTemplateUtil.getEmailHtml(map, "jobexceptionwarning.ftl");

        mailSender.sendHtmlMail(to, subject, htmlText, cc);

    }

    @Test
    void sendAttachmentsMail() throws MessagingException {
        String content = "this is test attachments email";
        String filePath = "E:\\usr\\local\\mail\\attachments\\taozhi\\2017-12-22.log";
        mailSender.sendAttachmentsMail(to, subject, content, filePath, cc);
    }

    @Test
    void sendResourceMail() throws MessagingException {
        String content = "this is test resource email";
        String filePath = "E:\\usr\\local\\mail\\attachments\\taozhi\\background_fwqgx.png";
        String rscId = "background_fwqgx";
        mailSender.sendResourceMail(to, subject, content, filePath, rscId, cc);
    }
}