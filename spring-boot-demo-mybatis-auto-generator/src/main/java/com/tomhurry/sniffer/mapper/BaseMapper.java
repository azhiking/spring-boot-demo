package com.tomhurry.sniffer.mapper;

import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.MySqlMapper;

/**
 * @author taozhi
 * @date 2021/4/15
 * @since 1.0.0
 */
public interface BaseMapper<T> extends Mapper<T>, MySqlMapper<T> {
}
