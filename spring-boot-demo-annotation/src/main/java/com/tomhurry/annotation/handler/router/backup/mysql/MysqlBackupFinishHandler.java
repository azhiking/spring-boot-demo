package com.tomhurry.annotation.handler.router.backup.mysql;

import com.tomhurry.annotation.annotation.RuleNode;
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
@RuleNode(action = {"fbackup", "ibackup"}, object = "mysql", type = "storage", status = "4",
        before = {BackupFailedRetryJobHandler.class}, after = {ProcessQueueJobHandler.class})
public class MysqlBackupFinishHandler implements MessageHandler {
    @Override
    public void handle(MqMessage message) throws Exception {
        log.info("这是MySQL备份存储完成handler:{}",message.toString());
    }
}
