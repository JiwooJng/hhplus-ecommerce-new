package kr.hhplus.be.application.payment;

import java.math.BigDecimal;

public class PaymentCommand {
    private Long userId;
    private Long orderId;
    private BigDecimal payAmount;

    public PaymentCommand(Long userId, Long orderId, BigDecimal payAmount) {
        this.userId = userId;
        this.orderId = orderId;
        this.payAmount = payAmount;
    }

    public Long getUserId() {
        return userId;
    }
    public Long getOrderId() {
        return orderId;
    }
    public BigDecimal getPayAmount() {
        return payAmount;
    }
}
