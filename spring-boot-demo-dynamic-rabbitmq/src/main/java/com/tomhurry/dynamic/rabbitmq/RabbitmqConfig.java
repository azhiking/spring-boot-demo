package com.tomhurry.dynamic.rabbitmq;

import com.tomhurry.dynamic.rabbitmq.model.CdmoneEngine;
import lombok.Data;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.util.ClassUtils;

import java.beans.Introspector;

/**
 * MQ配置参数
 *
 * @author taozhi
 * @date 2020/8/26
 * @since 1.0.0
 */
@Data
public class RabbitmqConfig {

    private Integer id;

    private String engineId;

    private String address;

    private String username;

    private String password;

    private String virtualHost = "/";

    private boolean durable = true;

    private boolean autoDelete = false;

    public RabbitmqConfig(CdmoneEngine cdmoneEngine) {
        this.id = cdmoneEngine.getId();
        this.engineId = cdmoneEngine.getEngineId();
        this.address = cdmoneEngine.getAddress();
        this.username = cdmoneEngine.getUsername();
        this.password = cdmoneEngine.getPassword();
    }

    public RabbitAdmin getRabbitAdmin() {
        RabbitAdmin rabbitAdmin = new RabbitAdmin(getConnectionFactory());
        rabbitAdmin.setAutoStartup(true);
        return rabbitAdmin;
    }

    public ConnectionFactory getConnectionFactory() {
        CachingConnectionFactory factory = new CachingConnectionFactory();
        factory.setAddresses(address);
        factory.setUsername(username);
        factory.setPassword(password);
        factory.setVirtualHost(virtualHost);
        return factory;
    }

    public String getRabbitAdminName() {
        String beanClassName = "RabbitAdmin";
        String shortClassName = ClassUtils.getShortName(beanClassName);
        return Introspector.decapitalize(shortClassName).concat("#").concat(this.id.toString());
    }

    public String getConnectionFactoryName() {
        String beanClassName = "ConnectionFactory";
        String shortClassName = ClassUtils.getShortName(beanClassName);
        return Introspector.decapitalize(shortClassName).concat("#").concat(this.id.toString());
    }
}
