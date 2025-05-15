package kr.hhplus.be.test.coupon;

import kr.hhplus.be.CouponRedisService;
import kr.hhplus.be.domain.coupon.repository.CouponRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;


@SpringBootTest
@Testcontainers
public class CouponRedisServiceTest {

    @Autowired
    private CouponRedisService couponRedisService;
    @Autowired
    private CouponRepository couponRepository;
    @Autowired
    private StringRedisTemplate redisTemplate;

    private final String COUPON_WAITING_KEY = "coupon:waiting";

    @Test
    public void 쿠폰발급요청_대기열_추가_성공() {
        // given
        Long couponId = 1L;
        Long userId = 1L;

        String value = String.format("%s:%s", couponId, userId);

        Double score = redisTemplate.opsForZSet().score(COUPON_WAITING_KEY, value);
        if (score != null) {
            redisTemplate.opsForZSet().remove(COUPON_WAITING_KEY, value);
        }
        boolean isAdded = couponRedisService.requestIssueCoupon(couponId, userId);

        assertThat(isAdded).isTrue();

    }

}
