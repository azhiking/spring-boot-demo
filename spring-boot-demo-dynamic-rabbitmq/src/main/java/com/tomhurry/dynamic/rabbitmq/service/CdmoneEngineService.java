package com.tomhurry.dynamic.rabbitmq.service;

import com.tomhurry.dynamic.rabbitmq.mapper.CdmoneEngineMapper;
import com.tomhurry.dynamic.rabbitmq.model.CdmoneEngine;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author TaoZhi
 * @date 2021/10/27 0:26
 * @since 1.0.0
 */
@Slf4j
@Service
public class CdmoneEngineService {
    @Resource
    private CdmoneEngineMapper cdmoneEngineMapper;

    public CdmoneEngine getById(String id) {
        return cdmoneEngineMapper.selectById(id);
    }
}
