package com.tomhurry;

import com.tomhurry.lock.LockFactory;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.Resource;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Hello world!
 */
@MapperScan("com.tomhurry.lock.postgres.dao.mapper")
@SpringBootApplication
public class App implements CommandLineRunner {

    @Qualifier("postgres")
    @Resource()
    private LockFactory postgresLockFactory;
    @Qualifier("redis")
    @Resource()
    private LockFactory redisLockFactory;

    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }

    @Override
    public void run(String... args) throws Exception {

//        redisDistributedLockFactory.getLock("redis").lock();
        ScheduledExecutorService scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
        scheduledExecutorService.scheduleAtFixedRate(() -> {
            postgresLockFactory.getLock("postgres").tryLock(5 * 1000L);
        }, 0, 10, TimeUnit.SECONDS);

    }
}
