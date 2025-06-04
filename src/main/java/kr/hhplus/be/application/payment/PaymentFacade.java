package kr.hhplus.be.application.payment;

import kr.hhplus.be.application.order.OrderInfo;
import kr.hhplus.be.domain.event.EventPublisher;
import kr.hhplus.be.domain.order.OrderService;
import kr.hhplus.be.domain.payment.Payment;
import kr.hhplus.be.domain.payment.PaymentService;
import kr.hhplus.be.domain.payment.PaymentSuccessEvent;
import kr.hhplus.be.domain.point.PointService;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;


@Component
public class PaymentFacade {
    private final PointService pointService;
    private final PaymentService paymentService;
    private final OrderService orderService;
    private final EventPublisher eventPublisher;

    public PaymentFacade(PointService pointService, PaymentService paymentService, OrderService orderService, EventPublisher eventPublisher) {
        this.pointService = pointService;
        this.paymentService = paymentService;
        this.orderService = orderService;
        this.eventPublisher = eventPublisher;

    }

    @Transactional
    public void payment(PaymentCommand command) {
        pointService.use(command.getUserId(), command.getPayAmount());
        Payment payment = paymentService.pay(command.getPayAmount());

        OrderInfo orderInfo = orderService.getOrderInfo(command.getOrderId());
        eventPublisher.publish(new PaymentSuccessEvent(orderInfo));
    }
}
