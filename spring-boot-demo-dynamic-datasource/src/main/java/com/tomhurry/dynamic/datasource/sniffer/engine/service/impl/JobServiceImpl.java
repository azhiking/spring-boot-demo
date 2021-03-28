package com.tomhurry.dynamic.datasource.sniffer.engine.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tomhurry.dynamic.datasource.dto.result.SnifferResult;
import com.tomhurry.dynamic.datasource.sniffer.engine.dao.JobMapper;
import com.tomhurry.dynamic.datasource.sniffer.engine.model.Job;
import com.tomhurry.dynamic.datasource.sniffer.engine.service.JobService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * job记录 服务实现类
 * </p>
 *
 * @author taozhi
 * @since 2021-01-14
 */
@Service
public class JobServiceImpl extends ServiceImpl<JobMapper, Job> implements JobService {

    @Override
    public SnifferResult<Job> convert(Job job) {
        SnifferResult<Job> result = new SnifferResult<>();
        result.setTableName("job");
        result.setDescription("engine job");
        result.setData(job);
        return result;
    }
}
