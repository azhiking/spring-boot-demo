package com.tomhurry.dynamic.rabbitmq;

import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.connection.SimpleRoutingConnectionFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * MQ动态管理类
 *
 * @author taozhi
 * @date 2021/2/26
 * @since 1.0.0
 */
@Service
public class DynamicRoutingConnectionManager {

    @Resource
    private SimpleRoutingConnectionFactory simpleRoutingConnectionFactory;

    private final Map<Object, ConnectionFactory> targetConnectionFactories = new ConcurrentHashMap<Object, ConnectionFactory>();

    private ConnectionFactory defaultTargetConnectionFactory;

    /**
     * 设置默认MQ连接
     *
     * @param connectionFactory
     */
    public void setDefaultTargetConnectionFactory(ConnectionFactory connectionFactory) {
        this.defaultTargetConnectionFactory = connectionFactory;
        simpleRoutingConnectionFactory.setDefaultTargetConnectionFactory(connectionFactory);
    }

    public ConnectionFactory getDefaultTargetConnectionFactory() {
        return this.defaultTargetConnectionFactory;
    }

    public void addTargetConnectionFactory(String key, ConnectionFactory connectionFactory) {
        this.targetConnectionFactories.computeIfAbsent(key, o -> connectionFactory);
        simpleRoutingConnectionFactory.setTargetConnectionFactories(this.targetConnectionFactories);
    }

    public void removeTargetConnectionFactory(String key) {
        this.targetConnectionFactories.remove(key);
        simpleRoutingConnectionFactory.setTargetConnectionFactories(this.targetConnectionFactories);
    }

    public String genConnectionFactoryBeanName(String address) {
        return String.format("connectionFactory#%s", address);
    }

}
