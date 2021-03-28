package com.tomhurry.dynamic.datasource.core;

import com.tomhurry.dynamic.datasource.model.DataSourceConfig;

/**
 * @author taozhi
 * @date 2021/3/27
 * @since 1.0.0
 */
public class DataSourceBeanNameGenerator {

    private DataSourceBeanNameGenerator() {
    }

    public static String genDataSourceBeanName(DataSourceConfig dataSourceConfig) {
        String ip = dataSourceConfig.getIp().replaceAll("\\.", "");
        return String.format("dataSourceConnection#%s%s", ip, dataSourceConfig.getPort());
    }
}
