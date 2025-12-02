package com.tomhurry.lock;

/**
 * distributed lock factory
 *
 * @author TaoZhi
 * @date 2024/9/26 19:48
 * @since 1.0.0
 */
public interface LockFactory {

    Lock getLock(String uniqueName);

}
