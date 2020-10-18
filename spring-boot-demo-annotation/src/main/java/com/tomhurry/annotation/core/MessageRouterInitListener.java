package com.tomhurry.annotation.core;

import com.alibaba.fastjson.JSON;
import com.tomhurry.annotation.annotation.MessageRouterMapping;
import com.tomhurry.annotation.model.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 消息路由初始化listener
 *
 * @author taozhi
 * @date 2020/10/13
 * @since 1.0.0
 */
@Slf4j
@Component
@Order(1)
public class MessageRouterInitListener implements ApplicationListener<ContextRefreshedEvent> {

    @Resource
    private AgentMessageRouter agentMessageRouter;
    @Resource
    private StorageMessageRouter storageMessageRouter;
    @Resource
    private VirtMessageRouter virtMessageRouter;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        log.info("开始初始化注解");
        ApplicationContext applicationContext = contextRefreshedEvent.getApplicationContext();
        if (applicationContext.getParent() != null) {
            applicationContext = applicationContext.getParent();
        }
        log.info("agent:{}", agentMessageRouter);
        log.info("storage:{}", storageMessageRouter);
        log.info("virt:{}", virtMessageRouter);
        // 获取所有用MessageHandler注释的bean
        Map<String, Object> messageHandlerBeans = applicationContext.getBeansWithAnnotation(com.tomhurry.annotation.annotation.MessageHandler.class);
        // 获取所有用MessageRouter注释的bean
        Map<String, Object> messageRouterBeans = applicationContext.getBeansWithAnnotation(MessageRouterMapping.class);

        for (Map.Entry<String, Object> entry : messageRouterBeans.entrySet()) {
            if (entry.getValue() instanceof MessageHandler) {
                MessageRouterMapping messageRouterMapping = AnnotationUtils.findAnnotation(entry.getValue().getClass(), MessageRouterMapping.class);
                if (ObjectUtils.isEmpty(messageRouterMapping)) {
                    continue;
                }
                log.info("{},{},{},{},{}", entry.getKey(), JSON.toJSONString(messageRouterMapping.action()), messageRouterMapping.object(), messageRouterMapping.status(), messageRouterMapping.type());

                // 组装所有消息处理handler
                List<MessageHandler> messageHandlerList = new ArrayList<>();
                for (Class<?> beanClass : messageRouterMapping.before()) {
                    Object handler = applicationContext.getBean(beanClass);
                    if (!ObjectUtils.isEmpty(handler)) {
                        if (handler instanceof MessageHandler) {
                            messageHandlerList.add((MessageHandler) handler);
                        }
                    }
                }
                messageHandlerList.add((MessageHandler) entry.getValue());
                for (Class<?> beanClass : messageRouterMapping.after()) {
                    Object handler = applicationContext.getBean(beanClass);
                    if (!ObjectUtils.isEmpty(handler)) {
                        if (handler instanceof MessageHandler) {
                            messageHandlerList.add((MessageHandler) handler);
                        }
                    }
                }

                MessageHandler[] messageHandlers = new MessageHandler[messageHandlerList.size()];
                messageHandlerList.toArray(messageHandlers);
                if (messageRouterMapping.type().equals("agent")) {
                    buildMessageRule(agentMessageRouter, messageRouterMapping.action(), messageRouterMapping.object(), messageRouterMapping.status(), messageHandlers);
                } else if (messageRouterMapping.type().equals("storage")) {
                    buildMessageRule(storageMessageRouter, messageRouterMapping.action(), messageRouterMapping.object(), messageRouterMapping.status(), messageHandlers);
                } else if (messageRouterMapping.type().equals("virt")) {
                    buildMessageRule(virtMessageRouter, messageRouterMapping.action(), messageRouterMapping.object(), messageRouterMapping.status(), messageHandlers);
                }
            } else {
                log.info("实例{}没有实现MessageHandler接口", entry.getKey());
            }
        }

        log.info("agent.rule:{}", agentMessageRouter.getRouterCount());
        log.info("storage.rule:{}", storageMessageRouter.getRouterCount());
        log.info("virt.rule:{}", virtMessageRouter.getRouterCount());

        agentMessageRouter.printAllRules();
        storageMessageRouter.printAllRules();
        virtMessageRouter.printAllRules();

        log.info("初始化完成");
    }

    /**
     * build消息路由规则
     *
     * @param messageRouter
     * @param actions
     * @param object
     * @param status
     * @param messageHandlers
     */
    private void buildMessageRule(MessageRouter messageRouter, String[] actions, String object, String status, MessageHandler[] messageHandlers) {
        for (String action : actions) {
            messageRouter.rule()
                    .action(action)
                    .type(object)
                    .status(status)
                    .handler(messageHandlers)
                    .end();
        }
    }
}
