package kr.hhplus.be.coupon.repository;

import kr.hhplus.be.coupon.entity.Coupon;

public interface CouponRepository {

    Coupon findById(Long couponId);
    Coupon save(Coupon coupon);

    boolean checkExistById(Long couponId);
}
