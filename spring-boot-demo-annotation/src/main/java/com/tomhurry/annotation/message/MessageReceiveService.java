package com.tomhurry.annotation.message;

import com.tomhurry.annotation.model.AgentMessageRouter;
import com.tomhurry.annotation.model.MqMessage;
import com.tomhurry.annotation.model.StorageMessageRouter;
import com.tomhurry.annotation.model.VirtMessageRouter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author taozhi
 * @date 2020/10/13
 * @since 1.0.0
 */
@Slf4j
@Service
public class MessageReceiveService {
    @Resource
    private AgentMessageRouter agentMessageRouter;
    @Resource
    private StorageMessageRouter storageMessageRouter;
    @Resource
    private VirtMessageRouter virtMessageRouter;

    public void receiveFromAgent(MqMessage mqMessage) {
        agentMessageRouter.route(mqMessage);
    }

    public void receiveFromStorage(MqMessage mqMessage) {
        storageMessageRouter.route(mqMessage);
    }

    public void receiveFromVirt(MqMessage mqMessage) {
        virtMessageRouter.route(mqMessage);
    }
}
