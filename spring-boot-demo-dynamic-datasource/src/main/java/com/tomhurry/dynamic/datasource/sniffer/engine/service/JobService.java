package com.tomhurry.dynamic.datasource.sniffer.engine.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.tomhurry.dynamic.datasource.dto.result.SnifferResult;
import com.tomhurry.dynamic.datasource.sniffer.engine.model.Job;

/**
 * <p>
 * job记录 服务类
 * </p>
 *
 * @author taozhi
 * @since 2021-01-14
 */
public interface JobService extends IService<Job> {

    SnifferResult<Job> convert(Job job);

}
