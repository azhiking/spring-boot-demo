package com.tomhurry.dynamic.rabbitmq.service.impl;

import com.tomhurry.dynamic.rabbitmq.RabbitmqConfig;
import com.tomhurry.dynamic.rabbitmq.service.RabbitmqService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;

/**
 * MQ操作实现
 *
 * @author taozhi
 * @date 2020/8/26
 * @since 1.0.0
 */
@Slf4j
@Service
public class RabbitmqServiceImpl implements RabbitmqService {

    @Resource
    private RabbitAdmin rabbitAdmin;
    @Resource
    private RabbitmqConfig rabbitmqConfig;

    @Override
    public void createExchange(String exchangeName) {
        createExchange(exchangeName, rabbitmqConfig.isDurable(), rabbitmqConfig.isAutoDelete());
    }

    @Override
    public void createExchange(String exchangeName, boolean durable, boolean autoDelete) {
        DirectExchange directExchange = new DirectExchange(exchangeName, durable, autoDelete);
        rabbitAdmin.declareExchange(directExchange);
    }

    @Override
    public void deleteExchange(String exchangeName) {
        rabbitAdmin.deleteExchange(exchangeName);
    }

    @Override
    public void createQueue(String queueName) {
        createQueue(queueName, true, false, false);
    }

    @Override
    public void createQueue(String queueName, boolean durable, boolean exclusive, boolean autoDelete) {
        Queue queue = new Queue(queueName, durable, exclusive, autoDelete);
        if (ObjectUtils.isEmpty(rabbitAdmin.getQueueInfo(queueName))) {
            log.info("创建队列{}", queueName);
            rabbitAdmin.declareQueue(queue);
        }
    }

    @Override
    public void deleteQueue(String queueName) {
        rabbitAdmin.deleteQueue(queueName);
    }

    @Override
    public void bind(String queueName, String exchangeName, String routingKey) {
        rabbitAdmin.declareBinding(new Binding(queueName, Binding.DestinationType.QUEUE, exchangeName, routingKey, null));
    }
}
