package com.tomhurry.protobuf.common;

import org.thingsboard.server.gen.transport.TransportProtos;

/**
 * @author taozhi
 * @date 2020/11/23
 * @since 1.0.0
 */
public interface TbServiceInfoProvider {

    String getServiceId();

    TransportProtos.ServiceInfo getServiceInfo();

    boolean isService(String serviceType);

}
