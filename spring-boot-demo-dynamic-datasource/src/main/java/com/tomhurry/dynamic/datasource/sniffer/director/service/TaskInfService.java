package com.tomhurry.dynamic.datasource.sniffer.director.service;

import com.tomhurry.dynamic.datasource.dto.result.SnifferResult;
import com.tomhurry.dynamic.datasource.sniffer.director.model.TaskInf;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 操作信息表 服务类
 * </p>
 *
 * @author taozhi
 * @since 2021-01-14
 */
public interface TaskInfService extends IService<TaskInf> {

    SnifferResult<TaskInf> convert(TaskInf taskInf);
}
