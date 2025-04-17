package kr.hhplus.be.payment;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import kr.hhplus.be.order.entity.Order;
import kr.hhplus.be.user.User;

import java.time.LocalDateTime;

public class Payment {
    @Id @GeneratedValue
    private Long id;
    @ManyToOne
    private User user;
    @OneToOne
    private Order order;
    private PaymentStatus paymentStatus;
    private LocalDateTime createDate;
    private LocalDateTime cancelDate;
    private LocalDateTime completeDate;

    public Payment(User user, Order order) {
        this.user = user;
        this.order = order;
        this.paymentStatus = PaymentStatus.WAITING;
        this.createDate = LocalDateTime.now();
    }

    public void complete() {
        this.paymentStatus = PaymentStatus.COMPLETED;
        this.completeDate = LocalDateTime.now();
    }

}
