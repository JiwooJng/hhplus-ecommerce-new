package kr.hhplus.be.domain.coupon;

import jakarta.persistence.NoResultException;
import kr.hhplus.be.RedissonLock;
import kr.hhplus.be.domain.coupon.entity.Coupon;
import kr.hhplus.be.domain.coupon.entity.UserCoupon;
import kr.hhplus.be.domain.coupon.enumtype.CouponType;
import kr.hhplus.be.domain.coupon.repository.CacheRepository;
import kr.hhplus.be.domain.coupon.repository.CouponRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Set;

@Service
public class CouponService {
    private static final String COUPON_KEY = "coupon";
    private static final String COUPON_WAITING_KEY = "coupon:waiting";

    private final CouponRepository couponRepository;
    private final CacheRepository cacheRepository;

    public CouponService(CouponRepository couponRepository, CacheRepository cacheRepository) {
        this.couponRepository = couponRepository;
        this.cacheRepository = cacheRepository;
    }

    public void publish(CouponType type, BigDecimal discountAmount, Long amount) {
        // 쿠폰 발행 로직
        Coupon coupon = new Coupon(type, discountAmount, amount);
        couponRepository.save(coupon);
    }

    public UserCoupon isExist(Long userId, Long couponId) {
        // 쿠폰 존재 여부 확인
        UserCoupon userCoupon = couponRepository.findUserCouponById(userId, couponId);
        if (userCoupon == null) {
            throw new RuntimeException("존재하지 않는 쿠폰입니다."); // 쿠폰이 존재하지 않음
        }
        return userCoupon;
    }

    // controller 에서 사용
    public UserCoupon use(Long userId, Long couponId) {
        // 쿠폰 사용 로직
        UserCoupon userCoupon = couponRepository.findUserCouponById(userId, couponId);
        userCoupon.use(); // 쿠폰 사용 처리

        return userCoupon; // 쿠폰 사용 성공
    }

    @Transactional
    public UserCoupon issue(Long couponId, Long userId) {
        // 쿠폰 발급 로직
        Coupon coupon = couponRepository.findById(couponId);
        if (coupon == null) {
            throw new IllegalArgumentException("Coupon not found");
        }
        coupon.issue(); // 쿠폰 발급 처리

        // 쿠폰 발급 처리
        return couponRepository.saveUserCoupon(new UserCoupon(userId, coupon)); // 쿠폰 발급 및 저장
    }
    @Transactional
    public void issueConcurrent(Long couponId, Long userId) {
        // 쿠폰 발급 수량 증가
        Coupon coupon = couponRepository.findByIdWithPessimisticLock(couponId)
                .orElseThrow(NoResultException::new);
        coupon.issue();
        couponRepository.saveUserCoupon(new UserCoupon(userId, coupon));

    }

    // Redisson Lock
    @RedissonLock(value = "#couponId")
    @Transactional
    public void issueRedisson(Long couponId, Long userId) {
        Coupon coupon = couponRepository.findById(couponId);
        coupon.issue();
        couponRepository.saveUserCoupon(new UserCoupon(userId, coupon));
    }

    // Cache
    public void publishCouponRedis(CouponType couponType, Long amount) {
        String couponKey = COUPON_KEY + ":" + couponType;
        cacheRepository.save(couponKey, String.valueOf(amount));
    }
    public boolean requestIssueCoupon(Long couponId, Long userId) {
        String value = String.format("%s:%s", couponId, userId); // couponId:userId
        double score = Instant.now().toEpochMilli(); // 요청 시간
        // Redis에 쿠폰 발급 요청을 저장하는 로직
        Boolean isAdded = cacheRepository.saveIfAbsent(COUPON_WAITING_KEY, value, score);
        return Boolean.TRUE.equals(isAdded);
    }
    public boolean issueCoupon(Long couponId) {
        String couponKey = COUPON_KEY + ":" + couponId;
        long remainCouponAmount = cacheRepository.decrement(couponKey);
        return remainCouponAmount >= 0;
    }
    public void issueProcessBatch(long batchSize) {
        Set<String> users = cacheRepository.fetchFromSet(COUPON_WAITING_KEY, batchSize);
        // 쿠폰 발급
        for (String requestUserCoupon : users) {
            // "couponId:userId" 형식에서 분리
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
                cacheRepository.removeFromSet(COUPON_WAITING_KEY, requestUserCoupon);
            } else {
                // 쿠폰 남은 수량이 부족하면 처리 중단
                break;
            }
        }
    }


}
