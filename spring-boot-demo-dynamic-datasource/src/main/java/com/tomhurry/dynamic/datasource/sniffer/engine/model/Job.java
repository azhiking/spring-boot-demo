package com.tomhurry.dynamic.datasource.sniffer.engine.model;

import com.baomidou.mybatisplus.extension.activerecord.Model;

import java.io.Serializable;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * job记录
 * </p>
 *
 * @author taozhi
 * @since 2021-01-14
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class Job extends Model<Job> {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    private String id;

    /**
     * 名称
     */
    private String name;

    /**
     * 客户端ID
     */
    private String backupAgentId;

    /**
     * 客户端名称
     */
    private String backupAgentName;

    /**
     * 执行类型：auto，manual
     */
    private String runType;

    /**
     * 动作类型：backup，discover，mount，restore，umount，clone，archive，save_image，delete_image
     */
    private String actionType;

    /**
     * 作业类型：fbackup，ibackup，log
     */
    private String backupType;

    /**
     * 业务ID：备份策略ID、数据使用记录ID
     */
    private String serviceId;

    /**
     * 数据类型：oracle ，vmware
     */
    private String serviceType;

    /**
     * 策略名称
     */
    private String policyName;

    /**
     * 备份对象ID
     */
    private String backupObjectId;

    /**
     * 任务内容
     */
    private String content;

    /**
     * 使用容量
     */
    private Long usedCapacity;

    /**
     * 进度
     */
    private Integer finishRate;

    /**
     * 状态：1 成功，2 失败，3 运行中
     */
    private String status;

    /**
     * 开始时间，毫秒数
     */
    private Long beginTime;

    /**
     * 结束时间，毫秒数
     */
    private Long endTime;

    /**
     * 克隆卷名
     */
    private String volumeName;

    /**
     * 存储池ID
     */
    private String storagePoolId;

    /**
     * 存储池名
     */
    private String storagePoolName;

    /**
     * 当前所属位置：init_storage,agent,release_storage
     */
    private String position;

    /**
     * 错误码
     */
    private Integer errorCode;

    /**
     * 错误信息
     */
    private String errorMessage;

    /**
     * 扩展信息
     */
    private String extendedInfo;

    /**
     * DOMAIN_ID
     */
    private String domainId;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
