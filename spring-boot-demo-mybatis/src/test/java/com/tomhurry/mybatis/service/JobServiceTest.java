package com.tomhurry.mybatis.service;

import com.tomhurry.mybatis.model.Job;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.Assert;

import javax.annotation.Resource;

@Slf4j
@SpringBootTest
public class JobServiceTest {

    @Resource
    private JobService jobService;

    @Test
    public void saveTest() {
        Job job = new Job();
        job.setId("112233");
        job.setName("test");
        job.setStatus("1");
        Assert.isTrue(jobService.save(job) > 0, "插入失败");
    }

    @Test
    public void findByIdTest() {
        String id = "112233";
        Job job = jobService.findById(id);
        log.info("查询结果：{}", job);
    }
}