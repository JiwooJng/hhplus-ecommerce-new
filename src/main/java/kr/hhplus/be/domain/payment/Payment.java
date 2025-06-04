package kr.hhplus.be.domain.payment;

import jakarta.persistence.*;
import kr.hhplus.be.domain.order.entity.Order;
import kr.hhplus.be.domain.user.User;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
public class Payment {
    @Id @GeneratedValue
    @Column(name = "payment_id")
    private Long id;
    @Column(name = "payAmount")
    private BigDecimal payAmount;
    @Enumerated(EnumType.STRING)
    private PaymentStatus paymentStatus;
    @Column(nullable = true)
    private LocalDateTime cancelDate;
    @Column(nullable = true)
    private LocalDateTime completeDate;

    public Payment(BigDecimal payAmount) {
        this.payAmount = payAmount;
    }
    public Payment() {
        // JPA에서 사용하기 위한 기본 생성자
    }
    public void complete() {
        this.paymentStatus = PaymentStatus.COMPLETED;
        this.completeDate = LocalDateTime.now();
    }

}
