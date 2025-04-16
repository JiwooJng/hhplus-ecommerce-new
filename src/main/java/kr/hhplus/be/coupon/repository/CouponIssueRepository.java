package kr.hhplus.be.coupon.repository;

import kr.hhplus.be.coupon.entity.CouponIssue;

public interface CouponIssueRepository {
    CouponIssue findById(Long userId, Long couponId);
}
