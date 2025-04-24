package kr.hhplus.be.domain.coupon.repository;

import kr.hhplus.be.domain.coupon.entity.Coupon;
import kr.hhplus.be.domain.coupon.entity.UserCoupon;

import java.util.Optional;

public interface CouponRepository {

    Coupon findById(Long couponId);
    Coupon save(Coupon coupon);

    Coupon checkExistById(Long couponId);

    UserCoupon findUserCouponById(Long userId, Long couponId);
    UserCoupon saveUserCoupon(UserCoupon userCoupon);
    void deleteUsedCoupon(UserCoupon userCoupon);

    Optional<Coupon> findByPessimisticLock(Long couponId);

    void flush();
}
