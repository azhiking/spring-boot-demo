package com.tomhurry.annotation.model;

import org.springframework.stereotype.Service;

/**
 * @author taozhi
 * @date 2020/10/14
 * @since 1.0.0
 */
@Service
public class StorageMessageRouter extends MessageRouter {
    public Integer getRouterCount() {
        return this.getRules().size();
    }
}
