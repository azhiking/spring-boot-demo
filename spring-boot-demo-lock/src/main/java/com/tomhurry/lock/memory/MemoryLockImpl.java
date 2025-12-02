package com.tomhurry.lock.memory;

import com.tomhurry.lock.Lock;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 内存锁实现
 *
 * @author taozhi
 * @date 2025/11/7 15:54
 * @since 1.0.0
 */
public class MemoryLockImpl implements Lock {

    private static final Logger LOGGER = LoggerFactory.getLogger(MemoryLockImpl.class);

    // 全局共享的锁映射表
    private static final ConcurrentHashMap<String, ReentrantLock> LOCK_MAP = new ConcurrentHashMap<>();

    private final String uniqueName;

    private final ReentrantLock reentrantLock;

    public MemoryLockImpl(String uniqueName) {
        if (StringUtils.isEmpty(uniqueName)) {
            throw new IllegalArgumentException("uniqueName can not be empty");
        }
        this.uniqueName = uniqueName;
        // 使用computeIfAbsent确保同一uniqueName获取到同一个锁实例
        this.reentrantLock = LOCK_MAP.computeIfAbsent(uniqueName, k -> new ReentrantLock());
    }

    @Override
    public void lock() {
        reentrantLock.lock();
    }

    @Override
    public boolean unlock() {
        if (reentrantLock.isHeldByCurrentThread()) {
            reentrantLock.unlock();
            return true;
        }
        return false;
    }

    @Override
    public boolean tryLock() {
        return reentrantLock.tryLock();
    }

    @Override
    public boolean tryLock(long millsSecond) {
        try {
            return reentrantLock.tryLock(millsSecond, TimeUnit.MILLISECONDS);
        } catch (InterruptedException e) {
            LOGGER.warn("try lock interrupted, name: {}", uniqueName, e);
            Thread.currentThread().interrupt();
            return false;
        }
    }

    @Override
    public boolean locked() {
        return reentrantLock.isLocked();
    }

    @Override
    public String name() {
        return uniqueName;
    }
}
