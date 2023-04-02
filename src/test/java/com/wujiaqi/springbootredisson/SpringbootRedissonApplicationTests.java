package com.wujiaqi.springbootredisson;

import org.junit.jupiter.api.Test;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.StringRedisTemplate;

import java.util.concurrent.TimeUnit;

@SpringBootTest
class SpringbootRedissonApplicationTests {

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Autowired
    private RedissonClient redissonClient;


    @Test
    void contextLoads() {
        System.out.println("redisTemplate = " + redisTemplate);
        redisTemplate.opsForValue().set("wujiaqi", "wujiaqi", 1, TimeUnit.DAYS);
        System.out.println("redissonClient = " + redissonClient);

        RLock lock = redissonClient.getLock("lock");
        try {
            if (lock.tryLock(5, 5, TimeUnit.MINUTES)) {
                System.out.println("AA三扥as扥年asl");
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }

    }

}
