package kr.hhplus.be.coupon.entity;

import jakarta.persistence.*;
import kr.hhplus.be.coupon.enumtype.CouponStatus;
import kr.hhplus.be.coupon.enumtype.CouponType;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
public class Coupon {

    @Id @GeneratedValue
    private Long id;
    private CouponType type;
    private BigDecimal discountAmount;
    private Long maxIssueAvailable;
    private Long issuedAmount;

    @Enumerated(EnumType.STRING)
    private CouponStatus canIssueStatus;

    private LocalDateTime publishDate;

    public Coupon (CouponType type, BigDecimal discountAmount, Long maxIssueAvailable) {
        this.type = type;
        this.discountAmount = discountAmount;
        this.maxIssueAvailable = maxIssueAvailable;
        this.issuedAmount = this.maxIssueAvailable;
        this.canIssueStatus = CouponStatus.ACTIVE;
        this.publishDate = LocalDateTime.now();

    }
    public Coupon() {
        // JPA에서 사용하기 위한 기본 생성자
    }

    public boolean canIssue() {
        if (this.canIssueStatus.equals(CouponStatus.INACTIVE)) {
            return false;
        }
        return true;
    }

    public void issue() {
        if (this.issuedAmount > this.maxIssueAvailable) {
            throw new RuntimeException("쿠폰이 소진 되었습니다");
        }
        this.issuedAmount++;
    }
}
