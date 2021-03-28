package com.tomhurry.dynamic.datasource.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.tomhurry.dynamic.datasource.core.DataSourceBeanNameGenerator;
import com.tomhurry.dynamic.datasource.core.DataSourceContextHolder;
import com.tomhurry.dynamic.datasource.core.DynamicDataSource;
import com.tomhurry.dynamic.datasource.dao.DataSourceConfigMapper;
import com.tomhurry.dynamic.datasource.model.DataSourceConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author taozhi
 * @date 2021/3/27
 * @since 1.0.0
 */
@Slf4j
@Service
public class DataSourceConfigService extends ServiceImpl<DataSourceConfigMapper, DataSourceConfig> {

    @Resource
    private DynamicDataSource dynamicDataSource;

    /**
     * 判断是否已存在
     *
     * @param ip       数据库ID
     * @param port     端口
     * @param database 数据库名称
     * @return
     */
    public boolean exist(String ip, String port, String database) {
        DataSourceConfig query = new DataSourceConfig();
        query.setIp(ip);
        query.setPort(port);
        query.setDatabaseName(database);
        return count(new QueryWrapper<>(query)) > 0;
    }

    /**
     * 分页查询
     *
     * @param page 页码
     * @param size 页大小
     * @return
     */
    public PageInfo<DataSourceConfig> listByPage(int page, int size) {
        PageHelper.startPage(page, size);
        return new PageInfo<>(list(new QueryWrapper<DataSourceConfig>().orderByDesc("create_time")));
    }

    /**
     * 根据类型查询
     *
     * @param type 类型
     * @return
     */
    public List<DataSourceConfig> listByType(String type) {
        DataSourceConfig query = new DataSourceConfig();
        query.setDataSourceType(type);
        return list(new QueryWrapper<>(query).orderByDesc("create_time"));
    }

    /**
     * 准备数据库连接
     *
     * @param id
     * @return
     */
    public boolean prepareDataSourceConnection(String id) {
        DataSourceConfig dataSourceConfig = getById(id);
        String key = DataSourceBeanNameGenerator.genDataSourceBeanName(dataSourceConfig);
        boolean result = true;
        if (!dynamicDataSource.exist(key)) {
            result = dynamicDataSource.createDataSource(key, dataSourceConfig.getDriverClassName(), dataSourceConfig.getUrl(), dataSourceConfig.getUserName(), dataSourceConfig.getPassword(), dataSourceConfig.getDataSourceType());
        }
        if (result) {
            DataSourceContextHolder.setDataSource(key);
        }
        return result;
    }

    /**
     * 销毁连接
     *
     * @param id
     */
    public void destroyDataSourceConnection(String id) {
        DataSourceContextHolder.clearDataSource();
        DataSourceConfig dataSourceConfig = getById(id);
        String key = DataSourceBeanNameGenerator.genDataSourceBeanName(dataSourceConfig);
        if (dynamicDataSource.exist(key)) {
            dynamicDataSource.deleteDataSource(key);
        }
    }


}
