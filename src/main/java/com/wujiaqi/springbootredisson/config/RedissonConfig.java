package com.wujiaqi.springbootredisson.config;

import lombok.extern.slf4j.Slf4j;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author WJQ
 */
@Configuration
@Slf4j
public class RedissonConfig {


    @Bean(destroyMethod = "shutdown")
    public RedissonClient getRedissonClient() {
        log.info(">>>>>>>>>>>>> init RedissonClient is running <<<<<<<<<<<<<");
        Config config = new Config();
        config.useSingleServer().setAddress("redis://127.0.0.1:6379");
        return Redisson.create(config);
    }

}
