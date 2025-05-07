package kr.hhplus.be.domain.payment;

import jakarta.persistence.*;
import kr.hhplus.be.domain.order.entity.Order;
import kr.hhplus.be.domain.user.User;

import java.time.LocalDateTime;

@Entity
public class Payment {
    @Id @GeneratedValue
    private Long id;
    @ManyToOne
    @JoinColumn(name = "user_id", foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private User user;
    @OneToOne
    @JoinColumn(name = "order_id", foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private Order order;
    @Enumerated(EnumType.STRING)
    private PaymentStatus paymentStatus;
    @Column(nullable = false)
    private LocalDateTime createDate;
    @Column(nullable = true)
    private LocalDateTime cancelDate;
    @Column(nullable = true)
    private LocalDateTime completeDate;

    public Payment(User user, Order order) {
        this.user = user;
        this.order = order;
        this.paymentStatus = PaymentStatus.WAITING;
        this.createDate = LocalDateTime.now();
    }
    public Payment() {

    }

    public void complete() {
        this.paymentStatus = PaymentStatus.COMPLETED;
        this.completeDate = LocalDateTime.now();
    }

}
