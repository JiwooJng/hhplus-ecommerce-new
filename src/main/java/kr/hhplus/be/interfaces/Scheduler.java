package kr.hhplus.be.interfaces;

import kr.hhplus.be.domain.coupon.CouponService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class Scheduler {
    private static final long BATCH_SIZE = 30;

    private CouponService couponService;

    @Scheduled(fixedRate = 2000)
    public void processIssueCoupon() {
        couponService.issueProcessBatch(BATCH_SIZE);
    }
}
