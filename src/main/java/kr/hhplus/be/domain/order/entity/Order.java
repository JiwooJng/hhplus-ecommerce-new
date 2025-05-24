package kr.hhplus.be.domain.order.entity;

import jakarta.persistence.*;
import kr.hhplus.be.domain.order.OrderStatus;
import kr.hhplus.be.domain.payment.Payment;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity(name = "orders")
public class Order {
    // 주문 번호
    @Id @GeneratedValue
    @Column(name = "order_id")
    private Long id;
    // 주문자 정보
    @Column(name = "user_id")
    private Long userId;
    // 주문 상품
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "order_id") // FK는 OrderItem 테이블에
    private List<OrderItem> orderItems = new ArrayList<>();
    // 주문 금액 합계
    @Column(name = "order_amount")
    private BigDecimal orderAmount;
    // 적용 쿠폰
    @Column(name = "user_coupon_id")
    private Long appliedCouponId;
    @Column(name = "discount_amount")
    private BigDecimal discountAmount;
    // 최종 결제 금액
    @Column(name = "pay_amount")
    private BigDecimal payAmount;
    // 주문 상태
    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;
    // 주문 날짜
    private LocalDateTime orderDate;
    private LocalDateTime cancelDate;
    // 결제 정보
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "payment_id")
    private Payment payment;

    public Order(Long userId, Long appliedCouponId, List<OrderItem> orderItems) {
        this.userId = userId;
        this.appliedCouponId = appliedCouponId;
        this.orderItems = orderItems;
        this.orderStatus = OrderStatus.WAITING;
        this.orderDate = LocalDateTime.now();
    }
    public Order() {
        // JPA에서 사용하기 위한 기본 생성자
    }

    public Long getId() {
        return id;
    }
    public Long getUserId() {
        return userId;
    }
    public List<OrderItem> getOrderItems() {
        return orderItems;
    }
    public Long getAppliedCouponId() {
        return appliedCouponId;
    }
    public BigDecimal getOrderAmount() {
        return orderAmount;
    }
    public BigDecimal getDiscountAmount() {
        return discountAmount;
    }
    public BigDecimal getPayAmount() {
        return payAmount;
    }
    public LocalDateTime getOrderDate() {
        return orderDate;
    }

    public void applyCoupon(Long appliedCouponId, BigDecimal discountAmount) {
        this.appliedCouponId = appliedCouponId;
        this.discountAmount = discountAmount;
    }

    public void calculateFinalPrice(BigDecimal totalPrice) {
        this.orderAmount = totalPrice;
        if (this.discountAmount != null) {
            this.payAmount = totalPrice.subtract(this.discountAmount);
        }
        else {
            this.payAmount = totalPrice;
        }
    }
}
