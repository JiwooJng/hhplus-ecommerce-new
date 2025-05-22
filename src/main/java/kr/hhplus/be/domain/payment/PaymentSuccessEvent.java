package kr.hhplus.be.domain.payment;

import kr.hhplus.be.application.order.OrderInfo;

public class PaymentSuccessEvent {
    private OrderInfo orderInfo;

    public PaymentSuccessEvent(OrderInfo orderInfo) {
        this.orderInfo = orderInfo;
    }

    public OrderInfo getOrderInfo() {
        return orderInfo;
    }
}
