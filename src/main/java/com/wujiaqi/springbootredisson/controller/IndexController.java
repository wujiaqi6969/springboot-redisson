package com.wujiaqi.springbootredisson.controller;

import com.wujiaqi.springbootredisson.anno.LockAnno;
import com.wujiaqi.springbootredisson.anno.LockManager;
import com.wujiaqi.springbootredisson.common.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.TimeUnit;


/**
 * @author WJQ
 */
@RestController
@Slf4j
public class IndexController {

    @GetMapping("index")
    public Result<Void> index() {
        return Result.success();

    }

    @Autowired
    private StringRedisTemplate redisTemplate;

    @GetMapping("save")
    @LockAnno(value = "IndexController:save", expireTime = 10, waitTime = 5)
    public Result<Void> save() {
        log.warn("IndexController save method is running!");
//        for (int i = 0; i < 3; i++) {
//            log.warn("IndexController save method is wait!");
//            try {
//                TimeUnit.SECONDS.sleep(1);
//            } catch (InterruptedException e) {
//                throw new RuntimeException(e);
//            }
//        }
        log.warn("IndexController save method is over!");
        return Result.success();
    }

    @GetMapping("update")
    public Result<Void> update() {
        log.warn("IndexController update method is running!");
        redisTemplate.opsForHash().put("lock:IndexController:update", "lock:IndexController:update", "value");
        log.warn("IndexController save method is over!");
        return Result.success();
    }


    /**
     * 模拟处理业务逻辑时间很长,测试{@linkplain LockManager#getLockResult}方法无法获取到锁,返回false
     *
     * @return result
     */
    @GetMapping("other")
    @LockAnno(value = "IndexController:save", expireTime = 10, waitTime = 5)
    public Result<Void> getOther() {
        try {
            TimeUnit.MINUTES.sleep(2);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return Result.success();
    }

}
