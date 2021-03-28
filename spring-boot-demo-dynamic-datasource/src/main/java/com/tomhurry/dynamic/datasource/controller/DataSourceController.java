package com.tomhurry.dynamic.datasource.controller;

import cn.hutool.core.date.DateUtil;
import com.github.pagehelper.PageInfo;
import com.tomhurry.api.ApiResponse;
import com.tomhurry.dynamic.datasource.dto.param.DataSourceParam;
import com.tomhurry.dynamic.datasource.model.DataSourceConfig;
import com.tomhurry.dynamic.datasource.service.DataSourceConfigService;
import com.tomhurry.enums.ApiResponseEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author taozhi
 * @date 2021/3/27
 * @since 1.0.0
 */
@RestController
@Slf4j
public class DataSourceController {
    @Resource
    private DataSourceConfigService dataSourceConfigService;

    /**
     * 新增数据库信息
     *
     * @param dataSourceParam 数据库信息入参对象
     * @return
     */
    @PostMapping("/sniffer/datasource")
    public ApiResponse<String> postDataSource(@RequestBody DataSourceParam dataSourceParam) {

        if (dataSourceConfigService.exist(dataSourceParam.getIp(), dataSourceParam.getPort(), dataSourceParam.getDatabaseName())) {
            return ApiResponse.genResponse(ApiResponseEnum.DATA_EXIST);
        }

        DataSourceConfig dataSourceConfig = new DataSourceConfig();
        BeanUtils.copyProperties(dataSourceParam, dataSourceConfig);
        dataSourceConfig.setDriverClassName("com.mysql.cj.jdbc.Driver");
        dataSourceConfig.setUrl(String.format("jdbc:mysql://%s:%s/%s?useUnicode=true&characterEncoding=UTF-8", dataSourceParam.getIp(), dataSourceParam.getPort(), dataSourceParam.getDatabaseName()));
        dataSourceConfig.setCreateTime(DateUtil.current(false));
        dataSourceConfig.setUpdateTime(DateUtil.current(false));
        dataSourceConfigService.save(dataSourceConfig);

        return ApiResponse.genOkResponse();
    }

    /**
     * 删除数据库参数
     *
     * @param id 记录ID
     * @return
     */
    @DeleteMapping("/sniffer/datasource")
    public ApiResponse<String> deleteDataSource(String id) {
        dataSourceConfigService.removeById(id);
        return ApiResponse.genOkResponse();
    }

    /**
     * 分页查询所有数据库信息
     *
     * @param page 页码
     * @param size 大小
     * @return
     */
    @GetMapping("/sniffer/list")
    public ApiResponse<PageInfo<DataSourceConfig>> list(Integer page, Integer size) {
        return ApiResponse.genOkResponse(dataSourceConfigService.listByPage(page, size));
    }

    /**
     * 根据类型查询数据库信息
     *
     * @param type 类型：engine | director
     * @return
     */
    @GetMapping("/sniffer/datasource/type")
    public ApiResponse<List<DataSourceConfig>> listByType(String type) {
        return ApiResponse.genOkResponse(dataSourceConfigService.listByType(type));
    }
}
