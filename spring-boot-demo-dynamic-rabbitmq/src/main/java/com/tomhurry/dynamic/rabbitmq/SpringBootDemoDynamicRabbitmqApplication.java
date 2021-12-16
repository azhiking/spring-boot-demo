package com.tomhurry.dynamic.rabbitmq;

import org.springframework.amqp.rabbit.annotation.MultiRabbitListenerAnnotationBeanPostProcessor;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.annotation.RabbitListenerAnnotationBeanPostProcessor;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.*;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.RabbitListenerEndpointRegistry;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@SpringBootApplication
public class SpringBootDemoDynamicRabbitmqApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringBootDemoDynamicRabbitmqApplication.class, args);
    }

    @Bean
    CachingConnectionFactory cf1() {
        return new CachingConnectionFactory("localhost");
    }

    @Bean
    CachingConnectionFactory cf2() {
        return new CachingConnectionFactory("otherHost");
    }

    @Bean
    CachingConnectionFactory cf3() {
        return new CachingConnectionFactory("thirdHost");
    }

    @Bean
    SimpleRoutingConnectionFactory rcf(CachingConnectionFactory cf1,
                                       CachingConnectionFactory cf2, CachingConnectionFactory cf3) {

        SimpleRoutingConnectionFactory rcf = new SimpleRoutingConnectionFactory();
        rcf.setDefaultTargetConnectionFactory(cf1);
        Map<Object, ConnectionFactory> factoryMap = new HashMap<>();
        factoryMap.put("one", cf1);
        factoryMap.put("two", cf2);
        factoryMap.put("three", cf3);
        rcf.setTargetConnectionFactories(factoryMap);
        return rcf;
    }

    @Bean("factory1-admin")
    RabbitAdmin admin1(CachingConnectionFactory cf1) {
        return new RabbitAdmin(cf1);
    }

    @Bean("factory2-admin")
    RabbitAdmin admin2(CachingConnectionFactory cf2) {
        return new RabbitAdmin(cf2);
    }

    @Bean("factory3-admin")
    RabbitAdmin admin3(CachingConnectionFactory cf3) {
        return new RabbitAdmin(cf3);
    }

    @Bean
    public RabbitListenerEndpointRegistry rabbitListenerEndpointRegistry() {
        return new RabbitListenerEndpointRegistry();
    }

    @Bean
    public RabbitListenerAnnotationBeanPostProcessor postProcessor(RabbitListenerEndpointRegistry registry) {
        MultiRabbitListenerAnnotationBeanPostProcessor postProcessor
                = new MultiRabbitListenerAnnotationBeanPostProcessor();
        postProcessor.setEndpointRegistry(registry);
        postProcessor.setContainerFactoryBeanName("defaultContainerFactory");

        return postProcessor;
    }

    @Bean
    public SimpleRabbitListenerContainerFactory factory1(CachingConnectionFactory cf1) {
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        factory.setConnectionFactory(cf1);
        return factory;
    }

    @Bean
    public SimpleRabbitListenerContainerFactory factory2(CachingConnectionFactory cf2) {
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        factory.setConnectionFactory(cf2);

        return factory;
    }

    @Bean
    public SimpleRabbitListenerContainerFactory factory3(CachingConnectionFactory cf3) {
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        factory.setConnectionFactory(cf3);
        return factory;
    }

    @Bean
    RabbitTemplate template(ConnectionFactory rcf) {
        return new RabbitTemplate(rcf);
    }

    @Bean
    ConnectionFactoryContextWrapper wrapper(SimpleRoutingConnectionFactory rcf) {
        return new ConnectionFactoryContextWrapper(rcf);
    }

}

@Component
class Listeners {

    @RabbitListener(queuesToDeclare = @Queue("q1"), containerFactory = "factory1")
    public void listen1(String in) {
        SimpleResourceHolder.get(this);
    }

    @RabbitListener(queuesToDeclare = @Queue("q2"), containerFactory = "factory2")
    public void listen2(String in) {

    }

    @RabbitListener(queuesToDeclare = @Queue("q3"), containerFactory = "factory3")
    public void listen3(String in) {

    }

}
