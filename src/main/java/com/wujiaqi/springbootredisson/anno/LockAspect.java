package com.wujiaqi.springbootredisson.anno;

import com.wujiaqi.springbootredisson.common.LockResult;
import com.wujiaqi.springbootredisson.common.Result;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author WJQ
 */
@Component
@Slf4j
@Aspect
public class LockAspect {


    @Autowired
    private LockManager lockManager;

    /**
     * 定义一个切点
     */
    @Pointcut("@annotation(LockAnno)")
    public void pointcut() {
    }


    @Around("pointcut()")
    public Object aroundProcessor(ProceedingJoinPoint pjp) {
        //拿到方法签名
        MethodSignature methodSignature = (MethodSignature) pjp.getSignature();
        LockAnno lockAnno = methodSignature.getMethod().getAnnotation(LockAnno.class);
        Object result = null;
        log.info("当前线程Id:{}", Thread.currentThread().getId());
        LockResult lockResult = lockManager.getLockResult(lockAnno.value(), lockAnno.expireTime(), lockAnno.waitTime(), lockAnno.getTimeUnit());
        if (lockResult.getLockStatus().equals(LockStatus.SUCCESS)) {
            try {
                result = pjp.proceed();
            } catch (Throwable throwable) {
                log.error("切面方法异常!", throwable);
            } finally {
                if (lockResult.getRLock() != null) {
                    lockManager.unLock(lockResult.getRLock());
                }
            }
        } else {
            result = Result.fail(6, "业务处理失败,请重试!");
        }

        return result;
    }


}
