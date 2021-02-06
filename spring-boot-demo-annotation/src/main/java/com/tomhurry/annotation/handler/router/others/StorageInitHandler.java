package com.tomhurry.annotation.handler.router.others;

import com.tomhurry.annotation.model.MessageHandler;
import com.tomhurry.annotation.model.MqMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author taozhi
 * @date 2020/10/14
 * @since 1.0.0
 */
@Slf4j
@Service
public class StorageInitHandler implements MessageHandler {
    @Override
    public void handle(MqMessage message) throws Exception {
        log.info("这是存储初始化完成handler");
    }
}
