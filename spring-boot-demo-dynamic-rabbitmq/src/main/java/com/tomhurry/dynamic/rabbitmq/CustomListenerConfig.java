package com.tomhurry.dynamic.rabbitmq;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.AcknowledgeMode;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;

/**
 * 自定义监听器配置
 *
 * @author taozhi
 * @date 2020/8/26
 * @since 1.0.0
 */
@Slf4j
@Configuration
public class CustomListenerConfig {

    @Resource
    private RabbitmqConfig rabbitmqConfig;

    @Resource
    private MessageReceiveService messageReceiveService;

    @Bean
    public SimpleMessageListenerContainer simpleMessageListenerContainer() {
        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer(rabbitmqConfig.getConnectionFactory());
        container.setConsumerStartTimeout(3000L);
        container.setExposeListenerChannel(true);
        // 设置确认模式，自动确认
        container.setAcknowledgeMode(AcknowledgeMode.AUTO);
        container.setMessageListener(messageReceiveService);
        return container;
    }


    @Bean
    public void start() {
        try {
            log.info("container start");
            simpleMessageListenerContainer().start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
