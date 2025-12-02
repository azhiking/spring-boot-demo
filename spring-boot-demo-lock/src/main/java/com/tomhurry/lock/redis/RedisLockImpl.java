package com.tomhurry.lock.redis;

import com.tomhurry.lock.Lock;

/**
 * redis distributed lock
 *
 * @author taozhi
 * @date 2024/9/26 22:04
 * @since 1.0.0
 */
public class RedisLockImpl implements Lock {


    public RedisLockImpl(String uniqueName) {
    }

    @Override
    public void lock() {

    }

    @Override
    public boolean unlock() {
        return false;
    }

    @Override
    public boolean tryLock() {
        return false;
    }

    @Override
    public boolean tryLock(long millsSecond) {
        return false;
    }

    @Override
    public boolean locked() {
        return false;
    }

    @Override
    public String name() {
        return "";
    }
}
