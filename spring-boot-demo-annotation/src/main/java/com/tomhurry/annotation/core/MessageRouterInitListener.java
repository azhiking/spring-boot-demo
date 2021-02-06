package com.tomhurry.annotation.core;

import com.alibaba.fastjson.JSON;
import com.tomhurry.annotation.annotation.RuleNode;
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
        // 获取所有用MessageRouter注释的bean
        Map<String, Object> messageRouterBeans = applicationContext.getBeansWithAnnotation(RuleNode.class);

        for (Map.Entry<String, Object> entry : messageRouterBeans.entrySet()) {
            if (entry.getValue() instanceof MessageHandler) {
                RuleNode ruleNode = AnnotationUtils.findAnnotation(entry.getValue().getClass(), RuleNode.class);
                if (ObjectUtils.isEmpty(ruleNode)) {
                    continue;
                }
                log.info("{},{},{},{},{}", entry.getKey(), JSON.toJSONString(ruleNode.action()), ruleNode.object(), ruleNode.status(), ruleNode.type());

                // 组装所有消息处理handler
                List<MessageHandler> messageHandlerList = new ArrayList<>();
                for (Class<?> beanClass : ruleNode.before()) {
                    Object handler = applicationContext.getBean(beanClass);
                    if (!ObjectUtils.isEmpty(handler)) {
                        messageHandlerList.add((MessageHandler) handler);
                    }
                }
                messageHandlerList.add((MessageHandler) entry.getValue());
                for (Class<?> beanClass : ruleNode.after()) {
                    Object handler = applicationContext.getBean(beanClass);
                    if (!ObjectUtils.isEmpty(handler)) {
                        messageHandlerList.add((MessageHandler) handler);
                    }
                }

                MessageHandler[] messageHandlers = new MessageHandler[messageHandlerList.size()];
                messageHandlerList.toArray(messageHandlers);
                if (ruleNode.type().equals("agent")) {
                    buildMessageRule(agentMessageRouter, ruleNode.action(), ruleNode.object(), ruleNode.status(), messageHandlers);
                } else if (ruleNode.type().equals("storage")) {
                    buildMessageRule(storageMessageRouter, ruleNode.action(), ruleNode.object(), ruleNode.status(), messageHandlers);
                } else if (ruleNode.type().equals("virt")) {
                    buildMessageRule(virtMessageRouter, ruleNode.action(), ruleNode.object(), ruleNode.status(), messageHandlers);
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
