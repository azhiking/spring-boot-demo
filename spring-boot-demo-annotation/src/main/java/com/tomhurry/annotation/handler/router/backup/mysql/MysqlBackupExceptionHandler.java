package com.tomhurry.annotation.handler.router.backup.mysql;

import com.tomhurry.annotation.annotation.MessageRouterMapping;
import com.tomhurry.annotation.handler.router.others.BackupFailedRetryJobHandler;
import com.tomhurry.annotation.handler.router.others.ProcessQueueJobHandler;
import com.tomhurry.annotation.model.MessageHandler;
import com.tomhurry.annotation.model.MqMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author taozhi
 * @date 2020/10/13
 * @since 1.0.0
 */
@Slf4j
@Service
@MessageRouterMapping(action = {"fbackup", "ibackup"}, object = "mysql", type = "agent", status = "2",
        before = {BackupFailedRetryJobHandler.class}, after = {ProcessQueueJobHandler.class})
public class MysqlBackupExceptionHandler implements MessageHandler {
    @Override
    public void handle(MqMessage message) throws Exception {
        log.info("这是MySQL备份客户端异常handler:{}", message.toString());
    }
}
