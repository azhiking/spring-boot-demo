package com.tomhurry.annotation.model;

/**
 * @author taozhi
 * @date 2020/10/13
 * @since 1.0.0
 */
public interface MessageHandler {
    /**
     * 处理消息
     *
     * @param message
     * @throws Exception
     */
    void handle(MqMessage message) throws Exception;
}
