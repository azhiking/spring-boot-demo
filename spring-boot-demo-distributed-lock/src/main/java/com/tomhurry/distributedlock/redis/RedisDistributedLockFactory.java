package com.tomhurry.distributedlock.redis;

import com.tomhurry.distributedlock.DistributedLockFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

/**
 * redis distributed lock factory
 *
 * @author taozhi
 * @date 2024/9/26 22:11
 * @since 1.0.0
 */
@Qualifier("redis")
@Component
public class RedisDistributedLockFactory implements DistributedLockFactory {

    @Override
    public RedisDistributedLockImpl getLock(String uniqueName) {
        return new RedisDistributedLockImpl(uniqueName);
    }
}
