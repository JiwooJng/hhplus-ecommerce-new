package kr.hhplus.be;

import kr.hhplus.be.domain.coupon.entity.Coupon;
import kr.hhplus.be.domain.coupon.entity.UserCoupon;
import kr.hhplus.be.domain.coupon.enumtype.CouponType;
import kr.hhplus.be.domain.coupon.repository.CouponRepository;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Set;

@Service
public class CouponRedisService {
    private static final String COUPON_KEY = "coupon";
    private static final String COUPON_WAITING_KEY = "coupon:waiting";

    private StringRedisTemplate redisTemplate;
    private CouponRepository couponRepository;

    public CouponRedisService(StringRedisTemplate redisTemplate, CouponRepository couponRepository) {
        this.redisTemplate = redisTemplate;
        this.couponRepository = couponRepository;
    }

    public void publishCouponRedis(CouponType couponType, Long amount) {
        String couponKey = COUPON_KEY + ":" + couponType;
        redisTemplate.opsForValue().set(couponKey, String.valueOf(amount));
    }

    public boolean requestIssueCoupon(Long couponId, Long userId) {
        String value = String.format("%s:%s", couponId, userId); // couponId:userId
        double score = Instant.now().toEpochMilli(); // 요청 시간
        // Redis에 쿠폰 발급 요청을 저장하는 로직
        Boolean isAdded = redisTemplate.opsForZSet().addIfAbsent(COUPON_WAITING_KEY, value, score);
        return Boolean.TRUE.equals(isAdded);
    }

    public Set<String> fetchFromZSet(int batchSize) {
        // 0부터 limit-1까지의 사용자 ID 가져오기;
        return redisTemplate.opsForZSet().range(COUPON_WAITING_KEY, 0, batchSize - 1);
    }

    public boolean issueCoupon(Long couponId) {
        String couponKey = COUPON_KEY + ":" + couponId;
        Long remainCouponAmount = redisTemplate.opsForValue().decrement(couponKey);
        return remainCouponAmount >= 0;
    }

    public void issueProcessBatch(int batchSize) {
        Set<String> users = fetchFromZSet(batchSize);
        // 쿠폰 발급
        for (String requestUserCoupon : users) {
            // "userId:couponId" 형식에서 분리
            String[] parts = requestUserCoupon.split(":");
            if (parts.length != 2) {
                continue;
            }
            Long couponId = Long.parseLong(parts[0]);
            Long userId = Long.parseLong(parts[1]);

            Coupon coupon = couponRepository.findById(couponId);
            // 쿠폰 수량 차감
            boolean successIssue = issueCoupon(couponId);
            if (successIssue) {
                // 데이터베이스에 발급 내역 저장
                UserCoupon userCoupon = new UserCoupon(userId, coupon);
                couponRepository.saveUserCoupon(userCoupon);

                // ZSet에서 사용자 제거
                redisTemplate.opsForZSet().remove(COUPON_WAITING_KEY, requestUserCoupon);
            } else {
                // 쿠폰 남은 수량이 부족하면 처리 중단
                break;
            }
        }
    }
}
