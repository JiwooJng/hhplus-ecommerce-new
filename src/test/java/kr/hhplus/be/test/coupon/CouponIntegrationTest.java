package kr.hhplus.be.test.coupon;

import jakarta.persistence.NoResultException;
import kr.hhplus.be.domain.coupon.CouponService;
import kr.hhplus.be.domain.coupon.entity.Coupon;
import kr.hhplus.be.domain.coupon.enumtype.CouponType;
import kr.hhplus.be.domain.coupon.repository.CouponRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import org.testcontainers.junit.jupiter.Testcontainers;
import static org.assertj.core.api.Assertions.assertThat;
import java.math.BigDecimal;
import java.util.Optional;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


@SpringBootTest
@Testcontainers
public class CouponIntegrationTest {
    @Autowired
    CouponService couponService;
    @Autowired
    CouponRepository couponRepository;

    @Test
    @Transactional
    void 쿠폰_발급_성공() {
        Coupon coupon = new Coupon(CouponType.선착순_쿠폰, new BigDecimal("5000"), 10L);
        couponRepository.save(coupon);

        // 쿠폰 발급
        couponService.issue(coupon.getId(), 1L);

        // 쿠폰 발급 확인
        Coupon issuedCoupon = couponRepository.findById(coupon.getId());
        assertThat(issuedCoupon).isNotNull();
        assertThat(1L).isEqualTo(issuedCoupon.getIssuedAmount()); // 발급 수량이 1개여야 함
    }

    @Test
    void 선착순_쿠폰_발급_동시성_제어() throws InterruptedException {
        // Given
        Coupon coupon = new Coupon(CouponType.선착순_쿠폰, new BigDecimal("5000"), 10L);
        couponRepository.save(coupon);

        int threadCount = 5;
        ExecutorService executorService = Executors.newFixedThreadPool(threadCount);
        CountDownLatch latch = new CountDownLatch(threadCount);

        // When
        for (int i = 0; i < threadCount; i++) {
            final long userId = i + 1;
            executorService.submit(() -> {
                try {
                    couponService.increaseIssuedAmount(coupon.getId(), userId);
                } catch (Exception e) {
                    System.out.println("Error: " + e.getMessage());
                } finally {
                    latch.countDown();
                }
            });
        }
        latch.await(); // 모든 스레드가 작업을 완료할 때까지 대기

        // Then
        // 쿠폰 발급 결과 확인
        Optional<Coupon> updateCoupon = couponRepository.findByPessimisticLock(coupon.getId());
        assertThat(updateCoupon).isPresent(); // 쿠폰이 존재해야 함
        assertThat(5L).isEqualTo(updateCoupon.get().getIssuedAmount()); // 쿠폰 발급 수량이 5개여야 함

    }

}
