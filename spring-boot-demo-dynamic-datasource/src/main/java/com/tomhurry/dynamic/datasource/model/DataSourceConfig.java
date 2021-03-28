package com.tomhurry.dynamic.datasource.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * 数据库连接参数
 *
 * @author taozhi
 * @date 2020/12/21
 * @since 1.0.0
 */
@Data
@TableName("data_source_config")
public class DataSourceConfig {
    @TableId(type = IdType.ASSIGN_UUID)
    private String id;

    @TableField("name")
    private String name;

    @TableField("driver_class_name")
    private String driverClassName;

    private String ip;

    private String port;

    @TableField("database_name")
    private String databaseName;

    private String url;
    @TableField("user_name")
    private String userName;

    private String password;

    @TableField("data_source_type")
    private String dataSourceType;

    private Long createTime;

    private Long updateTime;
}
