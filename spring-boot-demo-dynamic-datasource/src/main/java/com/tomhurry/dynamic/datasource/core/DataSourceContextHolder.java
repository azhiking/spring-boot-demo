package com.tomhurry.dynamic.datasource.core;

/**
 * 数据源上下文
 *
 * @author taozhi
 * @date 2020/12/21
 * @since 1.0.0
 */
public class DataSourceContextHolder {
    private static final ThreadLocal<String> contextHolder = new ThreadLocal<>();

    /**
     * 调用该方法，切换数据源
     *
     * @param dataSource
     */
    public static void setDataSource(String dataSource) {
        contextHolder.set(dataSource);
    }

    /**
     * 获取数据源
     *
     * @return
     */
    public static String getDataSource() {
        return contextHolder.get();
    }

    /**
     * 删除数据源
     */
    public static void clearDataSource() {
        contextHolder.remove();
    }
}
