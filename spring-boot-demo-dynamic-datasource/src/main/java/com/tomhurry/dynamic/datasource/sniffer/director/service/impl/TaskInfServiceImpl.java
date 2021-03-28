package com.tomhurry.dynamic.datasource.sniffer.director.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tomhurry.dynamic.datasource.sniffer.director.dao.TaskInfMapper;
import com.tomhurry.dynamic.datasource.dto.result.SnifferResult;
import com.tomhurry.dynamic.datasource.sniffer.director.model.TaskInf;
import com.tomhurry.dynamic.datasource.sniffer.director.service.TaskInfService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 操作信息表 服务实现类
 * </p>
 *
 * @author taozhi
 * @since 2021-01-14
 */
@Service
public class TaskInfServiceImpl extends ServiceImpl<TaskInfMapper, TaskInf> implements TaskInfService {

    @Override
    public SnifferResult<TaskInf> convert(TaskInf taskInf) {
        SnifferResult<TaskInf> result = new SnifferResult<>();
        result.setTableName("task_inf");
        result.setDescription("director task");
        result.setData(taskInf);
        return result;
    }
}
