package com.tomhurry.dynamic.rabbitmq.model;

import lombok.Data;

import java.util.Date;

/**
 * engine信息
 *
 * @author TaoZhi
 * @date 2021/10/27 0:14
 * @since 1.0.0
 */
@Data
public class CdmoneEngine {

    private Integer id;

    private String engineId;

    private String address;

    private String username;

    private String password;

    private String status;

    private String extendedInfo;

    private Date createTime;

    private Date updateTime;
}
