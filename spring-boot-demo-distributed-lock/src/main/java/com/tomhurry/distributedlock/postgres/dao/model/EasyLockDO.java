package com.tomhurry.distributedlock.postgres.dao.model;

import com.baomidou.mybatisplus.annotation.TableName;
import com.tomhurry.distributedlock.common.Constants;
import lombok.Data;
import org.springframework.util.ObjectUtils;

import java.util.Optional;

/**
 * easy lock do
 *
 * @author taozhi
 * @date 2024/9/26 20:02
 * @since 1.0.0
 */
@Data
@TableName("easy_lock")
public class EasyLockDO {

    /**
     * id
     */
    private Integer id;

    /**
     * 锁对象key
     */
    private String key;

    /**
     * 锁持有者
     */
    private String owner;

    /**
     * 锁重入次数
     */
    private Integer reentrantCount;

    /**
     * 锁超时时间，单位毫秒
     */
    private Long timeout;

    /**
     * 锁创建时间，单位毫秒
     */
    private Long createTime;

    /**
     * 锁更新时间，单位毫秒
     */
    private Long updateTime;

    public static EasyLockDO of(EasyLockDTO easyLockDTO) {
        if (easyLockDTO == null
                || ObjectUtils.isEmpty(easyLockDTO.getKey())
                || ObjectUtils.isEmpty(easyLockDTO.getOwner())
                || ObjectUtils.isEmpty(easyLockDTO.getReentrantCount())
                || easyLockDTO.getReentrantCount() <= 0) {
            return null;
        }
        EasyLockDO easyLockDO = new EasyLockDO();
        easyLockDO.setKey(easyLockDTO.getKey());
        easyLockDO.setOwner(easyLockDTO.getOwner());
        easyLockDO.setReentrantCount(easyLockDTO.getReentrantCount());
        easyLockDO.setTimeout(Optional.ofNullable(easyLockDTO.getTimeout()).orElse(Constants.DEFAULT_LOCK_TIMEOUT));
        return easyLockDO;
    }
}
