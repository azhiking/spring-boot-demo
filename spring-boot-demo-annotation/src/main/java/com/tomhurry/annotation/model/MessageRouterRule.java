package com.tomhurry.annotation.model;

import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * MQ消息路由规则
 *
 * @author taozhi
 * @date 2020/10/14
 * @since 1.0.0
 */
@Slf4j
public class MessageRouterRule {

    /**
     * 业务动作
     */
    private String action;

    /**
     * 业务类型：oracle，vmware
     */
    private String type;

    /**
     * 状态： 1 成功，0 失败
     */
    private String status;

    private boolean reEnter = false;

    private final MessageRouter routerBuilder;

    private List<MessageHandler> handlers = new ArrayList<>();

    public MessageRouterRule(MessageRouter routerBuilder) {
        this.routerBuilder = routerBuilder;
    }

    /**
     * 如果业务动作等于某个动作时触发
     *
     * @param action 业务动作
     * @return
     */
    public MessageRouterRule action(String action) {
        this.action = action;
        return this;
    }

    /**
     * 如果业务类型等于某个类型时触发
     *
     * @param type 业务类型：oracle，vmware
     * @return
     */
    public MessageRouterRule type(String type) {
        this.type = type;
        return this;
    }

    /**
     * 如果状态等于某个状态时触发
     *
     * @param status 状态
     * @return
     */
    public MessageRouterRule status(String status) {
        this.status = status;
        return this;
    }

    /**
     * 规则结束，代表如果一个消息匹配该规则，那么它将不再会进入其他规则
     *
     * @return
     */
    public MessageRouter end() {
        this.routerBuilder.getRules().add(this);
        return this.routerBuilder;
    }

    /**
     * 规则结束，但是消息还会进入其他规则
     *
     * @return
     */
    public MessageRouter next() {
        this.reEnter = true;
        return end();
    }

    public boolean isReEnter() {
        return reEnter;
    }

    /**
     * 设置MQ消息处理器
     *
     * @param handler
     * @return
     */
    public MessageRouterRule handler(MessageHandler handler) {
        return handler(handler, (MessageHandler[]) null);
    }

    /**
     * 设置MQ消息处理器
     *
     * @param handler
     * @param otherHandlers
     * @return
     */
    public MessageRouterRule handler(MessageHandler handler, MessageHandler... otherHandlers) {
        this.handlers.add(handler);
        if (otherHandlers != null && otherHandlers.length > 0) {
            for (MessageHandler i : otherHandlers) {
                this.handlers.add(i);
            }
        }
        return this;
    }

    public MessageRouterRule handler(MessageHandler... handlers) {
        if (handlers != null && handlers.length > 0) {
            this.handlers.addAll(Arrays.asList(handlers));
        }
        return this;
    }

    /**
     * 匹配MQ消息
     *
     * @param message MQ消息
     * @return
     */
    protected boolean test(MqMessage message) {
        return action.equals(message.getAction()) && type.equals(message.getObject()) && status.equals(message.getStatus());
    }

    /**
     * 处理MQ推送过来的消息
     *
     * @param message MQ消息
     * @return true 代表继续执行别的router，false 代表停止执行别的router
     */
    protected void service(MqMessage message) {

        try {
            /**
             * 交给handler处理
             */
            for (MessageHandler handler : this.handlers) {
                /**
                 * 返回最后handler的结果
                 */
                handler.handle(message);
            }
        } catch (Exception e) {
            log.error("执行处理MQ消息出错！", e);
        }
    }

    @Override
    public String toString() {
        return "MessageRouterRule{" +
                "action='" + action + '\'' +
                ", type='" + type + '\'' +
                ", status='" + status + '\'' +
                ", reEnter=" + reEnter +
                ", routerBuilder=" + routerBuilder +
                ", handlers=" + handlers +
                '}';
    }
}
