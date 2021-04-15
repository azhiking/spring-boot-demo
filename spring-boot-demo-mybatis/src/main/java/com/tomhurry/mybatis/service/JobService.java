package com.tomhurry.mybatis.service;

import com.tomhurry.mybatis.mapper.JobMapper;
import com.tomhurry.mybatis.model.Job;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author taozhi
 * @date 2021/4/15
 * @since 1.0.0
 */
@Service
public class JobService {

    @Resource
    private JobMapper jobMapper;

    public int save(Job job) {
        return jobMapper.insert(job);
    }

    public Job findById(String id) {
        return jobMapper.selectById(id);
    }

}
