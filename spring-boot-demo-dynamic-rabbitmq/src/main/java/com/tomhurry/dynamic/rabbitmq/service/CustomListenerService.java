package com.tomhurry.dynamic.rabbitmq.service;

/**
 * 监听操作
 *
 * @author taozhi
 * @date 2020/8/26
 * @since 1.0.0
 */
public interface CustomListenerService {

    /**
     * 新增需要监听的队列
     *
     * @param queueName 队列名
     */
    void addNewListener(String queueName);

    /**
     * 删除已监听的队列
     *
     * @param queueName
     * @return
     */
    boolean removeListener(String queueName);
}
