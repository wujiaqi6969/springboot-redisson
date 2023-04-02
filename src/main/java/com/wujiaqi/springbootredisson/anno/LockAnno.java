package com.wujiaqi.springbootredisson.anno;

import java.lang.annotation.*;
import java.util.concurrent.TimeUnit;

/**
 * @author WJQ
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface LockAnno {

    /**
     * 存储到redis中的key
     *
     * @return string
     */
    String value();

    /**
     * 锁过期时间
     * @return long
     */
    long expireTime() default 5;


    /**
     * 获取锁等待时间
     */
    long waitTime() default 10;



    /**
     * 时间单位
     */
    TimeUnit getTimeUnit() default TimeUnit.SECONDS;
}
