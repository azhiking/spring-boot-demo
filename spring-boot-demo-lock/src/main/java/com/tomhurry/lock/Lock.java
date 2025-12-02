package com.tomhurry.lock;

/**
 * distributed lock
 *
 * @author TaoZhi
 * @date 2024/9/26 19:49
 * @since 1.0.0
 */
public interface Lock {

    void lock();

    boolean unlock();

    boolean tryLock();

    boolean tryLock(long millsSecond);

    boolean locked();

    String name();
}
