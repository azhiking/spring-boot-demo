package com.tomhurry.distributedlock;

/**
 * distributed lock
 *
 * @author TaoZhi
 * @date 2024/9/26 19:49
 * @since 1.0.0
 */
public interface DistributedLock {

    void lock();

    boolean unlock();

    boolean tryLock();

    boolean tryLock(long millsSecond);

    boolean locked();

    String name();
}
