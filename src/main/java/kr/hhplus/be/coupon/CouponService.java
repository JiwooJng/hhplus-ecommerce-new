package kr.hhplus.be.coupon;

import kr.hhplus.be.coupon.entity.Coupon;
import kr.hhplus.be.coupon.entity.UserCoupon;
import kr.hhplus.be.coupon.enumtype.CouponType;
import kr.hhplus.be.coupon.repository.UserCouponRepository;
import kr.hhplus.be.coupon.repository.CouponRepository;
import kr.hhplus.be.user.User;
import kr.hhplus.be.user.UserRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class CouponService {
    private final UserRepository userRepository;
    private final CouponRepository couponRepository;
    private final UserCouponRepository userCouponRepository;

    public CouponService(UserRepository userRepository, CouponRepository couponRepository, UserCouponRepository userCouponRepository) {
        this.userRepository = userRepository;
        this.couponRepository = couponRepository;
        this.userCouponRepository = userCouponRepository;
    }

    public void publish(CouponType type, BigDecimal discountAmount, Long amount) {
        // 쿠폰 발행 로직
        Coupon coupon = new Coupon(type, discountAmount, amount);
        couponRepository.save(coupon);
    }

    public UserCoupon issue(Long couponId, Long userId) {
        // 쿠폰 발급 로직
        Coupon coupon = couponRepository.findById(couponId);
        User user = userRepository.findById(userId);
        if (coupon == null) {
            throw new IllegalArgumentException("Coupon not found");
        }
        if (!coupon.canIssue()) {
            throw new RuntimeException("Coupon is not available for issue");
        }
        coupon.issue(); // 쿠폰 발급 처리

        // 쿠폰 발급 처리
        return userCouponRepository.save(new UserCoupon(user, coupon)); // 쿠폰 발급 및 저장
    }

    public boolean use(Long userId, Long couponId) {
        // 쿠폰 사용 로직
        UserCoupon couponIssue = userCouponRepository.findById(userId, couponId);
        if (couponIssue == null) {
            return false; // 쿠폰이 존재하지 않음
        }

        if (!couponIssue.canUse()) { // true 면 사용 가능, false 면 사용 불가
            return false; // 이미 사용된 쿠폰
        }
        couponIssue.use(); // 쿠폰 사용 처리
        return true; // 쿠폰 사용 성공
    }

}
