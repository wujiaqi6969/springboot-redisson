package com.wujiaqi.springbootredisson.common;

import com.wujiaqi.springbootredisson.anno.LockStatus;
import lombok.Data;

import org.redisson.api.RLock;

/**
 * @author WJQ
 */
@Data
public class LockResult {

    private LockStatus lockStatus;

    private RLock rLock;

}
