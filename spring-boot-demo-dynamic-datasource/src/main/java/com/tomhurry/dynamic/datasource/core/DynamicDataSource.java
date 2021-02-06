package com.tomhurry.dynamic.datasource.core;

import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;
import org.springframework.stereotype.Service;

/**
 * 动态路由数据源
 *
 * @author taozhi
 * @date 2020/12/21
 * @since 1.0.0
 */
@Slf4j
@Service
public class DynamicDataSource extends AbstractRoutingDataSource {
    @Override
    protected Object determineCurrentLookupKey() {
        return null;
    }
}
