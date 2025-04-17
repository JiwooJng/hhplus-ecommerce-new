package kr.hhplus.be.coupon.repository;

import kr.hhplus.be.coupon.entity.UserCoupon;

public interface UserCouponRepository {
    UserCoupon findById(Long userId, Long couponId);
    UserCoupon save(UserCoupon userCoupon);
    void delete(UserCoupon userCoupon);

}
