package com.tomhurry.distributedlock.redis;

import com.tomhurry.distributedlock.DistributedLock;

/**
 * redis distributed lock
 *
 * @author taozhi
 * @date 2024/9/26 22:04
 * @since 1.0.0
 */
public class RedisDistributedLockImpl implements DistributedLock {


    public RedisDistributedLockImpl(String uniqueName) {
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
