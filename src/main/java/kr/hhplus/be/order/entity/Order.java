package kr.hhplus.be.order.entity;

import jakarta.persistence.*;
import kr.hhplus.be.coupon.entity.Coupon;
import kr.hhplus.be.order.OrderStatus;
import kr.hhplus.be.user.User;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Order {
    @Id @GeneratedValue
    private Long id;
    @ManyToOne
    private User user;
    @OneToOne
    private Coupon appliedCoupon;
    private BigDecimal totalPrice;
    private BigDecimal discountPrice;
    private BigDecimal finalPrice;
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

    public BigDecimal applyCoupon(Coupon coupon) {
        this.appliedCoupon = coupon;
        this.discountPrice = coupon.apply();
        this.finalPrice = this.totalPrice.subtract(this.discountPrice);
        return this.finalPrice;
    }
    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }
    public void setFinalPrice(BigDecimal finalPrice) {
        this.finalPrice = finalPrice;
    }
}
