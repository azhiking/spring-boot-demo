package com.tomhurry.dynamic.datasource.sniffer.engine.model;

import com.baomidou.mybatisplus.extension.activerecord.Model;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 业务接收记录
 * </p>
 *
 * @author taozhi
 * @since 2021-01-14
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class ServiceReceive extends Model<ServiceReceive> {

    private static final long serialVersionUID=1L;

    /**
     * 主键
     */
    private String id;

    /**
     * 操作类型：
     */
    private String type;

    /**
     * 内容
     */
    private String content;

    /**
     * DOMAIN_ID
     */
    private String domainId;

    /**
     * 创建时间
     */
    private Long createTime;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
