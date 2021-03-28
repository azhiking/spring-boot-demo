package com.tomhurry.dynamic.datasource.sniffer.engine.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.tomhurry.dynamic.datasource.dto.result.SnifferResult;
import com.tomhurry.dynamic.datasource.sniffer.engine.model.ServiceReceive;

/**
 * <p>
 * 业务接收记录 服务类
 * </p>
 *
 * @author taozhi
 * @since 2021-01-14
 */
public interface ServiceReceiveService extends IService<ServiceReceive> {
    SnifferResult<ServiceReceive> convert(ServiceReceive serviceReceive);
}
