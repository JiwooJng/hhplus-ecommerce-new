package kr.hhplus.be.domain.coupon.entity;

import jakarta.persistence.*;
import kr.hhplus.be.domain.coupon.enumtype.CouponIssueStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;


@Entity
public class UserCoupon {

    @Id @GeneratedValue
    @Column(name = "user_coupon_id")
    private Long id;
    @Column(nullable = false)
    private Long userId;
    @ManyToOne
    @JoinColumn(name = "coupon_id", foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private Coupon coupon;
    @Enumerated(EnumType.STRING)
    private CouponIssueStatus status; // 사용, 미사용
    @Column(nullable = false)
    private LocalDateTime issuedDate;
    @Column(nullable = true)
    private LocalDateTime usedDate;

    public UserCoupon(Long userId, Coupon coupon) {
        this.userId = userId;
        this.coupon = coupon;
        this.status = CouponIssueStatus.UNUSED;
        this.issuedDate = LocalDateTime.now();
    }
    public UserCoupon() {
        // JPA에서 사용하기 위한 기본 생성자
    }

    public Long getId() {
        return id;
    }
    public void use() {
        if (this.status.equals(CouponIssueStatus.USED)) {
            throw new RuntimeException("쿠폰을 사용할 수 없습니다");
        }
        this.status = CouponIssueStatus.USED;
        this.usedDate = LocalDateTime.now();
    }
    public BigDecimal getDiscountAmount() {
        // 쿠폰 할인 금액 계산 로직
        return coupon.getDiscountAmount();
    }
}
