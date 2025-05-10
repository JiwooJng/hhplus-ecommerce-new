package kr.hhplus.be.test.coupon;

import kr.hhplus.be.domain.coupon.CouponService;
import kr.hhplus.be.domain.coupon.entity.Coupon;
import kr.hhplus.be.domain.coupon.enumtype.CouponType;
import kr.hhplus.be.domain.coupon.repository.CouponRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.math.BigDecimal;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Testcontainers
public class CouponRedissonTest {
    @Autowired
    CouponService couponService;

    @Autowired
    CouponRepository couponRepository;

    private Long couponId = 1L;

    @Test
    public void 선착순_쿠폰_발급_동시성_레디스_테스트() throws InterruptedException {
        // given
        Coupon coupon = new Coupon(CouponType.선착순_쿠폰, new BigDecimal("5000"), 10L);
        Coupon publishedCoupon = couponRepository.save(coupon);

        int threadCount = 20;
        ExecutorService executorService = Executors.newFixedThreadPool(threadCount);
        CountDownLatch latch = new CountDownLatch(threadCount);

        AtomicInteger issuedCount = new AtomicInteger();
        AtomicInteger errorCount = new AtomicInteger();

        for (int i = 0; i < threadCount; i++) {
            final long userId = i + 1;
            executorService.execute(() -> {
                try {
                    couponService.issueRedisson(publishedCoupon.getId(), userId);
                    issuedCount.incrementAndGet();
                } catch (Exception e) {
                    errorCount.incrementAndGet();
                    System.out.println("Error: " + e.getMessage());
                } finally {
                    latch.countDown();
                }
            });
        }

        latch.await();
        executorService.shutdown();

        Coupon updateCoupon = couponRepository.findById(coupon.getId());
        System.out.println("발급된 쿠폰 수량: " + updateCoupon.getIssuedAmount());
        assertThat(updateCoupon.getIssuedAmount()).isEqualTo(issuedCount.get());

    }
}
