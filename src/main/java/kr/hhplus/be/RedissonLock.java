package kr.hhplus.be;

import java.lang.annotation.*;

@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface RedissonLock {

    String value(); // Lock의 이름 (고유값)
    long waitTime() default 5L; // Lock획득을 시도하는 최대 시간 (ms)
    long leaseTime() default 2L; // 락을 획득한 후, 점유하는 최대 시간 (ms)

}