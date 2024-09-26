package com.tomhurry.distributedlock.postgres;

import com.tomhurry.distributedlock.DistributedLock;
import com.tomhurry.distributedlock.common.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

/**
 * distributed lock的postgres版本实现
 *
 * @author taozhi
 * @date 2024/9/26 19:51
 * @since 1.0.0
 */
public class PostgresDistributedLockImpl implements DistributedLock {

    private static final Logger LOGGER = LoggerFactory.getLogger(PostgresDistributedLockImpl.class);

    private final LockSyncHelper lockSyncHelper;

    public PostgresDistributedLockImpl(String uniqueName) {
        if (StringUtils.isEmpty(uniqueName)) {
            throw new IllegalArgumentException("uniqueName can not be empty");
        }
        this.lockSyncHelper = new LockSyncHelper(uniqueName);
    }

    @Override
    public void lock() {
        if (!tryLock(Constants.DEFAULT_LOCK_TIMEOUT)) {
            throw new RuntimeException("lock time out");
        }
    }

    @Override
    public boolean unlock() {
        return this.lockSyncHelper.release(1);
    }

    @Override
    public boolean tryLock() {
        return this.lockSyncHelper.tryAcquire(1);
    }

    @Override
    public boolean tryLock(long millsSecond) {
        try {
            return this.lockSyncHelper.tryAcquire(1) || this.lockSyncHelper.tryAcquireNanos(1, millsSecond * 1000 * 1000);
        } catch (Exception e) {
            LOGGER.warn("try lock fail, name: {}, cause: {}", this.lockSyncHelper.getUniqueName(), e.getMessage());
            return false;
        }
    }

    @Override
    public boolean locked() {
        return this.lockSyncHelper.canRelease();
    }

    @Override
    public String name() {
        return this.lockSyncHelper.getUniqueName();
    }
}
