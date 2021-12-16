package com.tomhurry.dynamic.rabbitmq;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.stereotype.Component;

/**
 * @author TaoZhi
 * @date 2021/11/13 23:59
 * @since 1.0.0
 */
@Component
public class SpringUtils implements BeanFactoryPostProcessor {

    private static DefaultListableBeanFactory defaultListableBeanFactory;

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory configurableListableBeanFactory) throws BeansException {

    }
}
