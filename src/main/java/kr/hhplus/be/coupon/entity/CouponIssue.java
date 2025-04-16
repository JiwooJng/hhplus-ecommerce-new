package kr.hhplus.be.coupon.entity;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import kr.hhplus.be.coupon.enumtype.CouponIssueStatus;

import java.time.LocalDateTime;

public class CouponIssue {

    @Id @GeneratedValue
    private Long id;
    private Long couponId;
    private Long userId;
    private CouponIssueStatus status;
    private LocalDateTime issuedDate;
    private LocalDateTime usedDate;

    public CouponIssue(Long couponId, Long userId) {
        this.couponId = couponId;
        this.userId = userId;
        this.status = CouponIssueStatus.UNUSED;
        this.issuedDate = LocalDateTime.now();
    }

    public boolean canUse() {
        return this.status.equals(CouponIssueStatus.UNUSED);
    }
    public void use() {
        this.status = CouponIssueStatus.USED;
        this.usedDate = LocalDateTime.now();
    }

}
