package com.tomhurry.dynamic.rabbitmq;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageListener;
import org.springframework.stereotype.Service;

/**
 * MQ接收处理类
 *
 * @author taozhi
 * @date 2020/8/25
 * @since 1.0.0
 */
@Slf4j
@Service
public class MessageReceiveService implements MessageListener {
    @Override
    public void onMessage(Message message) {
        try {
            String msg = new String(message.getBody());
            String queueName = message.getMessageProperties().getConsumerQueue();
            log.info("接收到{}消息：{}", queueName, msg);
        } catch (Exception e) {
            log.error("MQ接收消息错误", e);
        }
    }
}