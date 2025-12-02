package com.tomhurry.lock.memory;

import com.tomhurry.lock.Lock;
import com.tomhurry.lock.LockFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

/**
 *
 *
 * @author taozhi
 * @date 2025/11/7 15:53
 * @since 1.0.0
 */
@Qualifier("memory")
@Component
public class MemoryLockFactory implements LockFactory {

    @Override
    public Lock getLock(String uniqueName) {
        return new MemoryLockImpl(uniqueName);
    }
}
