package kr.hhplus.be.domain.order.entity;

import jakarta.persistence.*;
import kr.hhplus.be.domain.coupon.entity.UserCoupon;
import kr.hhplus.be.domain.order.OrderStatus;
import kr.hhplus.be.domain.user.User;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity(name = "orders")
public class Order {
    @Id @GeneratedValue
    @Column(name = "order_id")
    private Long id;
    @ManyToOne
    @JoinColumn(name = "user_id", foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private User user;
    @OneToOne
    @JoinColumn(name = "user_coupon_id", foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private UserCoupon appliedCoupon;
    @Column(name = "total_price")
    private BigDecimal totalPrice;
    @Column(name = "discount_amount")
    private BigDecimal discountAmount;
    @Column(name = "final_price")
    private BigDecimal finalPrice;
    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;
    private LocalDateTime orderDate;

    private LocalDateTime cancelDate;
    private LocalDateTime completeDate;

    public Order(User user) {
        this.user = user;
        this.orderStatus = OrderStatus.WAITING;
        this.orderDate = LocalDateTime.now();
    }
    public Order() {
        // JPA에서 사용하기 위한 기본 생성자
    }

    public void applyCoupon(UserCoupon userCoupon) {
        this.appliedCoupon = userCoupon;
        this.discountAmount = appliedCoupon.getDiscountAmount();
    }

    public void calculateFinalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
        if (this.discountAmount != null) {
            this.finalPrice = totalPrice.subtract(this.discountAmount);
        }
        else {
            this.finalPrice = totalPrice;
        }
    }
}
