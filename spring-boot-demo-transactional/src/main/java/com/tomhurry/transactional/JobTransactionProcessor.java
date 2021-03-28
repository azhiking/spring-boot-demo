package com.tomhurry.transactional;

import com.tomhurry.transactional.service.JobService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * @author taozhi
 * @date 2021/3/20
 * @since 1.0.0
 */
@Slf4j
@Service
public class JobTransactionProcessor {

    @Resource
    private JobService jobService;

    @Transactional(rollbackFor = Exception.class)
    public void execute(String jobId) {
        runJob(jobId);
    }


    public void runJob(String jobId) {
        try {
            log.info("【{}】开始处理", jobId);
            jobService.saveJob(jobId, "success");

            int i = 1 / 0;

        } catch (Exception e) {
//            jobId = "22223333444455556666777788889999";
            jobService.saveFailedJob(jobId, "failed");
            throw e;
        } finally {
            log.info("【{}】处理结束", jobId);
        }
    }
}
