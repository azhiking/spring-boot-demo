package com.tomhurry.dynamic.datasource.dto.param;

import lombok.Data;

import java.io.Serializable;

/**
 * 数据源接口入参
 *
 * @author taozhi
 * @date 2020/10/28
 * @since 1.0.0
 */
@Data
public class DataSourceParam implements Serializable {
    /**
     * 数据库ip
     */
    private String ip;
    /**
     * 数据库端口
     */
    private String port;
    /**
     * 库名
     */
    private String databaseName;
    /**
     * 登录账号
     */
    private String userName;
    /**
     * 密码
     */
    private String password;
    /**
     * 数据源类型：director | engine
     */
    private String dataSourceType;
}
