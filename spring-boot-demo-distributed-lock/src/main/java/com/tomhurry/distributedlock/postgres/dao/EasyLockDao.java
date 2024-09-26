package com.tomhurry.distributedlock.postgres.dao;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.tomhurry.distributedlock.common.Constants;
import com.tomhurry.distributedlock.postgres.dao.mapper.EasyLockMapper;
import com.tomhurry.distributedlock.postgres.dao.model.EasyLockDO;
import com.tomhurry.distributedlock.postgres.dao.model.EasyLockDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * easy lock dao
 *
 * @author taozhi
 * @date 2024/9/26 19:57
 * @since 1.0.0
 */
@Service
public class EasyLockDao {

    public static final String INSTANCE_FLAG = String.valueOf(System.currentTimeMillis());
    private static final Logger LOGGER = LoggerFactory.getLogger(EasyLockDao.class);
    private final ScheduledExecutorService scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
    @Resource
    private EasyLockMapper easyLockMapper;

    @PostConstruct
    public void init() {
        scheduledExecutorService.scheduleAtFixedRate(new CleanLockTask(), 1, 10, TimeUnit.MINUTES);
    }

    /**
     * add lock
     *
     * @param easyLockDTO lock dto
     * @return add result
     */
    public boolean add(EasyLockDTO easyLockDTO) {
        EasyLockDO easyLockDO = EasyLockDO.of(easyLockDTO);
        if (easyLockDO == null) {
            return false;
        }
        easyLockDO.setCreateTime(System.currentTimeMillis());
        easyLockDO.setUpdateTime(System.currentTimeMillis());
        return easyLockMapper.insert(easyLockDO) > 0;
    }

    /**
     * delete lock by key
     *
     * @param lockKey lock key
     * @return delete result
     */
    public boolean deleteByKey(String lockKey) {
        return easyLockMapper.delete(new LambdaQueryWrapper<EasyLockDO>().eq(EasyLockDO::getKey, lockKey)) > 0;
    }

    /**
     * update lock by EasyLockDTO
     *
     * @param easyLockDTO lock dto
     * @return update result
     */
    public boolean update(EasyLockDTO easyLockDTO) {
        EasyLockDO easyLockDO = EasyLockDO.of(easyLockDTO);
        if (easyLockDO == null) {
            return false;
        }
        easyLockDO.setUpdateTime(System.currentTimeMillis());
        return easyLockMapper.update(easyLockDO, new LambdaQueryWrapper<EasyLockDO>().eq(EasyLockDO::getKey, easyLockDTO.getKey())) > 0;
    }

    /**
     * get lock by key
     *
     * @param lockKey lock key
     * @return lock dto
     */
    public EasyLockDTO getByKey(String lockKey) {
        EasyLockDO easyLockDO = easyLockMapper.selectOne(new LambdaQueryWrapper<EasyLockDO>().eq(EasyLockDO::getKey, lockKey));
        return EasyLockDTO.of(easyLockDO);
    }

    /**
     * clean lock task
     */
    private final class CleanLockTask implements Runnable {
        @Override
        public void run() {
            //LOGGER.info("clean lock task start");
            try {
                List<EasyLockDO> easyLockDOList = easyLockMapper.selectList(new LambdaQueryWrapper<>());
                if (easyLockDOList != null && !easyLockDOList.isEmpty()) {
                    for (EasyLockDO easyLockDO : easyLockDOList) {
                        if (System.currentTimeMillis() - easyLockDO.getUpdateTime() > Optional.ofNullable(easyLockDO.getTimeout()).orElse(Constants.DEFAULT_LOCK_TIMEOUT)) {
                            easyLockMapper.deleteById(easyLockDO.getId());
                            LOGGER.info("clean lock task, delete lock:{}", easyLockDO);
                        }
                    }
                }

            } catch (Exception e) {
                LOGGER.error("clean lock task error", e);
            }
            //LOGGER.info("clean lock task end");
        }
    }
}
