package com.tomhurry.distributedlock.postgres;

import com.tomhurry.distributedlock.postgres.dao.EasyLockDao;
import com.tomhurry.distributedlock.postgres.dao.model.EasyLockDTO;
import com.tomhurry.util.SpringUtil;
import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.locks.AbstractQueuedSynchronizer;

/**
 * sync helper
 *
 * @author taozhi
 * @date 2024/9/26 19:54
 * @since 1.0.0
 */
public class LockSyncHelper extends AbstractQueuedSynchronizer {

    private static final Logger LOGGER = LoggerFactory.getLogger(LockSyncHelper.class);

    @Getter
    private final String uniqueName;

    private final EasyLockDao easyLockDao;

    public LockSyncHelper(String uniqueName) {
        this.uniqueName = uniqueName;
        this.easyLockDao = SpringUtil.getBean(EasyLockDao.class);
    }

    @Override
    protected boolean tryAcquire(int acquires) {
        boolean acquire;
        try {
            EasyLockDTO easyLockDTO = getLock();
            acquire = (hasLocked(easyLockDTO) && incrLock(easyLockDTO, acquires)) || (noLock(easyLockDTO) && addLock());
        } catch (Exception e) {
            LOGGER.warn("try acquire fail, name: {}, owner: {}, cause: {}", this.uniqueName, genLockOwner(), e.getMessage());
            acquire = false;
        }
        LOGGER.warn("try acquire success: {}, name: {}, owner: {}", acquire, this.uniqueName, genLockOwner());
        return acquire;
    }

    @Override
    protected boolean tryRelease(int releases) {
        EasyLockDTO easyLockDTO = getLock();
        if (noLock(easyLockDTO) || !hasLocked(easyLockDTO)) {
            return false;
        }
        boolean free = null == easyLockDTO.getReentrantCount() || releases >= easyLockDTO.getReentrantCount();
        boolean release = free ? deleteLock() : decrLock(easyLockDTO, releases);
        LOGGER.warn("try release success: {}, name: {}, owner: {}", release, this.uniqueName, genLockOwner());
        return free && release;
    }

    public boolean canRelease() {
        return hasLocked(getLock());
    }

    private EasyLockDTO getLock() {
        return easyLockDao.getByKey(this.uniqueName);
    }

    private boolean noLock(EasyLockDTO easyLockDTO) {
        return null == easyLockDTO;
    }

    private boolean addLock() {
        return easyLockDao.add(new EasyLockDTO(this.uniqueName, genLockOwner(), 1));
    }

    private boolean hasLocked(EasyLockDTO easyLockDTO) {
        return null != easyLockDTO && genLockOwner().equals(easyLockDTO.getOwner());
    }

    private boolean incrLock(EasyLockDTO easyLockDTO, int acquires) {
        easyLockDTO.setReentrantCount(easyLockDTO.getReentrantCount() + acquires);
        return easyLockDao.update(easyLockDTO);
    }

    private boolean decrLock(EasyLockDTO easyLockDTO, int releases) {
        easyLockDTO.setReentrantCount(easyLockDTO.getReentrantCount() - releases);
        return easyLockDao.update(easyLockDTO);
    }

    private boolean deleteLock() {
        return easyLockDao.deleteByKey(this.uniqueName);
    }

    private String genLockOwner() {
        return String.format("%s-%s", EasyLockDao.INSTANCE_FLAG, Thread.currentThread().getId());
    }
}
