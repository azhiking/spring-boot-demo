package com.tomhurry.dynamic.datasource.dto.result;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * 排查结果
 *
 * @author taozhi
 * @date 2021/1/15
 * @since 1.0.0
 */
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class SnifferResult<T> {

    private String tableName;

    private String description;

    private Date createTime;

    private Date updateTime;

    private T data;
}
