package com.tomhurry.dynamic.datasource.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tomhurry.dynamic.datasource.model.DataSourceConfig;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author taozhi
 * @date 2020/10/27
 * @since 1.0.0
 */
@Mapper
public interface DataSourceConfigMapper extends BaseMapper<DataSourceConfig> {
}
