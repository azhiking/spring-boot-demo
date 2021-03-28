package com.tomhurry.dynamic.datasource.sniffer.engine.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tomhurry.dynamic.datasource.dto.result.SnifferResult;
import com.tomhurry.dynamic.datasource.sniffer.engine.dao.ServiceReceiveMapper;
import com.tomhurry.dynamic.datasource.sniffer.engine.model.ServiceReceive;
import com.tomhurry.dynamic.datasource.sniffer.engine.service.ServiceReceiveService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 业务接收记录 服务实现类
 * </p>
 *
 * @author taozhi
 * @since 2021-01-14
 */
@Service
public class ServiceReceiveServiceImpl extends ServiceImpl<ServiceReceiveMapper, ServiceReceive> implements ServiceReceiveService {

    @Override
    public SnifferResult<ServiceReceive> convert(ServiceReceive serviceReceive) {
        SnifferResult<ServiceReceive> result = new SnifferResult<>();
        result.setTableName("service_receive");
        result.setDescription("engine task receive");
        result.setData(serviceReceive);
        return result;
    }
}
