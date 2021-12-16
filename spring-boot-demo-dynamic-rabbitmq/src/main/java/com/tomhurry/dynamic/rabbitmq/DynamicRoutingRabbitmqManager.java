package com.tomhurry.dynamic.rabbitmq;

import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.connection.SimpleRoutingConnectionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
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
public class DynamicRoutingRabbitmqManager {

    @Resource
    private SimpleRoutingConnectionFactory simpleRoutingConnectionFactory;

    private final Map<Object, ConnectionFactory> targetConnectionFactories = new ConcurrentHashMap<Object, ConnectionFactory>();

    private final Map<String, Object> engineIdConnectionFactoryName = new HashMap<>();

    private ConnectionFactory defaultTargetConnectionFactory;

    @Bean
    public SimpleRoutingConnectionFactory defaultSimpleRoutingConnectionFactory() {
        return new SimpleRoutingConnectionFactory();
    }

    private ConnectionFactory getDefaultTargetConnectionFactory() {
        return this.defaultTargetConnectionFactory;
    }

    private void setDefaultTargetConnectionFactory(ConnectionFactory connectionFactory) {
        this.defaultTargetConnectionFactory = connectionFactory;
        simpleRoutingConnectionFactory.setDefaultTargetConnectionFactory(connectionFactory);
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
