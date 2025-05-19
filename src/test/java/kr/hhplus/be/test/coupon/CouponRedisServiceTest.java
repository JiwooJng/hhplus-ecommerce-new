package kr.hhplus.be.test.coupon;

import kr.hhplus.be.domain.coupon.CouponService;
import kr.hhplus.be.domain.coupon.repository.CacheRepository;
import kr.hhplus.be.domain.coupon.repository.CouponRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;


@SpringBootTest
@Testcontainers
public class CouponRedisServiceTest {

    @Autowired
    private CouponService couponService;
    @Autowired
    private CouponRepository couponRepository;
    @Autowired
    private CacheRepository cacheRepository;

    private final String COUPON_WAITING_KEY = "coupon:waiting";
    private final Long couponId = 1L;
    private final Long userId = 1L;
    String value = String.format("%s:%s", couponId, userId);

    @Test
    public void 쿠폰발급요청_대기열에_없는_요청이면_추가_성공() {
        boolean isExist = cacheRepository.exists(COUPON_WAITING_KEY, value);
        if (isExist) {
            cacheRepository.removeFromSet(COUPON_WAITING_KEY, value);
        }
        boolean isAdded = couponService.requestIssueCoupon(couponId, userId);

        assertThat(isAdded).isTrue();

    }

    @Test
    public void 쿠폰발급요청_대기열에_존재하는_요청이면_추가_실패() {
        boolean isExist = cacheRepository.exists(COUPON_WAITING_KEY, value);
        if (!isExist) {
            cacheRepository.saveIfAbsent(COUPON_WAITING_KEY, value, 0);
        }
        boolean isAdded = couponService.requestIssueCoupon(couponId, userId);

        assertThat(isAdded).isFalse();

    }

}
