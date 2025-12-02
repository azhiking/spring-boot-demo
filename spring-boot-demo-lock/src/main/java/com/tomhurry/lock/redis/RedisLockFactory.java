package com.tomhurry.lock.redis;

import com.tomhurry.lock.LockFactory;
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
public class RedisLockFactory implements LockFactory {

    @Override
    public RedisLockImpl getLock(String uniqueName) {
        return new RedisLockImpl(uniqueName);
    }
}
