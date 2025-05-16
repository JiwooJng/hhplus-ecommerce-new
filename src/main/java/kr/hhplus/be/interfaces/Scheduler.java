package kr.hhplus.be.interfaces;

import kr.hhplus.be.CouponRedisService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class Scheduler {
    private static final int BATCH_SIZE = 30;

    private CouponRedisService couponRedisService;

    @Scheduled(fixedRate = 2000)
    public void processIssueCoupon() {
        couponRedisService.issueProcessBatch(BATCH_SIZE);
    }
}
