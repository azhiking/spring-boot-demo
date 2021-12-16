package com.tomhurry.dynamic.rabbitmq.mapper;

import com.tomhurry.dynamic.rabbitmq.model.CdmoneEngine;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * @author TaoZhi
 * @date 2021/10/27 0:22
 * @since 1.0.0
 */
@Mapper
public interface CdmoneEngineMapper {

    CdmoneEngine selectById(@Param("id") String id);
}
