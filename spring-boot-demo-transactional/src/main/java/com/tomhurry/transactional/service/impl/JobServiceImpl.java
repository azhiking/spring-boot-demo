package com.tomhurry.transactional.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tomhurry.transactional.dao.JobMapper;
import com.tomhurry.transactional.model.Job;
import com.tomhurry.transactional.service.JobService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author taozhi
 * @since 2021-04-13
 */
@Service
public class JobServiceImpl extends ServiceImpl<JobMapper, Job> implements JobService {

}
