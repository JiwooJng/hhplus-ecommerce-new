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

    public boolean isExist(Long couponId) {
        // 쿠폰 존재 여부 확인
        return couponRepository.checkExistById(couponId);
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

    public Coupon use(Long userId, Long couponId) {
        // 쿠폰 사용 로직
        UserCoupon userCoupon = userCouponRepository.findById(userId, couponId);
        Coupon coupon = couponRepository.findById(couponId);
        if (userCoupon == null) {
            throw new IllegalArgumentException("존재하지 않는 쿠폰입니다.");
        }

        if (!userCoupon.canUse()) { // true 면 사용 가능, false 면 사용 불가
            throw new RuntimeException("이미 사용한 쿠폰입니다.");
        }
        userCoupon.use(); // 쿠폰 사용 처리
        return coupon; // 쿠폰 사용 성공
    }

}
