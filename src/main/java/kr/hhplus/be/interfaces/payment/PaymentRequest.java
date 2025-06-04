package kr.hhplus.be.interfaces.payment;

import kr.hhplus.be.application.payment.PaymentCommand;

import java.math.BigDecimal;

public class PaymentRequest {
    private Long userId;
    private Long orderId;
    private BigDecimal payAmount;

    public PaymentRequest(Long userId, Long orderId, BigDecimal payAmount) {
        this.userId = userId;
        this.orderId = orderId;
        this.payAmount = payAmount;
    }

    public PaymentCommand toCommand() {
        return new PaymentCommand(userId, orderId, payAmount);
    }

}
