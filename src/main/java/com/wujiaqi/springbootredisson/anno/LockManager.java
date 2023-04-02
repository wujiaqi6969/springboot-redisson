package com.wujiaqi.springbootredisson.anno;

import com.wujiaqi.springbootredisson.common.LockResult;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * @author WJQ
 */
@Component
@Slf4j
public class LockManager {


    @Autowired
    private RedissonClient redissonClient;


    public LockResult getLockResult(String key, Long expireTime, long waitTime, TimeUnit timeUnit) {
        LockResult result = new LockResult();

        //尝试获取锁
        RLock lock = redissonClient.getLock("lock:" + key);
        try {
            if (lock.tryLock(waitTime, expireTime, timeUnit)) {
                log.warn(">>>>>>>>>>>>>获取到了锁<<<<<<<<<<<<<");
                result.setLockStatus(LockStatus.SUCCESS);
                result.setRLock(lock);
            } else {
                log.warn(">>>>>>>>>>>>>等待时间已过,没有获取到锁<<<<<<<<<<<<<");
                result.setLockStatus(LockStatus.FAILED);
            }
        } catch (InterruptedException e) {
            log.info("LockManager方法中断异常!", e);
            result.setLockStatus(LockStatus.ERROR);
            lock.unlock();
        }


        return result;
    }

    public void unLock(RLock rLock) {
        try {
            rLock.unlock();
        } catch (Exception e) {
            log.error("解锁失败!", e);
        }
    }

}
