package com.tomhurry.dynamic.rabbitmq;

import com.tomhurry.dynamic.rabbitmq.model.CdmoneEngine;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.connection.SimpleRoutingConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author TaoZhi
 * @date 2021/11/13 23:41
 * @since 1.0.0
 */
@Service
public class RabbitmqRoutingManager {

    private final Map<Object, ConnectionFactory> targetConnectionFactories = new ConcurrentHashMap<>();
    private final Map<Object, RabbitAdmin> targetRabbitAdmins = new ConcurrentHashMap<>();
    private final Map<String, Object> engineIdConnectionFactoryName = new HashMap<>();
    @Resource
    private DefaultListableBeanFactory defaultListableBeanFactory;
    @Resource
    private SimpleRoutingConnectionFactory simpleRoutingConnectionFactory;

    @Bean
    public SimpleRoutingConnectionFactory defaultSimpleRoutingConnectionFactory() {
        return new SimpleRoutingConnectionFactory();
    }


    public void addNewConnection(CdmoneEngine cdmoneEngine) throws Exception {
        RabbitmqConfig config = new RabbitmqConfig(cdmoneEngine);
        ConnectionFactory connectionFactory = registerConnectionFactory(config);
        String connectionFactoryName = config.getConnectionFactoryName();
        if (!targetConnectionFactories.containsKey(connectionFactoryName)) {
            targetConnectionFactories.put(connectionFactoryName, connectionFactory);
            simpleRoutingConnectionFactory.setTargetConnectionFactories(targetConnectionFactories);
            simpleRoutingConnectionFactory.afterPropertiesSet();
        }

        RabbitAdmin rabbitAdmin = registerRabbitAdminBean(config);
        String rabbitAdminName = config.getRabbitAdminName();
        if (!targetRabbitAdmins.containsKey(rabbitAdminName)) {
            targetRabbitAdmins.put(rabbitAdminName, rabbitAdmin);
        }

    }

    public RabbitAdmin registerRabbitAdminBean(RabbitmqConfig rabbitmqConfig) {
        defaultListableBeanFactory.registerSingleton(rabbitmqConfig.getRabbitAdminName(), rabbitmqConfig.getRabbitAdmin());
        return defaultListableBeanFactory.getBean(rabbitmqConfig.getRabbitAdminName(), RabbitAdmin.class);
    }

    public ConnectionFactory registerConnectionFactory(RabbitmqConfig rabbitmqConfig) {
        defaultListableBeanFactory.registerSingleton(rabbitmqConfig.getConnectionFactoryName(), rabbitmqConfig.getConnectionFactory());
        return defaultListableBeanFactory.getBean(rabbitmqConfig.getConnectionFactoryName(), ConnectionFactory.class);
    }

}
