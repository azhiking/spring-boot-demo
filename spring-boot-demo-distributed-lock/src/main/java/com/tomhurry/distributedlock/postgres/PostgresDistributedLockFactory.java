package com.tomhurry.distributedlock.postgres;

import com.tomhurry.distributedlock.DistributedLock;
import com.tomhurry.distributedlock.DistributedLockFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

/**
 * default distributed lock factory
 *
 * @author taozhi
 * @date 2024/9/26 21:25
 * @since 1.0.0
 */
@Qualifier("postgres")
@Component
public class PostgresDistributedLockFactory implements DistributedLockFactory {

    @Override
    public DistributedLock getLock(String uniqueName) {
        return new PostgresDistributedLockImpl(uniqueName);
    }
}
