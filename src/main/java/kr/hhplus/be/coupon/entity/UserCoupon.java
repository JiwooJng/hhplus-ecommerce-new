package kr.hhplus.be.coupon.entity;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import kr.hhplus.be.coupon.enumtype.CouponIssueStatus;
import kr.hhplus.be.user.User;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class UserCoupon {

    @Id @GeneratedValue
    private Long id;
    @ManyToOne
    private User user;
    @ManyToOne
    private Coupon coupon;

    private CouponIssueStatus status; // 사용, 미사용
    private LocalDateTime issuedDate;
    private LocalDateTime usedDate;

    public UserCoupon(User user, Coupon coupon) {
        this.user = user;
        this.coupon = coupon;
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
    public BigDecimal calculatePrice(BigDecimal totalPrice) {
        // 쿠폰 할인 금액 계산 로직
        return coupon.apply();
    }

}
