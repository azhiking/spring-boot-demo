package com.tomhurry.distributedlock;

/**
 * distributed lock factory
 *
 * @author TaoZhi
 * @date 2024/9/26 19:48
 * @since 1.0.0
 */
public interface DistributedLockFactory {

    DistributedLock getLock(String uniqueName);

}
