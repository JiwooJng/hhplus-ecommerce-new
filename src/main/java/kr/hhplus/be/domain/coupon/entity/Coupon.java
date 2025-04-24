package kr.hhplus.be.domain.coupon.entity;

import jakarta.persistence.*;
import kr.hhplus.be.domain.coupon.enumtype.CouponStatus;
import kr.hhplus.be.domain.coupon.enumtype.CouponType;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
public class Coupon {
    @Id @GeneratedValue
    @Column(name = "coupon_id")
    private Long id;
    @Enumerated(EnumType.STRING)
    private CouponType type;
    @Column(nullable = false)
    private BigDecimal discountAmount;
    @Column(nullable = false)
    private Long maxIssueAvailable;
    private Long issuedAmount;

    @Enumerated(EnumType.STRING)
    private CouponStatus canIssueStatus;

    private LocalDateTime publishDate;

    public Coupon (CouponType type, BigDecimal discountAmount, Long maxIssueAvailable) {
        this.type = type;
        this.discountAmount = discountAmount;
        this.maxIssueAvailable = maxIssueAvailable;
        this.issuedAmount = 0L;
        this.canIssueStatus = CouponStatus.ACTIVE;
        this.publishDate = LocalDateTime.now();

    }
    public Coupon() {
        // JPA에서 사용하기 위한 기본 생성자
    }

    public void issue() {
        if (this.canIssueStatus.equals(CouponStatus.INACTIVE)) {
            throw new RuntimeException("쿠폰 발급이 불가능한 상태입니다.");
        }
        if (this.issuedAmount >= this.maxIssueAvailable) {
            throw new RuntimeException("쿠폰이 소진 되었습니다");
        }
        this.issuedAmount++;
    }

    public BigDecimal getDiscountAmount() {
        return this.discountAmount;
    }

    public Long getId() {
        return this.id;
    }
    public Long getIssuedAmount() {
        return this.issuedAmount;
    }
}
