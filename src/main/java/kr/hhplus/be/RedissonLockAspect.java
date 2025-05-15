package kr.hhplus.be;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.concurrent.TimeUnit;


@Aspect
@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class RedissonLockAspect {
    private final RedissonClient redissonClient;

    private static final String LOCK_PREFIX = ":";

    public RedissonLockAspect(RedissonClient redissonClient) {
        this.redissonClient = redissonClient;
    }

    @Around("@annotation(kr.hhplus.be.RedissonLock)")
    public Object redissonLock(final ProceedingJoinPoint joinPoint) throws Throwable {
        MethodSignature signature = (MethodSignature)  joinPoint.getSignature();
        Method method = signature.getMethod();
        RedissonLock annotation = method.getAnnotation(RedissonLock.class);

        String lockKey =  LOCK_PREFIX + CustomSpringELParser.getDynamicValue(signature.getParameterNames(), joinPoint.getArgs(), annotation.value());
        RLock lock = redissonClient.getLock(lockKey);

        try {
            boolean lockable = lock.tryLock(annotation.waitTime(), annotation.leaseTime(), TimeUnit.SECONDS);
            if(!lockable) {
                return false;
            }
            return joinPoint.proceed();
        }
        catch (InterruptedException e) {
            throw new InterruptedException();
        }
        finally {
            lock.unlock();
        }
    }
}
