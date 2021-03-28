package com.tomhurry.dynamic.datasource.sniffer.director.model;

import com.baomidou.mybatisplus.extension.activerecord.Model;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 操作信息表
 * </p>
 *
 * @author taozhi
 * @since 2021-01-14
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class TaskInf extends Model<TaskInf> {

    private static final long serialVersionUID=1L;

    /**
     * 主键
     */
    private String id;

    /**
     * 操作类型
     */
    private String action;

    /**
     * 操作参数
     */
    private String params;

    /**
     * 0：创建 1：已分发 2：已确认
     */
    private String status;

    /**
     * 创建时间
     */
    private Long createTime;

    /**
     * 账号编号
     */
    private String accountId;

    /**
     * DOMAIN_ID
     */
    private String domainId;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
