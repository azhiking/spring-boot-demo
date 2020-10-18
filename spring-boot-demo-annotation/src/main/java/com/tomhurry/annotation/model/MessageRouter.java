package com.tomhurry.annotation.model;

import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

/**
 * 消息路由机制，通过代码的配置方式，对MQ发来的消息进行拦截处理
 *
 * @author taozhi
 * @date 2020/10/14
 * @since 1.0.0
 */
@Slf4j
public class MessageRouter {
    /**
     * MQ消息规则集合
     */
    private final List<MessageRouterRule> rules = new ArrayList<>();

    List<MessageRouterRule> getRules() {
        return this.rules;
    }

    /**
     * 开始一个新的Route规则
     *
     * @return
     */
    public MessageRouterRule rule() {
        return new MessageRouterRule(this);
    }

    /**
     * 处理MQ消息
     *
     * @param message MQ消息
     */
    public void route(final MqMessage message) {

        final List<MessageRouterRule> matchRules = new ArrayList<>();

        /**
         * 收集匹配的规则
         */
        for (final MessageRouterRule rule : rules) {
            if (rule.test(message)) {
                matchRules.add(rule);
                if (!rule.isReEnter()) {
                    break;
                }
            }
        }

        for (final MessageRouterRule rule : matchRules) {
            rule.service(message);
        }
    }

    public void printAllRules() {
        for (MessageRouterRule rule : rules) {
            log.info("{}", rule.toString());
        }
    }
}
