package com.tomhurry.annotation.model;

import lombok.Data;

/**
 * MQ消息
 *
 * @author taozhi
 * @date 2020/10/14
 * @since 1.0.0
 */
@Data
public class MqMessage {
    private String action;

    private String object;

    private String status;
}
