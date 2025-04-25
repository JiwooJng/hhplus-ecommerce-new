package kr.hhplus.be.domain.coupon;

import jakarta.persistence.NoResultException;
import kr.hhplus.be.domain.coupon.entity.Coupon;
import kr.hhplus.be.domain.coupon.entity.UserCoupon;
import kr.hhplus.be.domain.coupon.enumtype.CouponType;
import kr.hhplus.be.domain.coupon.repository.CouponRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
public class CouponService {
    private final CouponRepository couponRepository;

    public CouponService(CouponRepository couponRepository) {
        this.couponRepository = couponRepository;
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


}
