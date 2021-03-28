package com.tomhurry.dynamic.rabbitmq.service.impl;

import com.tomhurry.dynamic.rabbitmq.service.CustomListenerService;
import com.tomhurry.util.SpringUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 监听操作实现
 *
 * @author taozhi
 * @date 2020/8/26
 * @since 1.0.0
 */
@Slf4j
@Service
public class CustomListenerServiceImpl implements CustomListenerService {

    @Resource
    private SimpleMessageListenerContainer simpleMessageListenerContainer;

    @Override
    public void addNewListener(String queueName) {
        if (!ObjectUtils.isEmpty(queueName)) {
            List<String> queueList = new ArrayList<>();
            Collections.addAll(queueList, simpleMessageListenerContainer.getQueueNames());
            if (queueList.contains(queueName)) {
                return;
            }
            simpleMessageListenerContainer.addQueueNames(queueName);
        }
    }

    @Override
    public boolean removeListener(String queueName) {
        if (!ObjectUtils.isEmpty(queueName)) {
            SimpleMessageListenerContainer container = SpringUtil.getBean(SimpleMessageListenerContainer.class);
            return container.removeQueueNames(queueName);
        }
        return false;
    }
}
