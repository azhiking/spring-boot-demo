package com.tomhurry.transactional;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

import javax.annotation.Resource;

@EnableAsync
@SpringBootApplication
public class SpringBootDemoTransactionalApplication implements CommandLineRunner {

    @Resource
    private JobTransactionProcessor jobTransactionProcessor;

    public static void main(String[] args) {
        SpringApplication.run(SpringBootDemoTransactionalApplication.class, args);
    }


    @Override
    public void run(String... args) throws Exception {
        String jobId = "11112222333344445555666677778888";
        jobTransactionProcessor.execute(jobId);
    }
}
