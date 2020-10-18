package com.tomhurry.annotation;

import com.tomhurry.annotation.message.MessageReceiveService;
import com.tomhurry.annotation.model.MqMessage;
import com.tomhurry.util.SpringUtil;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.Resource;

@SpringBootApplication
public class SpringBootDemoAnnotationApplication implements ApplicationRunner {

    @Resource
    private MessageReceiveService messageReceiveService;

    public static void main(String[] args) {
        SpringApplication.run(SpringBootDemoAnnotationApplication.class, args);
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        MqMessage mqMessage = new MqMessage();
        mqMessage.setAction("fbackup");
        mqMessage.setObject("mysql");
        mqMessage.setStatus("1");

        messageReceiveService.receiveFromAgent(mqMessage);
        messageReceiveService.receiveFromStorage(mqMessage);
        messageReceiveService.receiveFromVirt(mqMessage);
    }
}
