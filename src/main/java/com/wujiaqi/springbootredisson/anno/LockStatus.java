package com.wujiaqi.springbootredisson.anno;

import lombok.Getter;

/**
 * @author WJQ
 */
@Getter
public enum LockStatus {

    /**
     *成功
     */
    SUCCESS,

    /**
     * 错误
     */
    FAILED,

    /**
     * 异常
     */
    ERROR

}
