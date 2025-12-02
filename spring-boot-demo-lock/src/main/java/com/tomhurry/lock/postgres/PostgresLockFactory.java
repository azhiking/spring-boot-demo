package com.tomhurry.lock.postgres;

import com.tomhurry.lock.Lock;
import com.tomhurry.lock.LockFactory;
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
public class PostgresLockFactory implements LockFactory {

    @Override
    public Lock getLock(String uniqueName) {
        return new PostgresLockImpl(uniqueName);
    }
}
