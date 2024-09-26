package com.tomhurry.distributedlock.postgres.dao.model;

import com.tomhurry.distributedlock.common.Constants;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import java.util.Optional;

/**
 * easy lock dto
 *
 * @author taozhi
 * @date 2024/9/26 20:15
 * @since 1.0.0
 */
@Data
@NoArgsConstructor
public class EasyLockDTO {

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

    public EasyLockDTO(String key, String owner, Integer reentrantCount) {
        this.key = key;
        this.owner = owner;
        this.reentrantCount = reentrantCount;
        this.timeout = Constants.DEFAULT_LOCK_TIMEOUT;
    }

    public EasyLockDTO(String key, String owner, Integer reentrantCount, Long timeout) {
        this.key = key;
        this.owner = owner;
        this.reentrantCount = reentrantCount;
        this.timeout = timeout;
    }

    public static EasyLockDTO of(EasyLockDO easyLockDO) {
        if (easyLockDO == null
                || ObjectUtils.isEmpty(easyLockDO.getKey())
                || StringUtils.isEmpty(easyLockDO.getOwner())
                || ObjectUtils.isEmpty(easyLockDO.getReentrantCount())
                || easyLockDO.getReentrantCount() <= 0) {
            return null;
        }
        return new EasyLockDTO(easyLockDO.getKey(), easyLockDO.getOwner(), easyLockDO.getReentrantCount(), Optional.ofNullable(easyLockDO.getTimeout()).orElse(Constants.DEFAULT_LOCK_TIMEOUT));
    }
}
