package kr.hhplus.be.coupon.entity;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import kr.hhplus.be.coupon.enumtype.CouponStatus;
import kr.hhplus.be.coupon.enumtype.CouponType;

import java.time.LocalDateTime;

public class Coupon {

    @Id @GeneratedValue
    private Long id;
    private CouponType type;
    private Long maxIssueAvailable;
    private Long issuedAmount;

    @Enumerated(EnumType.STRING)
    private CouponStatus canIssueStatus;

    private LocalDateTime publishDate;

    public Coupon (CouponType type, Long maxIssueAvailable) {
        this.type = type;
        this.maxIssueAvailable = maxIssueAvailable;
        this.issuedAmount = this.maxIssueAvailable;
        this.canIssueStatus = CouponStatus.ACTIVE;
        this.publishDate = LocalDateTime.now();

    }

    public boolean canIssue() {
        if (this.canIssueStatus.equals(CouponStatus.INACTIVE)) {
            return false;
        }
        return true;
    }
}
